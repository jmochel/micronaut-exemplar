package org.saltations.domain.error;

import io.micronaut.serde.ObjectMapper;
import io.micronaut.serde.annotation.SerdeImport;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.saltations.DBContainerTestBase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Confirms that all domain errors can be created using expected inputs.
 * This is primarily a test that the implemented exceptions are using a correct detail message template internally
 */

@DisplayName("Error creation and serialization")
@SerdeImport(Exception.class)
class ErrorCreationTest extends DBContainerTestBase
{
    public static final String RESOURCE_TYPE = "Schmoo";
    public static final String OBJECT_CONTENTS = "smoo-object";
    public static final Long ID = Long.valueOf(127L);
    @Inject
    private ObjectMapper jsonMapper;

    @Inject
    private ErrorMapper errorMapper;

    @Test
    @Order(1)
    @DisplayName("can create GenericProblem from DomainException")
    void canCreateGenericProblemFromDomainException() throws Exception
    {
        var e = assertThrows(CannotCreateEntity.class, () -> { throw new CannotCreateEntity(RESOURCE_TYPE, OBJECT_CONTENTS);});

        var problem = errorMapper.create(e);

        assertEquals(e.getStatusType(), problem.getStatus());
        assertEquals(e.getTitle(), problem.getTitle());
        assertEquals(e.getDetail(), problem.getDetail());
        assertEquals(e.getProperties().get("trace-id"), problem.getParameters().get("trace-id"));

        assertEquals("localhost", problem.getType().getHost());
        assertEquals("/probs/cannot-create-entity", problem.getType().getPath());
    }

    @Test
    @Order(2)
    @DisplayName("can create CannotCreateEntity")
    void canInstantiate_CannotCreateEntity() throws Exception
    {
        var e = new CannotCreateEntity(RESOURCE_TYPE, OBJECT_CONTENTS);

        var str = jsonMapper.writeValueAsString(e);

        assertNotNull(str);
        assertTrue(str.contains(RESOURCE_TYPE));
        assertTrue(str.contains(OBJECT_CONTENTS));
    }

    @Test
    @Order(4)
    @DisplayName("can create CannotCreateEntity with exception")
    void canInstantiate_CannotCreateEntity_withException() throws Exception
    {
        var e = new CannotCreateEntity(new IllegalArgumentException("Bad idea"), RESOURCE_TYPE, OBJECT_CONTENTS);

        var str = jsonMapper.writeValueAsString(e);

        assertNotNull(str);
        assertTrue(str.contains(RESOURCE_TYPE));
        assertTrue(str.contains(OBJECT_CONTENTS));
    }

    @Test
    @Order(6)
    @DisplayName("can create CannotDeleteEntity")
    void canInstantiate_CannotDeleteEntity() throws Exception
    {
        var e = new CannotDeleteEntity(RESOURCE_TYPE, ID);

        var str = jsonMapper.writeValueAsString(e);

        assertNotNull(str);
        assertTrue(str.contains(RESOURCE_TYPE));
        assertTrue(str.contains(String.valueOf(ID)));
    }

    @Test
    @Order(8)
    @DisplayName("can create CannotDeleteEntity with exception")
    void canInstantiate_CannotDeleteEntity_withException() throws Exception
    {
        var e = new CannotDeleteEntity(new IllegalArgumentException("Bad idea"), RESOURCE_TYPE, ID);

        var str = jsonMapper.writeValueAsString(e);

        assertNotNull(str);
        assertTrue(str.contains(RESOURCE_TYPE));
        assertTrue(str.contains(String.valueOf(ID)));
    }

    @Test
    @Order(10)
    @DisplayName("can create CannotUpdateEntity")
    void canInstantiate_CannotUpdateEntity() throws Exception
    {
        var e = new CannotUpdateEntity(RESOURCE_TYPE, ID);

        var str = jsonMapper.writeValueAsString(e);

        assertNotNull(str);
        assertTrue(str.contains(RESOURCE_TYPE));
        assertTrue(str.contains(String.valueOf(ID)));
    }

    @Test
    @Order(12)
    @DisplayName("can create CannotUpdateEntity with exception")
    void canInstantiate_CannotUpdateEntity_withException() throws Exception
    {
        var e = new CannotUpdateEntity(new IllegalArgumentException("Bad idea"), RESOURCE_TYPE, ID);

        var str = jsonMapper.writeValueAsString(e);

        assertNotNull(str);
        assertTrue(str.contains(RESOURCE_TYPE));
        assertTrue(str.contains(String.valueOf(ID)));
    }
}

