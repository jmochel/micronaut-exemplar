package org.saltations.persons;

import com.fasterxml.jackson.core.JsonFactory;
import io.micronaut.core.beans.BeanIntrospection;
import io.micronaut.core.convert.ConversionService;
import io.micronaut.jackson.core.tree.JsonNodeTreeCodec;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.saltations.DBContainerTestBase;
import org.saltations.persons.mapping.PersonMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j(topic = "PersonPatchingTest")
@DisplayName("Person Patching")
class PersonPatchingTest extends DBContainerTestBase
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
    @Order(4)
    @DisplayName("can do a round trip serialization and deserialization of the entity")
    void canSerializeAndDeserializeTheEntity() throws Exception
    {
        var prototype = oracle.entityPrototype();
        var toBeModified = oracle.entityPrototype();

        var factory = new JsonFactory();

        var samplePatch = "{ \"first_name\" : null }";

        var patchAsNode = JsonNodeTreeCodec.getInstance().readTree(factory.createParser(samplePatch));

        jsonMapper.updateValueFromTree(toBeModified, patchAsNode);

        assertNull(toBeModified.getFirstName());
    }

}
