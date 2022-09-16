package org.saltations;

import io.micronaut.core.type.Argument;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * An adapter class that allows rest assured to use Micronaut's Serde serialization/deserialization
 */

@Slf4j
public class SerdeRestAssuredObjectMapper implements ObjectMapper
{
    private final io.micronaut.serde.ObjectMapper serdeMapper;

    public SerdeRestAssuredObjectMapper(io.micronaut.serde.ObjectMapper serdeMapper) {
        this.serdeMapper = serdeMapper;
    }

    @Override
    public Object deserialize(ObjectMapperDeserializationContext deserializationCtx)
    {
        var toDeserialize = deserializationCtx.getDataToDeserialize().asString();
        var objectType = deserializationCtx.getType();

        try {
            return serdeMapper.readValue(toDeserialize, Argument.of(objectType));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object serialize(ObjectMapperSerializationContext serializationCtx)
    {
        var toSerialize = serializationCtx.getObjectToSerialize();

        try {
            return serdeMapper.writeValueAsString(toSerialize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
