package techguns.sounds;

import java.util.ArrayList;

import net.minecraft.sound.SoundCategory;

public class TGSoundCategory {

	protected static ArrayList<TGSoundCategory> CATEGORIES = new ArrayList<>();
	
	public static TGSoundCategory GUN_FIRE = new TGSoundCategory("gunfire", SoundCategory.PLAYERS);
	public static TGSoundCategory PLAYER_EFFECT = new TGSoundCategory("playereffect", SoundCategory.PLAYERS);
	public static TGSoundCategory RELOAD = new TGSoundCategory("reload", SoundCategory.PLAYERS);
	public static TGSoundCategory EXPLOSION = new TGSoundCategory("explosion", SoundCategory.MASTER);
	public static TGSoundCategory MACHINE = new TGSoundCategory("machine", SoundCategory.BLOCKS);
	public static TGSoundCategory DEATHEFFECT = new TGSoundCategory("deathFX", SoundCategory.HOSTILE);
	public static TGSoundCategory HOSTILE = new TGSoundCategory("hostile", SoundCategory.HOSTILE);

	protected int id;
	protected String name;
	protected SoundCategory vanillaCategory;
	
	public TGSoundCategory(String name, SoundCategory vanillaCategory) {
		super();
		this.id = CATEGORIES.size();
		this.name = name;
		this.vanillaCategory = vanillaCategory;
		CATEGORIES.add(this);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public SoundCategory getVanillaCategory() {
		return vanillaCategory;
	}

	public static TGSoundCategory get(int index){
		if (index<CATEGORIES.size()){
			return CATEGORIES.get(index);
		}
		return null;
	}
	
}
