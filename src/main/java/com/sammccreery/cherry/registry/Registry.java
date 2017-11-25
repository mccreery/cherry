package com.sammccreery.cherry.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.sammccreery.cherry.util.UniversalName;

public abstract class Registry<T> implements Comparable<Registry<?>> {
	private static final Map<Class<?>, Registry<?>> registers = new HashMap<Class<?>, Registry<?>>();
	private static final Queue<Registry<?>> uninitialized = new PriorityQueue<Registry<?>>();

	public static <T> void addRegister(Class<T> type, Registry<T> register) {
		registers.put(type, register);
		uninitialized.add(register);
	}

	public int getPriority() {return 0;}
	public T registerLocal(T obj) {return registerLocal(obj, null);}
	public abstract T registerLocal(T obj, UniversalName name);
	public abstract void init();

	@SuppressWarnings("unchecked")
	public static final <T> T register(T obj, UniversalName name) {
		System.out.println(registers.keySet());

		for(Class<?> c = obj.getClass(); c != Object.class; c = c.getSuperclass()) {
			if(registers.containsKey(c)) {
				return register((Registry<T>)registers.get(c), obj, name);
			}

			for(Class<?> i : c.getInterfaces()) {
				if(registers.containsKey(i)) {
					return register((Registry<T>)registers.get(i), obj, name);
				}
			}
		}
		return null;
	}

	private static <T> T register(Registry<T> register, T obj, UniversalName name) {
		return name == null ? register.registerLocal(obj) : register.registerLocal(obj, name);
	}

	public static void initAll() {
		for(Registry<?> register : uninitialized) {
			register.init();
		}
	}

	@Override
	public final int compareTo(Registry<?> other) {
		/* this < other --> -
		 * this = other --> 0
		 * this > other --> +
		 * the least value is actually the one with the highest priority */
		return other.getPriority() - getPriority();
	}
}
