package techguns.util;


import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class MathUtil {

	public static final double D2R = Math.PI/180.0;
	public static final double R2D = 180.0/Math.PI;
	
	public static Vec2f polarOffsetXZ(double x, double z, float radius, float angle) {
    	float x_ret = (float) (x + (radius*MathHelper.cos(angle)));
    	float y_ret = (float) (z + (radius*MathHelper.sin(angle)));

    	return new Vec2f(x_ret, y_ret);
    }
	
	public static int randomInt(Random rand, int min, int max) {
    	if (max >= min)
    		return min+rand.nextInt((max-min)+1);
    	else
    		return max+rand.nextInt((min-max)+1);
    }
    
    public static float randomFloat(Random rand, float min, float max) {
    	return min+(rand.nextFloat()*(max-min));
    }

    public static Vec3d rotateVector(Vec3d vec, Vec3d axis, double theta) {
    	double u = axis.x;
    	double v = axis.y;
    	double w = axis.z;
    	
    	double xPrime = u*(u*vec.x + v*vec.y + w*vec.z)*(1d - Math.cos(theta)) 
                + vec.x*Math.cos(theta)
                + (-w*vec.y + v*vec.z)*Math.sin(theta);
		double yPrime = v*(u*vec.x + v*vec.y + w*vec.z)*(1d - Math.cos(theta))
		                + vec.y*Math.cos(theta)
		                + (w*vec.x - u*vec.z)*Math.sin(theta);
		double zPrime = w*(u*vec.x + v*vec.y + w*vec.z)*(1d - Math.cos(theta))
		                + vec.z*Math.cos(theta)
		                + (-v*vec.x + u*vec.y)*Math.sin(theta);
		return new Vec3d(xPrime, yPrime, zPrime);
    }

	public static int clamp(int val, int min, int max) {
		return Math.max(min, Math.min(max, val));
	}

	public static float clamp(float val, float min, float max) {
		return Math.max(min, Math.min(max, val));
	}

	/**
	 * The Vec3d method is client only
	 * @param dir
	 * @param angle
	 * @return
	 */
	public static Vec3d rotateVec3dAroundZ(Vec3d dir, float angle) {
		float f = MathHelper.cos(angle);
		float g = MathHelper.sin(angle);
		double d = dir.x * (double)f + dir.y * (double)g;
		double e = dir.y * (double)f - dir.x * (double)g;
		double h = dir.z;
		return new Vec3d(d, e, h);
	}

	/**
	 * Rodrigues Formula
	 * @param v
	 * @param k
	 * @param theta
	 * @return
	 */
	public static Vec3d rotateVec3dAroundAxis(Vec3d v, Vec3d k, double theta){
		return v.multiply(Math.cos(theta)).add(k.crossProduct(v).multiply(Math.sin(theta))).add(k.multiply(k.dotProduct(v)*(1-Math.cos(theta))));
	}
}
