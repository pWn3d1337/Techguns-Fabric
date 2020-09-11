package techguns.deatheffects;

/**
 * Server and client side, needed by server to know to send out packets
 *
 */
public class EntityDeathUtils {
	
	/*public static HashMap<DeathType, HashSet<Class<? extends EntityLivingBase>>> entityDeathTypes;

	public static HashSet<Class<? extends EntityLivingBase>> goreMap = new HashSet<>();
	static {
		entityDeathTypes = new HashMap<>();
		//Gore
		
		goreMap.add(EntityPlayer.class);
		goreMap.add(EntityZombie.class);
		goreMap.add(EntitySkeleton.class);
		goreMap.add(EntityEnderman.class);
		goreMap.add(EntityCreeper.class);
		goreMap.add(EntityCow.class);
		goreMap.add(EntitySheep.class);
		goreMap.add(EntityPig.class);
		goreMap.add(EntityChicken.class);
		goreMap.add(EntityPigZombie.class);
		goreMap.add(ZombieSoldier.class);
		goreMap.add(ArmySoldier.class);
		goreMap.add(CyberDemon.class);
		goreMap.add(ZombiePigmanSoldier.class);
		goreMap.add(EntitySpider.class);
		goreMap.add(EntityCaveSpider.class);
		goreMap.add(EntityWitch.class);
		goreMap.add(EntitySlime.class);
		goreMap.add(ZombieFarmer.class);
		goreMap.add(ZombieMiner.class);
		goreMap.add(Bandit.class);
		goreMap.add(ZombieSoldier.class);
		goreMap.add(EntityHorse.class);
		goreMap.add(EntityMooshroom.class);
		goreMap.add(EntityWolf.class);
		goreMap.add(EntitySquid.class);
		//goreMap.add(EntityGhast.class);
		goreMap.add(EntityVillager.class);
		goreMap.add(PsychoSteve.class);
		goreMap.add(DictatorDave.class);
		goreMap.add(SkeletonSoldier.class);
		goreMap.add(AlienBug.class);
		goreMap.add(StormTrooper.class);
		goreMap.add(SuperMutantElite.class);
		goreMap.add(SuperMutantHeavy.class);
		goreMap.add(SuperMutantBasic.class);
		goreMap.add(Outcast.class);
		goreMap.add(Commando.class);
		goreMap.add(Ghastling.class);
		
		goreMap.add(EntityLlama.class);
		goreMap.add(EntityEvoker.class);
		goreMap.add(EntityHusk.class);
		goreMap.add(EntityPolarBear.class);
		goreMap.add(EntityMagmaCube.class);
		goreMap.add(EntityParrot.class);
		goreMap.add(EntityRabbit.class);
		goreMap.add(EntityStray.class);
		goreMap.add(EntitySilverfish.class);
		goreMap.add(EntityVindicator.class);
		goreMap.add(EntityVex.class);
		goreMap.add(EntityShulker.class);
		goreMap.add(EntityWitherSkeleton.class);
		goreMap.add(EntityGhast.class);
		goreMap.add(EntityZombieVillager.class);
		goreMap.add(EntityHorse.class);
		goreMap.add(EntityDonkey.class);
		goreMap.add(EntityMule.class);
		
		
		entityDeathTypes.put(DeathType.GORE, goreMap);
	}*/
	
	/**
	 * Add an entity to the gore death type list
	 * @param clazz
	 */
	/*public static void addEntityToDeathEffectList(Class<? extends EntityLivingBase> clazz) {
		entityDeathTypes.get(DeathType.GORE).add(clazz);
	}*/
	
	/*public static boolean hasSpecialDeathAnim(EntityLivingBase entityLiving, DeathType deathtype) {

		//TEST CODE:
		if (deathtype == DeathType.BIO || deathtype == DeathType.LASER) return true;
		
		//GenericGore
		if (entityDeathTypes.get(DeathType.GORE).contains(entityLiving.getClass())){
			return true;
		}
		
		return false;
		

	}*/
	
    public enum DeathType {
    	DEFAULT(0), GORE(1), BIO(2), LASER(3), DISMEMBER(4);
    	
    	int value;
    	
    	private DeathType(int value) {
    		this.value = value;
    	}
    	
    	public int getValue() {
    		return value;
    	}
    }
}
