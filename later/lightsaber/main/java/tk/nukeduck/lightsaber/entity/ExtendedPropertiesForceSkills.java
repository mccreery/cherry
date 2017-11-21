package tk.nukeduck.lightsaber.entity;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.entity.skills.ForceSkill;
import tk.nukeduck.lightsaber.network.ManaMessage;
import tk.nukeduck.lightsaber.network.PointsMessage;
import tk.nukeduck.lightsaber.util.Constants;
import cpw.mods.fml.relauncher.Side;

public class ExtendedPropertiesForceSkills implements IExtendedEntityProperties {
	/** The player these properties are bound to. */
	private EntityPlayer player;
	
	/** The constant identifier for these properties. */
	public static final String NAME = "ForceSkills";
	/** Holds whether or not the player has attained each skill. */
	public Map<ForceSkill, Boolean> skillsAttained;
	
	/** The current progress to the next level, maximum {@code Constants.MAX_PROGRESS}
	 * @see tk.nukeduck.lightsaber.util.Constants#MAX_PROGRESS */
	public byte progressCurrent;
	/** How many points the player has to spend on skills. */
	public short points;
	
	/** How much mana the player currently has to use. */
	public short currentMana;
	/** The maximum capacity of mana on the player's bar. */
	public short maxMana;
	
	/** An array of {@code Constants.MAX_SELECTED} possible skills that can be selected at once.<br/>
	 * If a skill is not present in the slot, its value is {@code null}.
	 * @see tk.nukeduck.lightsaber.util.Constants#MAX_SELECTED */
	public ForceSkill[] selected;
	
