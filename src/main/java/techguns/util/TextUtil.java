package techguns.util;

import net.minecraft.client.resource.language.I18n;
import techguns.Techguns;

public class TextUtil {

	/**
	 * Trans with prefixing MODID.
	 */
	public static String transTG(String key){
		String trans = I18n.translate(Techguns.MODID+"."+key, new Object[0]);
		return trans;
	}

	public static String trans(String key) {
		String trans = I18n.translate(key, new Object[0]);
		return trans;
	}
}
