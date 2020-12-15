package techguns;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name="techguns_config")
public class TGConfig implements ConfigData {

	public static TGConfig INSTANCE;

	@ConfigEntry.Category("Clientside")
	@ConfigEntry.BoundedDiscrete(min=0,max=20)
	public int cl_sortPassesPerTick = 10; //0-20
	public boolean cl_enableDeathFX = true;
	public boolean cl_enableDeathFX_Gore = true;


}