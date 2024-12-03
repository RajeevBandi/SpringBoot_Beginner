package com.facilitiesportal.util;

import java.lang.reflect.Field;

/**
 * Utility class for copying non-null values from one object to another of the same class.
 */
public class ObjectCopyUtil {

	/**
     * Copies non-null values from the source object to the destination object.
     * 
     * @param <T>         The type of objects being copied.
     * @param source      The source object from which non-null values are copied.
     * @param destination The destination object to which non-null values are copied.
     * @return The destination object with non-null values copied from the source object.
     * @throws IllegalArgumentException If the source and destination objects are not of the same class.
     */
	public static <T> T copyNonNullValues(T source, T destination) {

		if (!source.getClass().equals(destination.getClass())) {
			throw new IllegalArgumentException("Source and destination objects must be of the same class");
		}

		Class<?> sourceClass = source.getClass();
		Field[] fields = sourceClass.getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);

			try {
				Object value = field.get(source);
				if (value != null) {
					field.set(destination, value);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return destination;
	}
}
