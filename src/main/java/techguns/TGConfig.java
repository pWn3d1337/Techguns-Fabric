package techguns;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name="techguns_config")
public class TGConfig implements ConfigData {

	public static TGConfig INSTANCE;

	@ConfigEntry.Category("Clientside")
	@ConfigEntry.BoundedDiscrete(min=0,max=20)
	@Comment("Clientside, Depth sort passes for particles each frame, 0 to deactivate")
	public int cl_sortPassesPerTick = 10; //0-20

	@Comment("Clientside, Enable death effects on kill")
	public boolean cl_enableDeathFX = true;

	@Comment("Clientside, Enable gore death effects on kill")
	public boolean cl_enableDeathFX_Gore = true;


	@ConfigEntry.Category("Items")
	public boolean addCopperIngots = true;

	@ConfigEntry.Gui.Excluded
	public boolean addTinIngots = true;
	@ConfigEntry.Gui.Excluded
	public boolean addBronzeIngots = true;
	@ConfigEntry.Gui.Excluded
	public boolean addSteelIngots = true;
	@ConfigEntry.Gui.Excluded
	public boolean addLeadIngots = true;

	@ConfigEntry.Gui.Excluded
	public boolean addCopperNuggets = true;
	@ConfigEntry.Gui.Excluded
	public boolean addSteelNuggets = true;
	@ConfigEntry.Gui.Excluded
	public boolean addLeadNuggets = true;
}