package tk.nukeduck.walljump;

public class FallDamageEventHandler
{
	/*@SubscribeEvent
	public void livingFall(LivingHurtEvent event)
	{
	    if(!(event.entityLiving instanceof EntityPlayer)) return;
	    if(!WallJumpTickHandler.isWallJumping) return;
	    if(event.source != DamageSource.fall) return;
	    
	    EntityPlayer eventPlayer = (EntityPlayer)event.entityLiving;
	    
	    event.ammount = (int) Math.round(WallJumpTickHandler.fallDistance) - 1;
	    WallJumpTickHandler.isWallJumping = false;
	    event.entityLiving.fallDistance = 0;
	    event.entityLiving.isAirBorne = false;
	}*/
}
