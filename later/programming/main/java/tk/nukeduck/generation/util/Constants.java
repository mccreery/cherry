package tk.nukeduck.generation.util;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;

public class Constants {
	public static final String MODID = "alchemy";
	public static final String MOD_NAME = "Generation Mod";
	public static final String VERSION = "1.0";

	public static final float DUNGEON_PITS_WEIGHT = 1.0F;

	public static final short // Flow Control
		FUNCTION =  FuncUtils.pack(BlockCategory.CONTROL, 0),
		IF =        FuncUtils.pack(BlockCategory.CONTROL, 1),
		LOOP =      FuncUtils.pack(BlockCategory.CONTROL, 2),
		WHILE =     FuncUtils.pack(BlockCategory.CONTROL, 3),
		RETURN =    FuncUtils.pack(BlockCategory.CONTROL, 4);

	public static final short // Logic
		AND =       FuncUtils.pack(BlockCategory.LOGIC, 0),
		OR =        FuncUtils.pack(BlockCategory.LOGIC, 1),
		NOT =       FuncUtils.pack(BlockCategory.LOGIC, 2),
		EQUAL =     FuncUtils.pack(BlockCategory.LOGIC, 3),
		NEQUAL =    FuncUtils.pack(BlockCategory.LOGIC, 4),
		GREATER =   FuncUtils.pack(BlockCategory.LOGIC, 5),
		GREATERE =  FuncUtils.pack(BlockCategory.LOGIC, 6),
		LESS =      FuncUtils.pack(BlockCategory.LOGIC, 7),
		LESSE =     FuncUtils.pack(BlockCategory.LOGIC, 8);

	public static final short // Mathematics
		ADD =       FuncUtils.pack(BlockCategory.MATH, 0),
		SUB =       FuncUtils.pack(BlockCategory.MATH, 1),
		MULT =      FuncUtils.pack(BlockCategory.MATH, 2),
		DIV =       FuncUtils.pack(BlockCategory.MATH, 3), 
		POWER =     FuncUtils.pack(BlockCategory.MATH, 4),
		SQUARE =    FuncUtils.pack(BlockCategory.MATH, 5),
		CUBE =      FuncUtils.pack(BlockCategory.MATH, 6),
		SQRT =      FuncUtils.pack(BlockCategory.MATH, 7),
		CURT =      FuncUtils.pack(BlockCategory.MATH, 8),
		ABS =       FuncUtils.pack(BlockCategory.MATH, 9);

	public static final short // Data
		CONSTANT =  FuncUtils.pack(BlockCategory.DATA, 0),
		BLOCK =     FuncUtils.pack(BlockCategory.DATA, 1),
		BLOCK_AT =  FuncUtils.pack(BlockCategory.DATA, 2),
		JOIN_TEXT = FuncUtils.pack(BlockCategory.DATA, 3),
		TEXT_BOX =  FuncUtils.pack(BlockCategory.DATA, 4),
		NUM_BOX =   FuncUtils.pack(BlockCategory.DATA, 5);

	public static final short // Variable
		VAR_SET =   FuncUtils.pack(BlockCategory.VARIABLE, 0),
		VAR_GET =   FuncUtils.pack(BlockCategory.VARIABLE, 1);

	public static final short // Action
		CHAT =      FuncUtils.pack(BlockCategory.ACTION, 0);
}