	/** Convenience method to register the given player.
	 * @param player The player to apply the properties to. */
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(NAME, new ExtendedPropertiesForceSkills(player));
	}
	
	/** Convenience method to get the properties of the given player.
	 * @param player The player to find the properties of.
	 * @return The properties of the given player. */
	public static final ExtendedPropertiesForceSkills get(EntityPlayer player) {
		return (ExtendedPropertiesForceSkills) player.getExtendedProperties(NAME);
	}
	
	/** Refill the player's mana to full ({@code maxMana}).
	 * @param player The player to add mana to.
	 * @param side The side mana is being added on. Used for packet handling. */
	public void refillMana(EntityPlayer player, Side side) {this.addMana(this.maxMana, player, side);}
	/** Add the specified amount of mana to the player.
	 * @param mana The amount of mana to add.
	 * @param player The player to add mana to.
	 * @param side The side mana is being added on. Used for packet handling. */
	public void addMana(short mana, EntityPlayer player, Side side) {
		this.setMana((short) Math.min(this.maxMana, this.currentMana + mana), player, side);
	}
	
	/** Set the player's mana to the specified amount.
	 * @param mana The amount of mana to set.
	 * @param player The player to set the mana of.
	 * @param side The side mana is being set on. Used for packet handling. */
	public void setMana(short mana, EntityPlayer player, Side side) {
		boolean changed = this.currentMana != mana;
		this.currentMana = mana;
		if(side == Side.SERVER && changed) sendManaMessage(player);
	}
	
	/** Attempt to remove the specified amount of mana to the player.
	 * @param mana The amount of mana to remove.
	 * @param player The player to remove mana from.
	 * @param side The side mana is being removed on. Used for packet handling.
	 * @return {@code true} if the player has enough mana to take away the specified amount, otherwise {@code false}. */
	public boolean useMana(short mana, EntityPlayer player, Side side) {
		if(currentMana >= mana) {
			this.setMana((short) (this.currentMana - mana), player, side);
			return true;
		} else {
			return false;
		}
	}
	
	/** Attempt to send a packet to the given player's client containing mana update info.<br/>
	 * Fails if the player is not a multiplayer entity. */
	public void sendManaMessage(EntityPlayer player) {
		if(player instanceof EntityPlayerMP) Lightsaber.networkWrapper.sendTo(new ManaMessage(this.currentMana, this.maxMana), (EntityPlayerMP) player);
	}
	
	/** Adds progress to the player's skill points ({@code progressCurrent} and {@code points}).<br/>
	 * Carries over a point every time the progress reaches {@code Constants.MAX_PROGRESS}.
	 * @param prog The progress to add.
	 * @param player The player to add progress to.
	 * @see tk.nukeduck.lightsaber.util.Constants#MAX_PROGRESS */
	public void addProgress(byte prog, EntityPlayerMP player) {
		this.progressCurrent += prog;
		if(this.progressCurrent >= Constants.MAX_PROGRESS) {
			this.progressCurrent -= Constants.MAX_PROGRESS;
			this.points++;
		}
		Lightsaber.networkWrapper.sendTo(new PointsMessage(this.progressCurrent, this.points), player);
	}
	
	/** Constructor.<br/>
	 * Initialises the skills the player has, starting with {@code ForceSkill.MEDITATION} and {@code ForceSkill.FOCUS}.
	 * @param player The player to save properties to.
	 * @see tk.nukeduck.lightsaber.entity.skills.ForceSkill#MEDITATION
	 * @see tk.nukeduck.lightsaber.entity.skills.ForceSkill#FOCUS */
	public ExtendedPropertiesForceSkills(EntityPlayer player) {
		this.player = player;
		this.skillsAttained = new HashMap<ForceSkill, Boolean>();
		this.progressCurrent = 0;
		this.points = 0;
		
		for(ForceSkill skill : ForceSkill.skills) {
			this.skillsAttained.put(skill, false);
		}
		this.skillsAttained.put(ForceSkill.MEDITATION, true);
		this.skillsAttained.put(ForceSkill.FOCUS, true);
		
		this.selected = new ForceSkill[5];
		this.updateMaxMana(player);
		this.currentMana = this.maxMana;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound skills = new NBTTagCompound();
		for(ForceSkill skill : ForceSkill.skills) {
			skills.setBoolean(skill.getCodeName(), skillsAttained.get(skill));
		}
		compound.setTag("ForceSkills", skills);
		compound.setByte("ForceExperience", progressCurrent);
		compound.setShort("ForceLevel", points);
		compound.setShort("ForceEnergy", currentMana);
		
		byte[] selectArray = new byte[5];
		for(int i = 0; i < 5; i++) {
			selectArray[i] = this.selected[i] == null ? (byte) Constants.NULL_ID : this.selected[i].getId();
		}
		compound.setByteArray("SelectedSkills", selectArray);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound skills = compound.getCompoundTag("ForceSkills");
		this.progressCurrent = compound.getByte("ForceExperience");
		this.points = compound.getShort("ForceLevel");
		this.currentMana = compound.getShort("ForceEnergy");
		
		this.maxMana = Constants.BASE_MANA;
		for(ForceSkill skill : ForceSkill.skills) {
			skillsAttained.put(skill, skills.getBoolean(skill.getCodeName()));
		}
		
		this.updateMaxMana(this.player);
		
		byte[] selectArray = compound.getByteArray("SelectedSkills");
		for(int i = 0; i < 5; i++) {
			this.selected[i] = ForceSkill.fromId(selectArray[i]);
		}
		
		// Display on server-side the values that have loaded
		String attained = "";
		for(ForceSkill skill : skillsAttained.keySet()) {
			attained += skill.getName() + ":" + skillsAttained.get(skill) + ", ";
		}
		System.out.println("Loaded " + this.player.getDisplayName() + "'s force skills with values: " + progressCurrent + ", " + points + ", " + attained);
	}
	
	/** Set max mana for this player to {@code Constants.BASE_MANA}.<br/>
	 * Then adds {@code Constants.ADDITIONAL_MANA} multiplied by the amount of skills the player already has.
	 * @see tk.nukeduck.lightsaber.util.Constants#BASE_MANA
	 * @see tk.nukeduck.lightsaber.util.Constants#ADDITIONAL_MANA */
	public void updateMaxMana(EntityPlayer player) {
		this.maxMana = Constants.BASE_MANA;
		for(ForceSkill skill : skillsAttained.keySet()) {
			if(skillsAttained.get(skill)) {
				maxMana += Constants.ADDITIONAL_MANA;
			}
		}
	}
	
	/** {@inheritDoc}<br/><br/>
	 * Not used for skills. */
	@Override
	public void init(Entity entity, World world) {} // Practically useless
}