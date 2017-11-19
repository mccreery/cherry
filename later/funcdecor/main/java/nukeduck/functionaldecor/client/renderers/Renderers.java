package nukeduck.functionaldecor.client.renderers;

public class Renderers {
	private static final DecorRenderer[] renderers = new DecorRenderer[256];
	private static byte id = 0;

	public static final byte registerRenderer(DecorRenderer renderer) {
		renderers[id & 0xFF] = renderer;
		return id++;
	}
	public static final DecorRenderer getRenderer(byte id) {
		return renderers[id & 0xFF];
	}

	public static final byte alarmClock = registerRenderer(new RendererAlarmClock());
	public static final byte lamp = registerRenderer(new RendererLamp());
	public static final byte peripheral = registerRenderer(new RendererPeripheral());
	public static final byte recycleBin = registerRenderer(new RendererRecycleBin());
}
