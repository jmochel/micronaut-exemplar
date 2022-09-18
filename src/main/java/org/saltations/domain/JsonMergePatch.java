package org.saltations.domain;

import com.google.common.base.CaseFormat;
import io.micronaut.core.beans.BeanProperty;
import io.micronaut.core.convert.ConversionService;
import io.micronaut.json.tree.JsonNode;
import org.saltations.domain.error.CannotPatchUnknownProperty;

import java.util.Map;

import static io.micronaut.core.beans.BeanIntrospection.getIntrospection;
import static java.util.stream.Collectors.toMap;

/**
 * Implementation of JSON Merge Patch (RFC 7386)
 *
 * <p><a href="http://tools.ietf.org/html/rfc7386">JSON Merge Patch</a> is a
 * "toned down" version of JSON Patch. However, it covers a very large number of
 * use cases for JSON value modifications; its focus is mostly on patching
 * JSON Objects, which are by far the most common type of JSON texts exchanged
 * on the Internet.</p>
 *
 * <p>Applying a JSON Merge Patch is defined by a single, pseudo code function
 * as follows (quoted from the RFC; indentation fixed):</p>
 *
 * <pre>
 *     define MergePatch(Target, Patch):
 *         if Patch is an Object:
 *             if Target is not an Object:
 *                 Target = {} # Ignore the contents and set it to an empty Object
 *             for each Name/Value pair in Patch:
 *                 if Value is null:
 *                     if Name exists in Target:
 *                         remove the Name/Value pair from Target
 *                 else:
 *                     Target[Name] = MergePatch(Target[Name], Value)
 *             return Target
 *         else:
 *             return Patch
 * </pre>
 */

public class JsonMergePatch
{
    private final ConversionService<?> conversionService;
    private final Object target;
    private final JsonNode patch;

    public JsonMergePatch(ConversionService<?> conversionService, Object target, JsonNode patch) {
        this.conversionService = conversionService;
        this.target = target;
        this.patch = patch;
    }

    public Object apply() throws CannotPatchUnknownProperty {

        /*
         *      define MergePatch(Object target, JsonNode patch)
         *         if Patch is an Object:
         *             if Target is not an Object:
         *                 Target = {} # Ignore the contents and set it to an empty Object
         *             for each Name/Value pair in Patch:
         *                 if Value is null:
         *                     if Name exists in Target:
         *                         remove the Name/Value pair from Target
         *                 else:
         *                     Target[Name] = MergePatch(Target[Name], Value)
         *             return Target
         *         else:
         *             return Patch
         */

        Map<String, BeanProperty<Object, Object>> propertiesByName = getIntrospection(target.getClass())
                .getBeanProperties()
                .stream()
                .collect(toMap(bp -> bp.getName(), bp -> (BeanProperty<Object, Object>) bp));

        if (patch.isObject())
        {
            for (Map.Entry<String, JsonNode> entry : patch.entries())
            {
                var name = convToPropertyName(entry.getKey());
                var value = entry.getValue();
                BeanProperty<Object, Object> bp = propertiesByName.get(name);

                if (bp == null)
                {
                    throw new CannotPatchUnknownProperty(target.getClass().getSimpleName(), name);
                }

                if (value.isNull())
                {

                    if (bp.get(target) != null)
                    {
                        if ( !bp.isNullable())
                        {
                            throw new IllegalArgumentException("Cannot make property " + name + " null");
                        }
                    }
                    else
                    {
                        bp.set(target, null);
                    }
                }
                else {
                    // If not an object, assign value to property

                    bp.set(target, conversionService.convert(value, bp.getType()));

                    // If an object, create a new merge patch and apply

                }
            }
        }

        return target;
    }

    private String convToPropertyName(String jsonName)
    {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, jsonName);
    }
}
