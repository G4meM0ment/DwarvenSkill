package me.G4meM0ment.DwarvenSkill.IngameObject.Being;

import java.util.ArrayList;
import java.util.List;

public class BeingHandler {

	private static List<Being> beings = new ArrayList<Being>(); 
	
	public BeingHandler() {
		
	}
	
	public synchronized List<Being> getAllBeings() {
		return beings;
	}
	public void addBeing(Being b) {
		getAllBeings().add(b);
	}
	public void removeBeing(Being b) {
		b.setId(-1);
	} //TODO create method to remove unused beings (id -1)
	
	public int getBeingListLoc(int id) {
		if(id <= -1)
			return -1;
		return id; //for now ids are 0,1,2,3 ...
	}
	
	public int getUnusedID() {
		return getAllBeings().size()-1; // ids start with 0 list with 0, last being got the the lowest unused id -1
	}
	
	public void removeUnusedBeings() {
		for(int i = 0; i < getAllBeings().size(); i++) {
			Being b = getAllBeings().get(i);
			if(b.getId() <= -1) {
				for(int o = i; o < getAllBeings().size(); o++) {
					Being other = getAllBeings().get(o);
					other.setId(other.getId()-1);
				}
				getAllBeings().remove(i);
			}
		}
	}
}
