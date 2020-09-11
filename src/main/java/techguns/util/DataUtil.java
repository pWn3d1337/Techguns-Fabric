package techguns.util;

import java.util.BitSet;

public class DataUtil {

	public static byte compress (boolean... args) {
		if (args.length>8) {
			throw new RuntimeException(new UnsupportedOperationException("Can't store more than 8 booleans into 1 byte!"));
		}

		byte b =0;
		for (int i=0;i<args.length;i++) {
			if (args[i]) {
				b |= 1 << i;
			}
		}
		
		return b;
	}
	
	public static BitSet uncompress(byte data) {
		BitSet b = BitSet.valueOf(new byte[]{data});
		return b;
	}
}
