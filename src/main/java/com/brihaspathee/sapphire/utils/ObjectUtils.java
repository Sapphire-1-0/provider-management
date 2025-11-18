package com.brihaspathee.sapphire.utils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 14, November 2025
 * Time: 05:57
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.utils
 * To change this template use File | Settings | File and Code Template
 */
public class ObjectUtils {

    public static boolean isEmptyObject(Object obj) {
        return isEmptyObject(obj, new HashSet<>()); // prevent circular loops
    }

    private static boolean isEmptyObject(Object obj, Set<Integer> visited) {

        if (obj == null) return true;

        // Avoid cycles (in case objects reference each other)
        int id = System.identityHashCode(obj);
        if (visited.contains(id)) return true;
        visited.add(id);

        Class<?> clazz = obj.getClass();

        // Strings
        if (obj instanceof String str) {
            return str.isBlank();
        }

        // Numbers, booleans, characters → not empty if present
        if (clazz.isPrimitive() ||
                Number.class.isAssignableFrom(clazz) ||
                Boolean.class.isAssignableFrom(clazz) ||
                Character.class.isAssignableFrom(clazz)) {
            return false; // primitives/wrappers count as non-empty if set
        }

        // Collections
        if (obj instanceof Collection<?> col) {
            if (col.isEmpty()) return true;
            // check nested elements
            return col.stream().allMatch(item -> isEmptyObject(item, visited));
        }

        // Maps
        if (obj instanceof Map<?, ?> map) {
            if (map.isEmpty()) return true;
            // check nested values
            return map.values().stream().allMatch(val -> isEmptyObject(val, visited));
        }

        // If object is an enum → non-empty
        if (clazz.isEnum()) return false;

        // If object is a simple Java type (e.g., java.time.*, etc.)
        if (clazz.getPackage() != null && clazz.getPackage().getName().startsWith("java.")) {
            return false; // treat as meaningful data
        }

        // Now treat it as a POJO → inspect fields recursively
        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(obj);

                if (!isEmptyObject(value, visited)) {
                    return false; // found something non-empty
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return true; // all fields empty
    }
}
