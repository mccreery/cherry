package nukeduck.coinage.util;

import nukeduck.coinage.Constants;

public class FormatUtil {
	public static String prefix(BlockItemName name) {
		return prefix(name.getUnderscored());
	}
	
	public static String prefix(String codeName) {
		return Constants.MODID + ":" + codeName;
	}
}
