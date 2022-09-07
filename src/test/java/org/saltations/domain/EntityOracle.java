package org.saltations.domain;

import lombok.Getter;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Generates example core objects and entities that implement the core interface (IC)
 *
 * @param <IC> Interface of the business item being represented
 * @param <C> Class of the business item
 * @param <E> Class of the persistable business item entity. Contains all the same data as <C> but Supports additional
 *           entity specific meta-data.
 */

public abstract class EntityOracle<IC, C extends IC, E extends IC>
{
    @Getter
    private final Class<C> coreClass;
    @Getter
    private final Class<E> entityClass;

    public EntityOracle(Class<C> coreClass, Class<E> entityClass)
    {
        this.coreClass = coreClass;
        this.entityClass = entityClass;
    }

    /**
     * Returns a prototype core object (object with core data).
     */

    public abstract C corePrototype();

    /**
     * Returns a core object with different values for each core attribute
     */

    public abstract C alteredCore();

    /**
     * Returns a prototype entity (object with core data and id)
     */

    public abstract E entityPrototype();



    /**
     * Confirms that the objects have the same core data
     */

    public abstract void hasSameCoreContent(IC expected, IC actual);

    public void hasSameCoreContentAsPrototype(IC actual)
    {
        hasSameCoreContent(corePrototype(), actual);
    }
}
