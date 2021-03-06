package com.sammccreery.cherry.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.sammccreery.cherry.util.Name;

import cpw.mods.fml.common.event.FMLEvent;

public abstract class Registry<T> implements Comparable<Registry<?>> {
	private static final Map<Class<?>, Registry<?>> registers = new HashMap<Class<?>, Registry<?>>();
	private static final Queue<Registry<?>> uninitialized = new PriorityQueue<Registry<?>>();

	public static <T> void addRegister(Class<T> type, Registry<T> register) {
		registers.put(type, register);
		uninitialized.add(register);
	}

	public int getPriority() {return 0;}
	public T registerLocal(T obj) {return registerLocal(obj, null);}
	public abstract T registerLocal(T obj, Name name);
	public abstract void init();

	@SuppressWarnings("unchecked")
	public static final <T> T register(T obj, Name name) {
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

	private static <T> T register(Registry<T> register, T obj, Name name) {
		return name == null ? register.registerLocal(obj) : register.registerLocal(obj, name);
	}

	public static void initAll(FMLEvent e) {
		List<Registry<?>> remaining = new ArrayList<Registry<?>>();

		while(!uninitialized.isEmpty()) {
			Registry<?> registry = uninitialized.remove();

			if(registry.isEventValid(e)) {
				registry.init();
			} else {
				remaining.add(registry);
			}
		}
		uninitialized.addAll(remaining);
	}

	/** @return {@code true} if the  */
	public boolean isEventValid(FMLEvent e) {
		return true;
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
