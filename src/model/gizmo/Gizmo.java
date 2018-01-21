package model.gizmo;

import model.GameObject;

import java.awt.*;

public abstract class Gizmo {

    private Color colour;

    public Gizmo(Color colour){
        this.colour = colour;
    }

    public Color getColour() {
        return colour;
    }

    public abstract GameObject getPhysicsObject();

    public abstract void doAction();
}
