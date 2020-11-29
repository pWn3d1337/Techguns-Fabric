package techguns.entities.projectiles;

public interface IProjectileType<T extends Enum<T>> {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends Enum<T>> T fromId(Class<IProjectileType> projectileTypeClass, int id){
		Enum<T>[] values = (Enum<T>[]) projectileTypeClass.getEnumConstants();
		if (id >=0 && id < values.length) {
			return (T) values[id];
		}
		return (T) values[0];
	}
	
	@SuppressWarnings("unchecked")
	public default byte getId(){
		return (byte)((Enum<T>)this).ordinal();
	}
}
