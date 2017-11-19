package nukeduck.coinage.util;

public class BlockItemName {
	private String[] nameParts;
	
	public BlockItemName(String... nameParts) {
		this.nameParts = nameParts;
	}
	
	public String getCamelCase() {
		if(nameParts.length == 0) return "";
		String l = nameParts[0].toLowerCase();
		for(int i = 1; i < nameParts.length; i++) {
			l += Character.toUpperCase(nameParts[i].charAt(0)) + nameParts[i].substring(1);
		}
		return l;
	}
	
	public String getLowerCase() {
		String l = "";
		for(String part : nameParts) {
			l += part.toLowerCase();
		}
		return l;
	}
	
	public String getUnderscored() {
		String l = nameParts[0].toLowerCase();
		for(int i = 1; i < nameParts.length; i++) {
			l += "_" + nameParts[i].toLowerCase();
		}
		return l;
	}
}
