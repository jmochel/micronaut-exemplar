package org.saltations.domain;

import io.micronaut.json.JsonMapper;
import io.micronaut.json.tree.JsonNode;
import org.saltations.domain.error.CannotCreateEntity;
import org.saltations.domain.error.CannotDeleteEntity;
import org.saltations.domain.error.CannotFindEntity;
import org.saltations.domain.error.CannotPatchUnknownProperty;
import org.saltations.domain.error.CannotUpdateEntity;
import org.saltations.domain.error.CannotUpdateEntityFromPatch;
import org.saltations.domain.error.SearchOperatorRequiresDifferentNumberOfCriteria;
import org.saltations.domain.error.UnknownSearchOperator;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *  Generic service for processing entities; An object uniquely identified by a single identity attribute
 *
 * @param <ID> Type of the unique identifier .
 * @param <IC> Interface of the business item being represented
 * @param <E> Class of the persistable business item entity. Contains all the same data as <C> but supports additional
 *           entity specific meta-data.
 * @param <R> Type of the repository used by the service
 * @param <S> Type of the search spec used by the service
 * @param <SP> Type of the search spec provider used by the service
 */

public abstract class EntityService<ID, IC, C extends IC, E extends IEntity<ID>, S extends EntitySearchSpec<E>,  R extends EntityRepo<ID,E>, M extends EntityMapper<ID,C,E>, SP extends EntitySearchSpecProvider<E,S>>
{
    private final Class<E> clazz;

    private final R repo;

    private final EntityMapper<ID,C,E> modelMapper;

    private final EntitySearchSpecProvider<E,S> searchSpecProvider;

    private final JsonMergePatchProvider patchProvider;

    private final JsonMapper jsonMapper;

    /**
     * Primary constructor
     *
     * @param clazz              Type of the entity
     * @param repo               Repository for persistence of entities
     * @param searchSpecProvider Search Spec Provider
     * @param patchProvider
     * @param jsonMapper
     */

    public EntityService(Class<E> clazz, R repo, EntityMapper<ID,C,E> modelMapper, EntitySearchSpecProvider<E, S> searchSpecProvider, JsonMergePatchProvider patchProvider, JsonMapper jsonMapper)
    {
        this.repo = repo;
        this.clazz = clazz;
        this.modelMapper = modelMapper;
        this.searchSpecProvider = searchSpecProvider;
        this.patchProvider = patchProvider;
        this.jsonMapper = jsonMapper;
    }

    /**
     * @return the simple name of the entity type
     */

    protected String resourceTypeName()
    {
        return clazz.getSimpleName();
    }

    /**
     * Find the entity by its identifier
     *
     * @param id Identifier. Not null.
     *
     * @return Possible result. {@link Optional#empty()} if no entity matching the id is find.
     */

    public Optional<E> findById(@NotNull ID id)
    {
        return repo.findById(id);
    }

    /**
     * Find all resources that match the criteria
     * <p>
     * The criteria are in the form of property-name and string value that contains a comma delimited set of an operator
     * and criteria for that operator. Some examples:
     * <table border=1>
     *     <tr><td>Property Name</td><td>Operator and criteria</td></tr>
     *     <tr><td>firstName</td><td>is_null</td></tr>
     *     <tr><td>firstName</td><td>is+not_null</td></tr>
     *     <tr><td>firstName</td><td>eq,Lazarus</td></tr>
     *     <tr><td>lastUpdated</td><td>eq,2022-09-22T12:00:00</td></tr>
     *     <tr><td>lastUpdated</td><td>btn,2022-09-22T12:00:00,2022-09-24T12:00:00</td></tr>
     * </table>
     *
     * @param queryCriteria Map of property names and strings containing the comma separated criteria.
     *
     * @return List of matching entities. An empty list if no entities match the criteria
     */

    public List<E> find(@NotNull Map<String,String> queryCriteria) throws SearchOperatorRequiresDifferentNumberOfCriteria, UnknownSearchOperator {
        var criteria = searchSpecProvider.supply(queryCriteria);

        return repo.findAll(criteria);
    }

    /**
     * Creates an entity of type E from the prototype object.
     *
     * @param prototype Prototype object that contains the attributes necessary to create an entity of type E. Valid and not null.
     *
     * @return Populated entity of type E
     *
     * @throws CannotCreateEntity if the entity could not be created from the prototype
     */

    @Transactional(Transactional.TxType.REQUIRED)
    public E create(@NotNull @Valid C prototype) throws CannotCreateEntity
    {
        E created;

        try
        {
            var toBeCreated = modelMapper.createEntity(prototype);

            created = repo.save(toBeCreated);
        }
        catch (Exception e)
        {
            throw new CannotCreateEntity(e, resourceTypeName(), prototype.toString());
        }

        return created;
    }


    /**
     * Updates an entity of type E with the contents of the given entity.
     *
     * @param update is the entity with the modified values and the ID of the entity to be modified. Valid and not null.
     *
     * @return updated entity.
     *
     * @throws CannotUpdateEntity If the entity could not be updated for any reason
     */

    @Transactional(Transactional.TxType.REQUIRED)
    public E replace(@NotNull @Valid E update) throws CannotUpdateEntity
    {
        E updated;

        try
        {
            updated = repo.update(update);
        }
        catch (Exception e)
        {
            throw new CannotUpdateEntity(e, resourceTypeName(), update.getId());
        }


        return updated;
    }

    /**
     * Modify the entity with the given id with the given patch
     *
     * @param id Identifier. Not null.
     * @param patch The JSON Patch
     *
     * @return
     *
     * @throws CannotFindEntity
     * @throws CannotUpdateEntityFromPatch
     */

    @Transactional(Transactional.TxType.REQUIRED)
    public E modify(@NotNull ID id, @NotNull JsonNode patch) throws CannotFindEntity, CannotUpdateEntityFromPatch, CannotPatchUnknownProperty
    {
        var current = repo.findById(id).orElseThrow(() -> new CannotFindEntity(resourceTypeName(), id));

        // Merge the patches on top of the current agreement

        var mergeProvider = patchProvider.supply(current, patch);
        mergeProvider.apply();

        return repo.update(current);
    }

    /**
     * Deletes an entity of type E with the given ID.
     *
     * @param id is the unique identifier for the entity
     *
     * @throws CannotDeleteEntity If the entity could not be deleted for any reason
     */

    @Transactional(Transactional.TxType.REQUIRED)
    public void delete(@NotNull ID id) throws CannotDeleteEntity
    {
        try
        {
            repo.deleteById(id);
        }
        catch (Exception e)
        {
            throw new CannotDeleteEntity(e, resourceTypeName(), id);
        }
    }

    /**
     * Delete all entities
     */

    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteAll()
    {
        repo.deleteAll();
    }

}
