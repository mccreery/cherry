package tk.nukeduck.lightsaber.entity.skills;

import java.util.ArrayList;

import tk.nukeduck.lightsaber.client.gui.tome.GuiSkillTreeItem;
import tk.nukeduck.lightsaber.util.Constants;
import tk.nukeduck.lightsaber.util.Strings;

public class ForceSkill {
	public static final ForceSkill MEDITATION = new ForceSkill(Strings.SKILL_MEDITATION);	// You get these two
	public static final ForceSkill FOCUS = new ForceSkill(Strings.SKILL_FOCUS),				// by default, so they're free
		CONTROL_I = new ForceSkill(Strings.SKILL_CONTROL_I)		.setParent(FOCUS)			.setCost((short) 1),
		SPEED_I = new ForceSkill(Strings.SKILL_SPEED_I)			.setParent(CONTROL_I)		.setCost((short) 3),
		SPEED_II = new ForceSkill(Strings.SKILL_SPEED_II)		.setParent(SPEED_I)			.setCost((short) 6),
		SPEED_III = new ForceSkill(Strings.SKILL_SPEED_III)		.setParent(SPEED_II)		.setCost((short) 12),
		CONTROL_II = new ForceSkill(Strings.SKILL_CONTROL_II)	.setParent(CONTROL_I)		.setCost((short) 5),
		CLOAK = new ForceSkill(Strings.SKILL_CLOAK)				.setParent(CONTROL_II)		.setCost((short) 8),
		BODY = new ForceSkill(Strings.SKILL_BODY)				.setParent(CONTROL_II)		.setCost((short) 7),
		SENSE = new ForceSkill(Strings.SKILL_SENSE)				.setParent(FOCUS)			.setCost((short) 2),
		SIGHT = new ForceSkill(Strings.SKILL_SIGHT)				.setParent(SENSE)			.setCost((short) 7),
		AURA = new ForceSkill(Strings.SKILL_AURA)				.setParent(SENSE)			.setCost((short) 10),
		ALTER = new ForceSkill(Strings.SKILL_ALTER)				.setParent(FOCUS)			.setCost((short) 2),
		TELEKINESIS = new ForceSkill(Strings.SKILL_TELEKINESIS)	.setParent(ALTER)			.setCost((short) 5),
		LEAP = new ForceSkill(Strings.SKILL_LEAP)				.setParent(TELEKINESIS)		.setCost((short) 9),
		PUSH = new ForceSkill(Strings.SKILL_PUSH)				.setParent(TELEKINESIS)		.setCost((short) 11),
		PULL = new ForceSkill(Strings.SKILL_PULL)				.setParent(PUSH)			.setCost((short) 15),
		BLAST = new ForceSkill(Strings.SKILL_BLAST)				.setParent(PULL)			.setCost((short) 13),
		FIRE = new ForceSkill(Strings.SKILL_FIRE)				.setParent(ALTER)			.setCost((short) 8);
	
	/** An array filled with all the skills available. */
	public static final ForceSkill[] skills = new ForceSkill[] {
		MEDITATION, FOCUS, CONTROL_I,
		SPEED_I, SPEED_II, SPEED_III,
		CONTROL_II, CLOAK, BODY,
		SENSE, SIGHT, AURA,
		ALTER, TELEKINESIS, LEAP,
		PUSH, PULL, BLAST,
		FIRE
	};
	
	/** @param name The skill name to search for.
	 * @return The skill with the given name, or {@code null} if one was not found. */
	public static ForceSkill fromCodeName(String name) {
		for(ForceSkill skill : skills) {
			if(skill.getCodeName().equals(name)) return skill;
		}
		return null;
	}
	
	/** @param name The skill ID to search for.
	 * @return The skill with the given ID, or {@code null} if one was not found. */
	public static ForceSkill fromId(byte id) {
		for(ForceSkill skill : skills) {
			if(skill.getId() == id) return skill;
		}
		return null;
	}
	
	/** The name of this skill, e.g. {@code "meditation"}. */
	private String name;
	/** The ID of this skill. */
	private byte id;
	/** The ID of this skill's parent. */
	private byte parentId = -1;
	/** The cost of buying this skill. */
	private short cost = 0;
	
	/** An auto-incrementing ID number so that each skill has a unique ID. */
	private static byte autoId = 0;
	
	/** Constructor.<br/>
	 * Auto-increments the ID number given to the skill.
	 * @param name The name to give this skill. */
	public ForceSkill(String name) {
		this.name = name;
		this.id = autoId;
		autoId++;
	}
	
	/** Sets the parent of this skill to the given skill.
	 * @param parent The skill to set as parent.
	 * @return This skill. */
	public ForceSkill setParent(ForceSkill parent) {
		this.parentId = parent.id;
		return this;
	}
	
	/** @return The parent of this skill. */
	public ForceSkill getParent() {
		return this.parentId == -1 ? null : fromId(this.parentId);
	}
	
	/** @return The name of this skill, e.g. {@code "meditation"}. */
	public String getCodeName() {
		return this.name;
	}
	
	/** @return The localised version of the name of this skill. */
	public String getName() {
		return Strings.translate(Strings.getSkill(this.getCodeName()));
	}
	
	/** @return The localised description of this skill. */
	public String getDescriptionRaw() {
		return Strings.translate(Strings.getSkillDesc(this.getCodeName()));
	}
	
	/** @return The localised description of this skill, formatted into lines of length {@code Constants.TOOLTIP_LINE_LENGTH}. 
	 * @see tk.nukeduck.lightsaber.util.Constants#TOOLTIP_LINE_LENGTH */
	public ArrayList<String> getDescription() {
		return Strings.formatLines(this.getDescriptionRaw(), Constants.TOOLTIP_LINE_LENGTH);
	}
	
	/** @return The ID of this skill. */
	public byte getId() {
		return this.id;
	}
	
	/** @return The cost of buying this skill. */
	public short getCost() {
		return this.cost;
	}
	
	/** Sets the cost of this skill to the given cost.
	 * @param cost The cost to set this skill's cost to.
	 * @return This skill. */
	public ForceSkill setCost(short cost) {
		this.cost = cost;
		return this;
	}
}