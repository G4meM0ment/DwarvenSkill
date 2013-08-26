package me.G4meM0ment.DwarvenSkill.WorldGenerator;

public class Diamond {
	
	private int[] a, b, c, d, mid;
	private int mod;
	public Diamond(int[] a, int[] b, int[] c, int[] d, int[] midPoint, int mod) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.mid = midPoint;
		this.mod = mod;
	}
	
	public int getMod() {
		return mod;
	}
	public void setMod(int mod) {
		this.mod = mod;
	}
	public int[] getA() {
		return a;
	}
	public void setA(int[] a) {
		this.a = a;
	}
	public int[] getB() {
		return b;
	}
	public void setB(int[] b) {
		this.b = b;
	}
	public int[] getC() {
		return c;
	}
	public void setC(int[] c) {
		this.c = c;
	}
	public int[] getD() {
		return d;
	}
	public void setD(int[] d) {
		this.d = d;
	}
	public int[] getMid() {
		return mid;
	}
	public void setMid(int[] mid) {
		this.mid = mid;
	}

}
