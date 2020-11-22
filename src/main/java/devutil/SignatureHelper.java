package devutil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class SignatureHelper {

	/**
	 * 
	 * 
	 * from https://stackoverflow.com/questions/45072268/how-can-i-get-the-signature-field-of-java-reflection-method-object
	 * @param m
	 * @return
	 */
	public static String getSignature(Method m){
	    String sig;
	    try {
	        Field gSig = Method.class.getDeclaredField("signature");
	        gSig.setAccessible(true);
	        sig = (String) gSig.get(m);
	        if(sig!=null) return sig;
	    } catch (IllegalAccessException | NoSuchFieldException e) { 
	        e.printStackTrace();
	    }

	    StringBuilder sb = new StringBuilder("(");
	    for(Class<?> c : m.getParameterTypes()) 
	        sb.append((sig=Array.newInstance(c, 0).toString())
	            .substring(1, sig.indexOf('@')));
	    String ret = sb.append(')')
	        .append(
	            m.getReturnType()==void.class?"V":
	            (sig=Array.newInstance(m.getReturnType(), 0).toString()).substring(1, sig.indexOf('@'))
	        )
	        .toString();
	    
	    return ret.replace(".", "/");
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		//Method m = LivingEntity.class.getDeclaredMethod("damage", DamageSource.class, float.class);
		Method m = MinecraftClient.class.getDeclaredMethod("disconnect", Screen.class);
		System.out.println(getSignature(m));
	}
}
