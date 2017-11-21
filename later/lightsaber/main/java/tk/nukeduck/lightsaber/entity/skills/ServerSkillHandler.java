package tk.nukeduck.lightsaber.entity.skills;

import java.util.ArrayList;
import java.util.HashMap;

import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.entity.ExtendedPropertiesForceSkills;
import tk.nukeduck.lightsaber.util.Constants;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ServerSkillHandler {
	/** Instance. Use this over {@code new ServerSkillHandler()}. */
	public static ServerSkillHandler instance = new ServerSkillHandler();
	
	/** A {@code HashMap} containing players and the skills they're currently using. */
	public static HashMap<EntityPlayer, SkillHandler> activeSkills = new HashMap<EntityPlayer, SkillHandler>();
	
	/** Stop the current skill (if any) and start the given one.<br/>
	 * This method is not responsible for checking whether or not the player can use the skill. It blindly starts it.
	 * @param player The player to start the skill with.
	 * @param skill The skill to start. */
	public void startSkill(EntityPlayer player, ForceSkill skill) {
		if(activeSkills.get(player) != null) activeSkills.get(player).onDeactivated(player, Side.SERVER);
		activeSkills.put(player, SkillHandler.fromSkill(skill));
		activeSkills.get(player).onActivated(player, Side.SERVER);
	}
	
	/** Stop the current skill of the given player (if any).
	 * @param player The player whose skill to stop. */
	public void endSkill(EntityPlayer player) {
		if(activeSkills.get(player) != null) {
			activeSkills.get(player).onDeactivated(player, Side.SERVER);
			activeSkills.remove(player);
		}
	}
	
	/** Ticks all handlers, and removes any handlers which tick unsuccessfully.<br/>
	 * Also ticks the charge counter. */
	@SubscribeEvent
	public void serverTick(TickEvent.ServerTickEvent e) {
		ArrayList<EntityPlayer> toRemove = new ArrayList<EntityPlayer>();
		for(EntityPlayer player : activeSkills.keySet()) {
			if(!activeSkills.get(player).onTick(player, Side.SERVER)) {
				toRemove.add(player);
			}
		}
		
		for(EntityPlayer player : toRemove) {
			endSkill(player);
		}
		
		chargeCounter++;
		if(chargeCounter == Constants.MANA_RECHARGE_RATE + 1) chargeCounter = 0;
	}
	
	/** Adds mana if the charge counter has reached the maximum. */
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent e) {
		if(chargeCounter == Constants.MANA_RECHARGE_RATE) ExtendedPropertiesForceSkills.get(e.player).addMana((short) 1, e.player, e.side);
	}
	
	/** A counter which is reset each time all players' mana is recharged.<br/>
	 * It maxes out at {@code Constants.MANA_RECHARGE_RATE}. */
	public int chargeCounter;
}