package com.sammccreery.cherry.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagEnd;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;

public final class NBTUtil {
	private NBTUtil() {}

	private static String expectedError(NBTTagType<?> expected, NBTTagType<?> got, int depth, String... path) {
		return expectedError(expected.getName(), got.getName(), depth, path);
	}
	private static String expectedError(NBTTagType<?> expected, NBTBase got, int depth, String... path) {
		return expectedError(expected.getName(), NBTBase.NBTTypes[got.getId()], depth, path);
	}
	private static String expectedError(String expected, String got, int depth, String... path) {
		String pathS;

		if(depth > 0) {
			StringBuilder builder = new StringBuilder();

			for(int i = 0; i < depth; i++) {
				builder.append('/').append(path[i]);
			}
			pathS = builder.toString();
		} else {
			pathS = "/";
		}

		return String.format("Expected %s, got %s at %s", expected, got, pathS);
	}

	/** @see #ensureCompound(ItemStack, int, String...) */
	public static NBTTagCompound ensureCompound(ItemStack stack, String... path) {
		return ensureCompound(stack, path.length, path);
	}

	/** Finds or creates a compound {@code depth} tags deep into {@code path} */
	private static NBTTagCompound ensureCompound(ItemStack stack, int depth, String... path) {
		NBTTagCompound compound;

		if(stack.hasTagCompound()) {
			compound = stack.getTagCompound();
		} else {
			stack.setTagCompound(compound = new NBTTagCompound());
		}

		for(int i = 0; i < depth; i++) {
			String crumb = path[i];

			if(compound.hasKey(crumb)) {
				NBTBase child = compound.getTag(crumb);
				if(!(child instanceof NBTTagCompound)) {
					throw new IllegalArgumentException(expectedError(NBTTagType.COMPOUND, child, i, path));
				}

				compound = (NBTTagCompound)child;
			} else {
				NBTTagCompound child = new NBTTagCompound();
				compound.setTag(crumb, child);
				compound = child;
			}
		}
		return compound;
	}

	/** Finds or creates a list at {@code path} */
	public static NBTTagList ensureList(ItemStack stack, String... path) {
		if(path.length == 0) {
			throw new IllegalArgumentException(expectedError(NBTTagType.COMPOUND, NBTTagType.LIST, 0));
		}

		NBTTagCompound parent = ensureCompound(stack, path.length-1, path);
		NBTTagList list;

		if(parent.hasKey(path[path.length-1])) {
			NBTBase child = parent.getTag(path[path.length-1]);

			if(!NBTTagType.LIST.equals(child)) {
				throw new IllegalArgumentException(expectedError(NBTTagType.LIST, child, path.length-1, path));
			}
			list = (NBTTagList)child;
		} else {
			list = new NBTTagList();
			parent.setTag(path[path.length-1], list);
		}
		return list;
	}

	/** @see #ensure(ItemStack, NBTBase, String...) */
	public static <T extends NBTBase> T ensure(ItemStack stack, NBTTagType<T> type, String... path) {
		return ensure(stack, type.getTag(), path);
	}

	/** Stores {@code tag} at {@code path} */
	public static <T extends NBTBase> T ensure(ItemStack stack, T tag, String... path) {
		if(path.length > 0) {
			ensureCompound(stack, path.length-1, path).setTag(path[path.length-1], tag);
		} else if(tag instanceof NBTTagCompound) {
			stack.setTagCompound((NBTTagCompound)tag);
		} else {
			throw new IllegalArgumentException(expectedError(NBTTagType.COMPOUND, tag, 0));
		}
		return tag;
	}

	public static final class NBTTagType<T extends NBTBase> {
		public static final NBTTagType<NBTTagEnd>       END        = new NBTTagType<NBTTagEnd>(new NBTTagEnd());
		public static final NBTTagType<NBTTagByte>      BYTE       = new NBTTagType<NBTTagByte>(new NBTTagByte((byte)0));
		public static final NBTTagType<NBTTagShort>     SHORT      = new NBTTagType<NBTTagShort>(new NBTTagShort((short)0));
		public static final NBTTagType<NBTTagInt>       INT        = new NBTTagType<NBTTagInt>(new NBTTagInt(0));
		public static final NBTTagType<NBTTagLong>      LONG       = new NBTTagType<NBTTagLong>(new NBTTagLong(0));
		public static final NBTTagType<NBTTagFloat>     FLOAT      = new NBTTagType<NBTTagFloat>(new NBTTagFloat(0));
		public static final NBTTagType<NBTTagDouble>    DOUBLE     = new NBTTagType<NBTTagDouble>(new NBTTagDouble(0));
		public static final NBTTagType<NBTTagByteArray> BYTE_ARRAY = new NBTTagType<NBTTagByteArray>(new NBTTagByteArray(new byte[0]));
		public static final NBTTagType<NBTTagString>    STRING     = new NBTTagType<NBTTagString>(new NBTTagString());
		public static final NBTTagType<NBTTagList>      LIST       = new NBTTagType<NBTTagList>(new NBTTagList());
		public static final NBTTagType<NBTTagCompound>  COMPOUND   = new NBTTagType<NBTTagCompound>(new NBTTagCompound());
		public static final NBTTagType<NBTTagIntArray>  INT_ARRAY  = new NBTTagType<NBTTagIntArray>(new NBTTagIntArray(new int[0]));

		private final T template;

		private NBTTagType(T template) {
			this.template = template;
		}

		@SuppressWarnings("unchecked")
		public T getTag() {
			return (T)template.copy();
		}

		public byte getId() {
			return template.getId();
		}

		public String getName() {
			return NBTBase.NBTTypes[getId()];
		}

		@Override
		public boolean equals(Object other) {
			return other instanceof NBTBase ? getId() == ((NBTBase)other).getId() : super.equals(other);
		}
	}
}
