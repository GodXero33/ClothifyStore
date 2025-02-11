package edu.clothifystore.ecom.util;

import java.util.ArrayList;
import java.util.List;

public class UtilFactory {
	private static UtilFactory instance;

	private final List<Object> objectsPool;

	private UtilFactory () {
		this.objectsPool = new ArrayList<>();
	}

	public static UtilFactory getInstance () {
		if (UtilFactory.instance == null) UtilFactory.instance = new UtilFactory();

		return UtilFactory.instance;
	}

	@SuppressWarnings("unchecked")
	public <T> T getObject (Class<T> objectClass) {
		final int objectsPoolLength = this.objectsPool.size();

		if (objectsPoolLength == 0) {
			final T obj = this.createNewObject(objectClass);

			if (obj != null) this.objectsPool.add(obj);

			return obj;
		}

		for (final Object obj : this.objectsPool) {
			if (obj.getClass().equals(objectClass)) return (T) obj;
		}

		final T obj = this.createNewObject(objectClass);

		if (obj != null) this.objectsPool.add(obj);

		return obj;
	}

	private <T> T createNewObject (Class<T> objectClass) {
		try {
			return objectClass.getDeclaredConstructor().newInstance();
		} catch (Exception exception) {
			System.out.println(exception.getMessage());

			return null;
		}
	}
}
