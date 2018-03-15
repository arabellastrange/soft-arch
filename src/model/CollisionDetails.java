package model;

import model.gizmo.Absorber;
import physics.Vect;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class CollisionDetails {

	private double tuc;
	private Vect velocity;
	private Absorber absorber;
	private Collidable collidingWith;
	private boolean isCollidingWithStaticObject;

	public CollisionDetails(double t, Vect v) {
		tuc = t;
		velocity = v;
	}

	public double getTuc() {
		return tuc;
	}
	
	public Vect getVelocity() {
		return velocity;
	}

	public Absorber getAbsorber (){
		return absorber;
	}

	public void setAbsorber (Absorber a)
	{
		absorber = a;
	}

	public boolean isCollidingWithStaticObject(){
		return isCollidingWithStaticObject;
	}

	void setIsCollidingWithStaticObject(boolean bool){
		this.isCollidingWithStaticObject = bool;
	}

	@Override
	public String toString(){
		return "CollisionDetails <" + tuc + "," + velocity + ">";
	}

	public void setCollidingWith(Collidable collidable) {
		this.collidingWith = collidable;
	}

	public Collidable getCollidingWith() throws IllegalStateException {
		if(collidingWith == null)
			throw new IllegalStateException();
		return collidingWith;
	}
}
