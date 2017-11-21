package tk.nukeduck.lightsaber.entity.skills;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

import org.apache.commons.lang3.ArrayUtils;

import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.entity.ExtendedPropertiesForceSkills;
import tk.nukeduck.lightsaber.input.KeyBindings;
import tk.nukeduck.lightsaber.network.SkillActiveMessage;
import cpw.mods.fml.relauncher.Side;

public class SkillHandler {
	/** All currently registered handlers. */
	private static ArrayList<SkillHandler> handlers = new ArrayList<SkillHandler>();
	
	/** The skill this handler handles. */
	private final ForceSkill skill;
	/** Whether or not the handler is being used. Always false on server-side. */
	private boolean isActive = false;
	
	/** @return The skill this handler handles. */
	public ForceSkill getSkill() {
		return this.skill;
	}
	
	/** @return Whether or not the handler is being used. Always false on server-side. */
	public boolean getIsActive() {
		return this.isActive;
	}
	
	/** Constructor. Also registers the handler, no need to do this manually.
	 * @param skill The skill this handler is used for. */
	public SkillHandler(ForceSkill skill) {
		this.skill = skill;
		register(this);
	}
	
	/** Called when this handler's skill is activated on the given side.
	 * @param player The player that activated the skill.
	 * @param side The side the method is being called on. */
	public void onActivated(EntityPlayer player, Side side) {}
	
	/** Called when this handler's skill is deactivated on the given side.
	 * @param player The player that activated the skill.
	 * @param side The side the method is being called on. */
	public void onDeactivated(EntityPlayer player, Side side) {}
	
	/** Called when this handler's skill is ticked on the given side.
	 * @param player The player that activated the skill.
	 * @param side The side the method is being called on.
	 * @return {@code true} if skill should continue, {@code false} otherwise. */
	public boolean onTick(EntityPlayer player, Side side) {return true;}
	
	/** @param player The player attempting to use the skill.
	 * @return {@code true} if the given player has the required skill for this handler. */
	protected final boolean canPerformAction(EntityPlayer player) {
		ExtendedPropertiesForceSkills prop = ExtendedPropertiesForceSkills.get(player);
		return prop.skillsAttained.get(this.skill);
	}
	
	public static SkillHandler MEDITATION;
	public static SkillHandler SPEED_I, SPEED_II, SPEED_III;
	public static SkillHandler CLOAK;
	public static SkillHandler BODY;
	public static SkillHandler SIGHT;
	public static SkillHandler AURA;
	
	/** Sets values for, and register, all the handlers. */
	public static void initHandlers() {
		MEDITATION = new SkillHandlerMeditation(ForceSkill.MEDITATION);
		SPEED_I = new SkillHandlerSpeed(ForceSkill.SPEED_I, 0.5f);
		SPEED_II = new SkillHandlerSpeed(ForceSkill.SPEED_II, 1f);
		SPEED_III = new SkillHandlerSpeed(ForceSkill.SPEED_III, 1.5f);
		CLOAK = new SkillHandlerCloak(ForceSkill.CLOAK);
		BODY = new SkillHandlerBody(ForceSkill.BODY);
		SIGHT = new SkillHandlerSight(ForceSkill.SIGHT);
		AURA = new SkillHandlerAura(ForceSkill.AURA);
	}
	
	/** Updates all handlers on client-side.<br/>
	 * Also responsible for sending packets to server to start and end skills. */
	public static void update(EntityPlayer player) {
		ExtendedPropertiesForceSkills skills = ExtendedPropertiesForceSkills.get(player);
		if(skills != null) {
			for(SkillHandler handler : handlers) {
				int index = ArrayUtils.indexOf(skills.selected, handler.skill);
				if(index != ArrayUtils.INDEX_NOT_FOUND) {
					if(KeyBindings.useSkills[index].getIsKeyPressed()) {
						if(handler.isActive) {
							if(!handler.onTick(player, Side.CLIENT)) {
								handler.isActive = false;
								handler.onDeactivated(player, Side.CLIENT);
							}
						} else {
							for(int i = 0; i < handlers.size(); i++) {
								if(handlers.get(i).isActive) {
									handlers.get(i).isActive = false;
									handlers.get(i).onDeactivated(player, Side.CLIENT);
								}
							}
							Lightsaber.networkWrapper.sendToServer(new SkillActiveMessage(handler.skill.getId(), true));
							handler.isActive = true;
							handler.onActivated(player, Side.CLIENT);
						}
					} else if(handler.isActive) {
						Lightsaber.networkWrapper.sendToServer(new SkillActiveMessage(handler.skill.getId(), false));
						handler.isActive = false;
						handler.onDeactivated(player, Side.CLIENT);
					}
				}
			}
		}
	}
	
	/** Add a new handler or handlers to the list.<br/>
	 * This is done automatically from the constructor. */
	private static void register(SkillHandler... toAdd) {
		for(SkillHandler handler : toAdd) {
			handlers.add(handler);
			MinecraftForge.EVENT_BUS.register(handler);
		}
	}
	
	/** @param skill The skill to search for.
	 * @return The skill handler that uses the given skill, or {@code null} if one was not found. */
	public static SkillHandler fromSkill(ForceSkill skill) {
		for(SkillHandler handler : handlers)  {
			if(handler.skill == skill) {
				return handler;
			}
		}
		return null;
	}
}