package org.saltations.persons;

import io.micronaut.core.beans.BeanIntrospection;
import io.micronaut.core.convert.ConversionService;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.saltations.DBContainerTestBase;
import org.saltations.persons.mapping.PersonMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j(topic = "PersonMappingTest")
@DisplayName("Person Mapping and Serialization")
class PersonMappingTest extends DBContainerTestBase
{
    @Inject
    private ObjectMapper jsonMapper;

    @Inject
    private PersonOracle oracle;

    @Inject
    private PersonMapper modelMapper;

    @Inject
    private ConversionService<?> conversionService;

    @Test
    @Order(1)
    void pokeTheIntrospector() throws Exception
    {
        var introspection = BeanIntrospection.getIntrospection(PersonCore.class);
        introspection.getBeanProperties().forEach(p -> log.info("{}:{}", p.getType().getSimpleName(), p.getName()));

        final PersonCore core = introspection.instantiate();
        log.info("{}", core.toString());

        var prop= introspection.getBeanProperties().stream().filter(p -> p.getName().equals("lastName")).findFirst().orElseThrow();

        var convertedVal =  conversionService.convert(Character.valueOf('J'), prop.getType()).get();
        prop.set(core,convertedVal);
        assertEquals("J", core.getLastName());


    }

    @Test
    @Order(2)
    @DisplayName("can do a round trip serialization and deserialization of the core object")
    void canSerializeAndDeserializeTheCoreObject() throws Exception
    {
        var prototype = oracle.corePrototype();
        var serialized = jsonMapper.writeValueAsString(prototype);
        var reconstituted = jsonMapper.readValue(serialized, oracle.getCoreClass());

        oracle.hasSameCoreContent(prototype, reconstituted);
    }

    @Test
    @Order(4)
    @DisplayName("can do a round trip serialization and deserialization of the entity")
    void canSerializeAndDeserializeTheEntity() throws Exception
    {
        var prototype = oracle.entityPrototype();
        var serialized = jsonMapper.writeValueAsString(prototype);
        var reconstituted = jsonMapper.readValue(serialized, oracle.getEntityClass());

        oracle.hasSameCoreContent(prototype, reconstituted);
        assertEquals(prototype.getId(), reconstituted.getId());
    }

    @Test
    @Order(6)
    @DisplayName("can create an entity from a core object")
    void canCreateEntityFromCoreObject() throws Exception
    {
        var coreObject = oracle.corePrototype();

        var entity  = modelMapper.createEntity(coreObject);

        oracle.hasSameCoreContent(coreObject, entity);
    }

    @Test
    @Order(8)
    @DisplayName("can create an core object from an entity")
    void canCreateCoreObjectFromEntity() throws Exception
    {
        var entity = oracle.entityPrototype();

        var coreObject  = modelMapper.createCore(entity);

        oracle.hasSameCoreContent(entity, coreObject);
    }

    @Test
    @Order(10)
    @DisplayName("can update an entity from a core object")
    void canUpdateCoreObjectFromEntity() throws Exception
    {
        var coreObject = oracle.alteredCore();
        var entity = oracle.entityPrototype();

        var updatedEntity  = modelMapper.update(coreObject, entity);

        oracle.hasSameCoreContent(coreObject, updatedEntity);
    }
}
