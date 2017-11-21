package tk.nukeduck.lightsaber.entity.skills;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import tk.nukeduck.lightsaber.entity.ExtendedPropertiesForceSkills;
import tk.nukeduck.lightsaber.util.Constants;
import cpw.mods.fml.relauncher.Side;

public class SkillHandlerCloak extends SkillHandler {
	/** Stores the ID value of the Invisibility potion, for a slight performance increase. */
	private static int invis = Potion.invisibility.id;
	
	/** Invisibility effect to restore after the skill is finished, if any. */
	private PotionEffect prevInvis = null;
	
	/** @see SkillHandler#SkillHandler(ForceSkill) */
	public SkillHandlerCloak(ForceSkill skill) {
		super(skill);
	}
	
	/** {@inheritDoc}<br/>
	 * Stores previous Invisibility effect (if any), or starts Invisibility otherwise. */
	@Override
	public void onActivated(EntityPlayer player, Side side) {
		prevInvis = null;
		if(!player.isPotionActive(invis)) {
			player.addPotionEffect(new PotionEffect(invis, 160));
		} else {
			prevInvis = player.getActivePotionEffect(Potion.invisibility);
		}
	}
	
	/** {@inheritDoc}<br/>
	 * Restarts Invisibility effect if it is about to run out.
	 * @return {@code true} if the player has enough mana. */
	@Override
	public boolean onTick(EntityPlayer player, Side side) {
		if(player.isPotionActive(invis) && player.getActivePotionEffect(Potion.invisibility).getDuration() < 10) {
			player.getActivePotionEffect(Potion.invisibility).combine(new PotionEffect(invis, 160));
		}
		if(!ExtendedPropertiesForceSkills.get(player).useMana((short) 1, player, side)) {
			return false;
		}
		return true;
	}
	
	/** {@inheritDoc}<br/>
	 * Removes Invisibility and restores previous effect (if any). */
	@Override
	public void onDeactivated(EntityPlayer player, Side side) {
		player.removePotionEffect(invis);
		if(this.prevInvis != null) {
			player.addPotionEffect(this.prevInvis);
		}
	}
}