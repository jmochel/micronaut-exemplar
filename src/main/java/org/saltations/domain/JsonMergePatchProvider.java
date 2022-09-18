package org.saltations.domain;

import io.micronaut.core.convert.ConversionService;
import io.micronaut.json.tree.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class JsonMergePatchProvider {

    @Inject
    private ConversionService<?> conversionService;

    public JsonMergePatch supply(Object target, JsonNode patch)
    {
        return new JsonMergePatch( conversionService, target, patch);
    }
}
