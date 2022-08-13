package org.saltations;

import java.net.InterfaceAddress;

public interface IEntity<ID>
{
    Long id();

    void id(Long id);
}
