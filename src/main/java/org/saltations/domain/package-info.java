/**
 * JSON Merge Patch implementation
 *
 * <p>This is an implementation of <a href="http://tools.ietf.org/html/rfc7386">RFC 7386 (JSON Merge Patch)</a>.
 *
 * <p>This is suitable for simple patching of JSON Objects. For example:
 *
 * <pre>
 *     { "firstName": "Lana"}
 * </pre>
 *
 * will change the existing first name to 'Lana' or add the first name of 'Lana' if there is not a first name
 * already set.
 *
 * <p>
 * This is recursive. For example:
 *
 * <pre>
 *     { "person" : { "lastName": "Lang" } }
 * </pre>
 *
 * <p>
 * will replace (or add, if not present) the last name of person with 'Lang'
 *
 * <p>
 * and
 * <pre>
 *     { "firstName": null }
 * </pre>
 *
 * will change the first name to a null
 *
 */

package org.saltations.domain;