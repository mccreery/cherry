package com.sammccreery.cherry.registry;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class SpongeClassTransformer implements IClassTransformer {
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if(transformedName.equals("net.minecraft.block.BlockDynamicLiquid")) {
			boolean isObfuscated = !name.equals(transformedName);

			ClassNode node = new ClassNode();
			ClassReader reader = new ClassReader(basicClass);
			reader.accept(node, 0);

			// Obf mappings
			String methodName = isObfuscated ? "o" : "func_149808_o";
			String world = isObfuscated ? "ahb" : "net/minecraft/world/World";
			String blockDynamicLiquid = isObfuscated ? "akr" : "net/minecraft/block/BlockDynamicLiquid";
			String field_149814_b = isObfuscated ? "b" : "field_149814_b";

			for(MethodNode method : node.methods) {
				if(method.name.equals(methodName) && method.desc.equals(String.format("(L%s;III)[Z", world))) {
					InsnList patch = new InsnList();

					/* if(Util.disableSpread(this, world, x, y, z)) {
					 *     Arrays.fill(field_149814_b, false);
					 *     return this.field_149814_b;
					 * } */

					patch.add(new VarInsnNode(Opcodes.ALOAD, 0));
					patch.add(new VarInsnNode(Opcodes.ALOAD, 1));
					patch.add(new VarInsnNode(Opcodes.ILOAD, 2));
					patch.add(new VarInsnNode(Opcodes.ILOAD, 3));
					patch.add(new VarInsnNode(Opcodes.ILOAD, 4));
					patch.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/sammccreery/cherry/util/Util", "disableSpread", String.format("(L%s;L%s;III)Z", blockDynamicLiquid, world), false));
					LabelNode skip = new LabelNode();
					patch.add(new JumpInsnNode(Opcodes.IFEQ, skip));

					// Push field_149814_b twice
					patch.add(new VarInsnNode(Opcodes.ALOAD, 0));
					patch.add(new FieldInsnNode(Opcodes.GETFIELD, blockDynamicLiquid, field_149814_b, "[Z"));
					patch.add(new InsnNode(Opcodes.DUP));

					patch.add(new InsnNode(Opcodes.ICONST_0));
					patch.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/util/Arrays", "fill", "([ZZ)V", false));
					patch.add(new InsnNode(Opcodes.ARETURN));
					patch.add(skip);

					method.instructions.insertBefore(method.instructions.getFirst(), patch);
					break;
				}
			}

			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			node.accept(writer);
			return writer.toByteArray();
		} else {
			return basicClass;
		}
	}
}
