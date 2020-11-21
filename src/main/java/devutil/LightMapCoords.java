package devutil;

import net.minecraft.util.math.Vec2f;

public class LightMapCoords {

	public static Vec2f int_to_uv(int uv) {
		return new Vec2f(uv & '\uffff', uv >> 16 & '\uffff');
	}
	
	public static int uv_to_int(int u, int v) {
		return u + (v<<16);
	}
	
	public static void main(String[] args) {
		int uv = uv_to_int(240,240);
		Vec2f vec = int_to_uv(uv);
		System.out.println("int: "+uv + " Vec: "+vec.x +","+vec.y);
	}
}
