package org.saltations.domain;

import lombok.Getter;

/**
 * Generates example objects of type T
 *
 * @param <T> Type Class of the exemplar
 */

public abstract class Oracle<T>
{
    @Getter
    private final Class<T> clazz;

    public Oracle(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    /**
     * Returns a prototype object
     */

    public abstract T prototype();

    /**
     * Confirms that the objects have the same core data
     */

    public abstract void hasSameContent(T expected, T actual);

    public void hasSameContentAsPrototype(T actual)
    {
        hasSameContent(prototype(), actual);
    }
}
