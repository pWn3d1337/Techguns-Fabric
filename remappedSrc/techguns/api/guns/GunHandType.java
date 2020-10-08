package techguns.api.guns;

import net.minecraft.util.Formatting;
import techguns.util.TextUtil;

public enum GunHandType {
	TWO_HANDED,
	ONE_HANDED,
	ONE_POINT_FIVE_HANDED;

	@Override
	public String toString() {
		String s="";
		switch(this) {
		case ONE_HANDED:
			s+=Formatting.GREEN;
			break;
		case ONE_POINT_FIVE_HANDED:
			s+=Formatting.YELLOW;
			break;
		case TWO_HANDED:
			s+=Formatting.GOLD;
			break;
		default:
			break;
		}
		return s+TextUtil.transTG("gunhandtype."+super.toString().toLowerCase());
	}
}
