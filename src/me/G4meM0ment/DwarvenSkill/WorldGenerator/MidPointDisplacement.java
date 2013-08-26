package me.G4meM0ment.DwarvenSkill.WorldGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.G4meM0ment.DwarvenSkill.Map.Map;

public class MidPointDisplacement {
		
	private int mapX;
	private int mapY;
	private int mapZ;
	private int[][] map;
	private Random r = new Random();
	private int defDisp;
	private double mod;
	private int divX;
	private int divY;
	private int counter = 0;
	private boolean ready;
	
	public MidPointDisplacement() {
		mapX = Map.getMapX();
		mapY = Map.getMapY();
		mapZ = Map.getMapZ();
		map = new int[mapX][mapY];
		defDisp = mapZ/2-1;
		divX = 2;
		divY = 2;
		mod = 1.0;
	}
	
	public int[][] generate() {
		ready = false;
		init();
		int[] a = new int[2];
		int[] b = new int[2];
		int[] c = new int[2];
		int[] d = new int[2];
		a[0] = 1; a[1] = 1;
		b[0] = mapX; b[1] = 1;
		c[0] = mapX; c[1] = mapY;
		d[0] = 1; d[1] = mapY;
		int[] midPoint = findMidPoint(a, b, c, d);		

		List<Diamond> diamonds = new ArrayList<Diamond>();
		diamonds.add(new Diamond(a, b, c, d, midPoint, 0));
		
		int counter = 0;
		while(diamonds.size() > 0)
		{
			if(counter > 2000)
				break;
			counter++;
			Diamond dia = diamonds.get(0);
			int modCount = dia.getMod(); 
			int disp = defDisp;
			while(modCount > 0) {
				disp = modifyDisp(disp);
				modCount--;
			}
			int aX = dia.getA()[0];
			int aY = dia.getA()[1];
			int bX = dia.getB()[0];
			int bY = dia.getB()[1];
			int cX = dia.getC()[0];
			int cY = dia.getC()[1];
			int dX = dia.getD()[0];
			int dY = dia.getD()[1];
			int midPointX = dia.getMid()[0];
			int midPointY = dia.getMid()[1];
			int e[] = new int[2], f[] = new int[2], g[] = new int[2], h[] = new int[2];
			int eX, eY, fX, fY, gX, gY, hX, hY;
			e[0] = eX = bX/2;
			e[1] = eY = bY;
			f[0] = fX = cX;
			f[1] = fY = cY/2;
			g[0] = gX = cX/2;
			g[1] = gY = cY;
			h[0] = hX = dX;
			h[1] = hY = dY/2;
			
			if(eX <= 0 || fY <= 0 || gX <= 0 || hY <= 0) {
				diamonds.remove(0);
				continue;
			}

			map[e[0]-1][e[1]-1] = (map[aX-1][aY-1]+map[bX-1][bY-1]+map[midPoint[0]-1][midPoint[1]-1])/3 + randomDisp(disp);
			map[f[0]-1][f[1]-1] = (map[bX-1][bY-1]+map[cX-1][cY-1]+map[midPoint[0]-1][midPoint[1]-1])/3 + randomDisp(disp);	
			map[g[0]-1][g[1]-1] = (map[cX-1][cY-1]+map[dX-1][dY-1]+map[midPoint[0]-1][midPoint[1]-1])/3 + randomDisp(disp);	
			map[h[0]-1][h[1]-1] = (map[dX-1][dY-1]+map[aX-1][aY-1]+map[midPoint[0]-1][midPoint[1]-1])/3 + randomDisp(disp);
			
			/*if(midPointX-1 == eX || midPointY-1 == eY || midPointX-1 == hX || midPointY-1 == hY || midPointX+1 == fX || midPointY-1 == fY || midPointX+1 == gX || midPointY+1 == gY) {
				diamonds.remove(0);
				continue;
			}*/
			
			//First call
			int[] midPoint1 = new int[2];
			int mpX1, mpY1;
			midPoint1[0] = mpX1 = (aX + ((eX - aX)/2));
			midPoint1[1] = mpY1 = (aY + ((eY - aY)/2));
			
			int height1 = (map[aX-1][aY-1]+map[eX-1][eY-1]+map[midPointX-1][midPointY-1]+map[hX-1][hY-1]) / 4 + randomDisp(disp);
			map[mpX1-1][mpY1-1] = height1;
			diamonds.add(new Diamond(a, e, midPoint, h, midPoint1, modCount++));
			
			//Second call
			int[] midPoint2 = new int[2];
			int mpX2, mpY2;
			midPoint2[0] = mpX2 = (eX + ((bX - eX)/2));
			midPoint2[1] = mpY2 = (eY + ((bY - eY)/2));
			
			int height2 = (map[eX-1][eY-1]+map[bX-1][bY-1]+map[fX-1][fY-1]+map[midPointX-1][midPointY-1]) / 4 + randomDisp(disp);
			map[mpX2-1][mpY2-1] = height2;
			diamonds.add(new Diamond(e, b, f, midPoint, midPoint2, modCount++));
			
			//Third call
			int[] midPoint3 = new int[2];
			int mpX3, mpY3;
			midPoint3[0] = mpX3 = (midPointX + ((fX - midPointX)/2));
			midPoint3[1] = mpY3 = (midPointY + ((fY - midPointY)/2));
			
			int height3 = (map[midPointX-1][midPointY-1]+map[fX-1][fY-1]+map[cX-1][cY-1]+map[gX-1][gY-1]) / 4 + randomDisp(disp);
			map[mpX3-1][mpY3-1] = height3;
			diamonds.add(new Diamond(midPoint, f, c, g, midPoint3, modCount++));
			
			//Fourth call
			int[] midPoint4 = new int[2];
			int mpX4, mpY4;
			midPoint4[0] = mpX4 = (hX + ((midPointX - hX)/2));
			midPoint4[1] = mpY4 = (hY + ((midPointY - hY)/2));
			
			int height4 = (map[hX-1][hY-1]+map[midPointX-1][midPointY-1]+map[gX-1][gY-1]+map[dX-1][dY-1]) / 4 + randomDisp(disp);
			map[mpX4-1][mpY4-1] = height4;
			diamonds.add(new Diamond(h, midPoint, g, d, midPoint4, modCount++));
			
			diamonds.remove(0);
		}
		ready = true;
		
		while(!ready){}
		return map;
	}
	
