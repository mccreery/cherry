package tk.nukeduck.lightsaber.entity.skills;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import tk.nukeduck.lightsaber.entity.ExtendedPropertiesForceSkills;
import tk.nukeduck.lightsaber.util.Constants;
import cpw.mods.fml.relauncher.Side;

public class SkillHandlerMeditation extends SkillHandler {
	/** Stores the ID value of the Blindness potion, for a slight performance increase. */
	private static final int blindness = Potion.blindness.id;
	/** Stores the ID value of the Slowness potion, for a slight performance increase. */
	private static final int slowness = Potion.moveSlowdown.id;
	
	/** Blindness effect to restore after the skill is finished, if any. */
	private PotionEffect prevBlindness = null;
	/** Slowness effect to restore after the skill is finished, if any. */
	private PotionEffect prevSlowness = null;
	
	/** @see SkillHandler#SkillHandler(ForceSkill) */
	public SkillHandlerMeditation(ForceSkill skill) {
		super(skill);
	}
	
	/** {@inheritDoc}<br/>
	 * Stores previous Slowness and Blindness effects (if any), or starts Slowness and/or Blindness otherwise. */
	@Override
	public void onActivated(EntityPlayer player, Side side) {
		prevSlowness = prevBlindness = null;
		
		if(!player.isPotionActive(blindness)) {
			player.addPotionEffect(new PotionEffect(blindness, 160));
		} else {
			prevBlindness = player.getActivePotionEffect(Potion.blindness);
		}
		if(!player.isPotionActive(slowness)) {
			player.addPotionEffect(new PotionEffect(slowness, 160, 5));
		} else {
			prevSlowness = player.getActivePotionEffect(Potion.moveSlowdown);
		}
	}
	
	/** {@inheritDoc}<br/>
	 * Restarts Slowness and Blindness effects if they are about to run out.
	 * @return {@code true} if the player has enough mana. */
	@Override
	public boolean onTick(EntityPlayer player, Side side) {
		if(player.isPotionActive(blindness) && player.getActivePotionEffect(Potion.blindness).getDuration() < 10) {
			player.getActivePotionEffect(Potion.blindness).combine(new PotionEffect(blindness, 160));
		}
		if(player.isPotionActive(slowness) && player.getActivePotionEffect(Potion.moveSlowdown).getDuration() < 10) {
			player.getActivePotionEffect(Potion.moveSlowdown).combine(new PotionEffect(slowness, 160, 5));
		}
		ExtendedPropertiesForceSkills.get(player).addMana((short) 1, player, side);
		return true;
	}
	
	/** {@inheritDoc}<br/>
	 * Removes Slowness and Blindness and restores previous effects (if any). */
	@Override
	public void onDeactivated(EntityPlayer player, Side side) {
		player.removePotionEffect(blindness);
		player.removePotionEffect(slowness);
		if(this.prevBlindness != null) {
			player.addPotionEffect(this.prevBlindness);
		}
		if(this.prevSlowness != null) {
			player.addPotionEffect(this.prevSlowness);
		}
	}
}