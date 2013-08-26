package me.G4meM0ment.DwarvenSkill.Map.Landscape;


public class Landscape {

	private Type type;
	private Shape shape;
	
	public Landscape(Type type, Shape shape) {
		this.type = type;
		this.shape = shape;
	}
	
	public Landscape() {
		this.type = new Type();		
	}

	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	public Shape getShape() {
		return shape;
	}
	public void setShape(Shape shape) {
		this.shape = shape;
	}
}
