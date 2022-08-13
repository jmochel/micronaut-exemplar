package org.saltations.persons;

import org.saltations.IEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class EntityFixture<CI, C extends CI, E extends CI>
{
    /**
     * Returns a prototype of the value (object with core data)
     */

    public abstract C protoValue();

    /**
     * Returns a prototype f the entity (object with core data and id)
     */

    public abstract E protoEntity();

    /**
     * Confirms that the objects have the same core metadata
     */

    public abstract void hasSameCoreContent(CI expected, CI actual);

}