	private void init() {
		for(int y=0;y<mapY;y++){
			for(int x=0;x<mapX;x++){
				map[x][y]=mapZ/2;
			}
		}
		
		map[0][0] = mapZ/2 + randomDisp(defDisp);
		map[mapX-1][0] = mapZ/2 + randomDisp(defDisp);
		map[mapX-1][mapY-1] = mapZ/2 + randomDisp(defDisp);
		map[0][mapY-1] = mapZ/2 + randomDisp(defDisp);
	}
	
	private int[] findMidPoint(int[] a, int[] b, int[] c, int[] d) {
		int aX = a[0];
		int aY = a[1];
		int bX = b[0];
		int bY = b[1];
		int cX = c[0];
		int cY = c[1];
		int dX = d[0];
		int dY = d[1];
		int[] midPoint = new int[2];
		midPoint[0] = (aX + ((bX - aX)/2));
		midPoint[1] = (aY + ((bY - aY)/2));
		
		int height = (map[aX-1][aY-1]+map[bX-1][bY-1]+map[cX-1][cY-1]+map[dX-1][dY-1]) / 4 + randomDisp(defDisp);
		
		map[midPoint[0]-1][midPoint[1]-1] = height;
		return midPoint;
	}
	
/*	private void doSquare(int[] a, int[] b, int[] c, int[] d, int[] midPoint, int modCount) {
		
		if((double) counter >= (Math.pow((double) 4,(double) ((Math.log(Map.getMap2DSize()))/(Math.log(4))))))
			return;
		else
			counter++;
		
		if(counter >= 200) {
			ready = true;
			return;		
		}

		else
			counter++;
		
		int disp = defDisp;
		while(modCount > 0) {
			disp = modifyDisp(disp);
			modCount--;
		}
		int aX = a[0];
		int aY = a[1];
		int bX = b[0];
		int bY = b[1];
		int cX = c[0];
		int cY = c[1];
		int dX = d[0];
		int dY = d[1];
		int midPointX = midPoint[0];
		int midPointY = midPoint[1];
		int e[] = new int[2], f[] = new int[2], g[] = new int[2], h[] = new int[2];
		int eX, eY, fX, fY, gX, gY, hX, hY;
		e[0] = eX = bX/2;
		e[1] = eY = bY;
		f[0] = fX = cX;
		f[1] = fY = cY/2;
		g[0] = gX = cX/2;
		g[1] = gY = cY;
		h[0] = hX = dX;
		h[1] = hY = dY/2;
		
		if(eX <= 0 || fY <= 0 || gX <= 0 || hY <= 0)
			return;
		
		map[e[0]-1][e[1]-1] = (map[aX-1][aY-1]+map[bX-1][bY-1]+map[midPoint[0]-1][midPoint[1]-1])/3 + randomDisp(disp);
		map[f[0]-1][f[1]-1] = (map[bX-1][bY-1]+map[cX-1][cY-1]+map[midPoint[0]-1][midPoint[1]-1])/3 + randomDisp(disp);	
		map[g[0]-1][g[1]-1] = (map[cX-1][cY-1]+map[dX-1][dY-1]+map[midPoint[0]-1][midPoint[1]-1])/3 + randomDisp(disp);	
		map[h[0]-1][h[1]-1] = (map[dX-1][dY-1]+map[aX-1][aY-1]+map[midPoint[0]-1][midPoint[1]-1])/3 + randomDisp(disp);
		
		if(midPoint[0]-1 == eX || midPoint[1]-1 == eY || midPoint[0]-1 == hX || midPoint[1]-1 == hY || midPoint[0]+1 == fX || midPoint[1]-1 == fY || midPoint[0]+1 == gX || midPoint[1]+1 == gY) {
			ready = true;
			return;
		}
		
		//First call
		int[] midPoint1 = new int[2];
		int mpX1, mpY1;
		midPoint1[0] = mpX1 = (aX + ((eX - aX)/2));
		midPoint1[1] = mpY1 = (aY + ((eY - aY)/2));
		
		int height1 = (map[aX-1][aY-1]+map[eX-1][eY-1]+map[midPointX-1][midPointY-1]+map[hX-1][hY-1]) / 4 + randomDisp(disp);
		map[mpX1-1][mpY1-1] = height1;
		doSquare(a, e, midPoint, h, midPoint1, modCount++);
		
		//Second call
		int[] midPoint2 = new int[2];
		int mpX2, mpY2;
		midPoint2[0] = mpX2 = (eX + ((bX - eX)/2));
		midPoint2[1] = mpY2 = (eY + ((bY - eY)/2));
		
		int height2 = (map[eX-1][eY-1]+map[bX-1][bY-1]+map[fX-1][fY-1]+map[midPointX-1][midPointY-1]) / 4 + randomDisp(disp);
		map[mpX2-1][mpY2-1] = height2;
		doSquare(e, b, f, midPoint, midPoint2, modCount++);
		
		//Third call
		int[] midPoint3 = new int[2];
		int mpX3, mpY3;
		midPoint3[0] = mpX3 = (midPointX + ((fX - midPointX)/2));
		midPoint3[1] = mpY3 = (midPointY + ((fY - midPointY)/2));
		
		int height3 = (map[midPointX-1][midPointY-1]+map[fX-1][fY-1]+map[cX-1][cY-1]+map[gX-1][gY-1]) / 4 + randomDisp(disp);
		map[mpX3-1][mpY3-1] = height3;
		doSquare(midPoint, f, c, g, midPoint3, modCount++);
		
		//Fourth call
		int[] midPoint4 = new int[2];
		int mpX4, mpY4;
		midPoint4[0] = mpX4 = (hX + ((midPointX - hX)/2));
		midPoint4[1] = mpY4 = (hY + ((midPointY - hY)/2));
		
		int height4 = (map[hX-1][hY-1]+map[midPointX-1][midPointY-1]+map[gX-1][gY-1]+map[dX-1][dY-1]) / 4 + randomDisp(disp);
		map[mpX4-1][mpY4-1] = height4;
		doSquare(h, midPoint, g, d, midPoint4, modCount++);
		
		
		
		//int[][][] newPack = new int[4][5][2];
		newPack[0] = doDiamond(a, e, midPoint, h);
		doDiamond(e, b, f, midPoint);
		doDiamond(midPoint, f, c, g);
		doDiamond(h, midPoint, g, d); 
//		return newPack; 
	} */
	
/*	private void doDiamond(int[] a, int[] b, int[] c, int[] d) {
		int aX = a[0];
		int aY = a[1];
		int bX = b[0];
		int bY = b[1];
		int cX = c[0];
		int cY = c[1];
		int dX = d[0];
		int dY = d[1];
//		int[][] packet = new int[5][2];
		int[] midPoint = new int[2];
		int mpX, mpY;
		midPoint[0] = mpX = (aX + ((bX - aX)/2));
		midPoint[1] = mpY = (aY + ((bY - aY)/2));
		
		int height = (map[aX-1][aY-1]+map[bX-1][bY-1]+map[cX-1][cY-1]+map[dX-1][dY-1]) / 4 + randomDisp();
		map[mpX-1][mpY-1] = height;
	
		if(counter >= 100)
			return;
		else
			counter++;
		
		
		if(mpX-1 == aX || mpY-1 == aY || mpX-1 == dX || mpY-1 == dY || mpX+1 == bX || mpY-1 == bY || mpX+1 == cX || mpY+1 == cY
				|| mpX+1 == aX || mpY+1 == aY || mpX+1 == dX || mpY+1 == dY || mpX-1 == bX || mpY+1 == bY || mpX-1 == cX || mpY-1 == cY)
			return;
		else {
			try{
				doSquare(a, b, c, d, midPoint);
			}catch(StackOverflowError e) {
				e.printStackTrace();
			}

		}
		
//		packet[0] = a;
//		packet[1] = b;
//		packet[2] = c;
//		packet[3] = d;
//		packet[4] = midPoint;
		
//		return packet;
	} */
	
	private int randomDisp(int disp) {
		if(disp < 1) disp = 1;
		return r.nextInt(disp);//*2);-disp;
	}
	private int modifyDisp(int disp) {
		disp *= Math.pow(2, -mod);
		return disp;
	}
}
