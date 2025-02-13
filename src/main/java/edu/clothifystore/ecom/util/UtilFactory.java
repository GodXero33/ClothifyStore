package edu.clothifystore.ecom.util;

import java.util.ArrayList;
import java.util.List;

public class UtilFactory {
	private static final List<Object> objectsPool = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public static <T> T getObject (Class<T> objectClass) {
		final int objectsPoolLength = UtilFactory.objectsPool.size();

		if (objectsPoolLength == 0) {
			final T obj = UtilFactory.createNewObject(objectClass);

			if (obj != null) UtilFactory.objectsPool.add(obj);

			return obj;
		}

		for (final Object obj : UtilFactory.objectsPool) {
			if (obj.getClass().equals(objectClass)) return (T) obj;
		}

		final T obj = UtilFactory.createNewObject(objectClass);

		if (obj != null) UtilFactory.objectsPool.add(obj);

		return obj;
	}

	private static <T> T createNewObject (Class<T> objectClass) {
		try {
			return objectClass.getDeclaredConstructor().newInstance();
		} catch (Exception exception) {
			System.out.println(exception.getMessage());

			return null;
		}
	}
}
