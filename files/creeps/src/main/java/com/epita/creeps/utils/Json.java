package com.epita.creeps.utils;

import com.google.gson.Gson;

/**
 * Helper class to ease Json {de}serialization.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
public final class Json {

    // Why would you?
    private Json() {
    }

    // We delegate everything to Gson.
    private static final Gson instance = new Gson();

    /**
     * Static wrapper aroung Gson instance to parse JSon strings. Pay attention not to pass object containing circular
     * references to this.
     *
     * @param json
     *         The JSon string to parse.
     * @param decClass
     *         The expected return class.
     * @param <T>
     *         The generic return type.
     *
     * @return An instance of T properly initialized with the values containes in the json argument.
     */
    public static <T> T from(final String json, Class<T> decClass) {
        return instance.fromJson(json, decClass);
    }

    /**
     * Serialize the given source object to JSon.
     *
     * @param source
     *         The object to serialize.
     *
     * @return The serialized object.
     */
    public static String to(final Object source) {
        return instance.toJson(source);
    }
}
