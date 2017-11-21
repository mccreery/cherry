package tk.nukeduck.lightsaber.entity.skills;

import tk.nukeduck.lightsaber.entity.ExtendedPropertiesForceSkills;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class SkillHandlerSight extends SkillHandler {
	/** Stores the ID value of the Night Vision potion, for a slight performance increase. */
	private static final int vision = Potion.nightVision.id;
	
	/** Night Vision effect to restore after the skill is finished, if any. */
	private PotionEffect prevVision = null;
	
	/** @see SkillHandler#SkillHandler(ForceSkill) */
	public SkillHandlerSight(ForceSkill skill) {
		super(skill);
	}
	
	/** {@inheritDoc}<br/>
	 * Stores previous Night Vision effect (if any), or starts Night Vision otherwise. */
	@Override
	public void onActivated(EntityPlayer player, Side side) {
		prevVision = null;
		
		if(!player.isPotionActive(vision)) {
			player.addPotionEffect(new PotionEffect(vision, 160));
		} else {
			prevVision = player.getActivePotionEffect(Potion.nightVision);
		}
	}
	
	/** {@inheritDoc}<br/>
	 * Restarts Night Vision effect if it is about to run out.
	 * @return {@code true} if the player has enough mana. */
	@Override
	public boolean onTick(EntityPlayer player, Side side) {
		if(ExtendedPropertiesForceSkills.get(player).useMana((short) 5, player, side)) {
			if(player.isPotionActive(vision) && player.getActivePotionEffect(Potion.nightVision).getDuration() < 10) {
				player.getActivePotionEffect(Potion.nightVision).combine(new PotionEffect(vision, 160));
			}
			return true;
		}
		return false;
	}
	
	/** {@inheritDoc}<br/>
	 * Removes Night Vision and restores previous effect (if any). */
	@Override
	public void onDeactivated(EntityPlayer player, Side side) {
		player.removePotionEffect(vision);
		if(this.prevVision != null) {
			player.addPotionEffect(this.prevVision);
		}
	}
}