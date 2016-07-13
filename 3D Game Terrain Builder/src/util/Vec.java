package util;

public class Vec {

	public static final Vec2
			RIGHT2 = new Vec2(1,0), UP2 = new Vec2(0,1),
			LEFT2 = new Vec2(-1,0), DOWN2 = new Vec2(0,-1);
	
	public static final Vec3
			RIGHT3 = new Vec3(1,0,0), UP3 = new Vec3(0,1,0),
			LEFT3 = new Vec3(-1,0,0), DOWN3 = new Vec3(0,-1,0),
			FORWARD3 = new Vec3(0,0,1), BACK3 = new Vec3(0,0,-1);
	
	public static final Vec4
			RIGHT4 = new Vec4(1,0,0,0), UP4 = new Vec4(0,1,0,0),
			LEFT4 = new Vec4(-1,0,0,0), DOWN4 = new Vec4(0,-1,0,0),
			FORWARD4 = new Vec4(0,0,1,0), BACK4 = new Vec4(0,0,-1,0);
	
}
