package tk.nukeduck.lightsaber.entity.skills;

import net.minecraft.entity.player.EntityPlayer;
import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.client.ClientEvents;
import tk.nukeduck.lightsaber.entity.ExtendedPropertiesForceSkills;
import cpw.mods.fml.relauncher.Side;

public class SkillHandlerBody extends SkillHandler {
	/** @see SkillHandler#SkillHandler(ForceSkill) */
	public SkillHandlerBody(ForceSkill skill) {
		super(skill);
	}
	
	/** Counter between taking health. */
	public int countDown = 10;
	
	@Override
	public boolean onTick(EntityPlayer player, Side side) {
		if(side == Side.CLIENT) {
			ClientEvents.overlayBodyOpacity = (float) Math.min(1.0D, ClientEvents.overlayBodyOpacity + 0.1D);
		}
		countDown--;
		if(countDown == 0) {
			countDown = 10;
			player.attackEntityFrom(Lightsaber.force, 2F);
			ExtendedPropertiesForceSkills.get(player).addMana((short) 10, player, side);
		}
		return true;
	}
}