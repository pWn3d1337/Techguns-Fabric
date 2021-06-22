package devutil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.MathHelper;

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
	
	
	public static List<Method> getMethods(@SuppressWarnings("rawtypes") Class clazz, String name){
		List<Method> ret = new LinkedList<Method>();
		Method[] methods = clazz.getDeclaredMethods();
		
		if (name !=null && !name.isEmpty()) {
			for (Method m: methods) {
				if (m.getName().equals(name)) {
					ret.add(m);
				}
			}
		} else {
			return Arrays.asList(methods);
		}
		return ret;
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, ClassNotFoundException {
		//Class<?> PacketHandlerClass = Class.forName("net.minecraft.network.NetworkState$PacketHandler");

		Class c = RenderLayer.class;

		List<Method> methods = getMethods(c, "of");

		System.out.println("L"+c.getCanonicalName().replace(".","/"));
		System.out.println("");

		for (Method m : methods) {
			System.out.println(m.getName()+getSignature(m));
		}
	}
}
