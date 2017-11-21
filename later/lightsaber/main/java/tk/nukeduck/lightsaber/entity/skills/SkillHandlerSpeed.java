package tk.nukeduck.lightsaber.entity.skills;

import tk.nukeduck.lightsaber.entity.ExtendedPropertiesForceSkills;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import cpw.mods.fml.relauncher.Side;

public class SkillHandlerSpeed extends SkillHandler {
	/** The direction vector for the player to charge in. */
	private Vec3 direction;
	/** The speed the player should be moved at. */
	private final float speed;
	
	/** @see SkillHandler#SkillHandler(ForceSkill)
	 * @param speed The speed the player should be moved at. */
	public SkillHandlerSpeed(ForceSkill skill, float speed) {
		super(skill);
		this.speed = speed;
	}
	
	/** {@inheritDoc}<br/>
	 * Sets the direction vector to charge in. */
	@Override
	public void onActivated(EntityPlayer player, Side side) {
		this.direction = player.getLookVec();
	}
	
	/** {@inheritDoc}<br/>
	 * Moves the player in the charge direction. */
	@Override
	public boolean onTick(EntityPlayer player, Side side) {
		if(ExtendedPropertiesForceSkills.get(player).useMana((short) 1, player, side)) {
			player.setVelocity(direction.xCoord * speed, player.motionY, direction.zCoord * speed);
			return true;
		}
		return false;
	}
}