package me.G4meM0ment.DwarvenSkill.Map;

public class Map {
	
	private static int mapX = 20;
	private static int mapY = 20;
	private static int mapZ = 3;
	private static int map2DSize = (mapX) * (mapY);
	private static int mapSize = (mapX) * (mapY) * (mapZ);
	//TODO make variable change if changing z
	private static int z = 1;
	
	public static int getMapX() {
		return mapX;
	}
	public static int getMapY() {
		return mapY;
	}
	public static int getMapZ() {
		return mapZ;
	}
	public static int getMapSize() {
		return mapSize;
	}
	public static int getMap2DSize() {
		return map2DSize;
	}
	public static void setMapSizes() {
		map2DSize = (mapX) * (mapY);
		mapSize = (mapX) * (mapY) * (mapZ);
	}
	public static void setMapX(int mapX) {
		Map.mapX = mapX;
	}
	public static void setMapY(int mapY) {
		Map.mapY = mapY;
	}
	public static void setMapZ(int mapZ) {
		Map.mapZ = mapZ;
	}
	public static int getZ() {
		return z;
	}
	public static void setZ(int z) {
		Map.z = z;
	}
}
