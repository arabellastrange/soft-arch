package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public class MovingGameObject extends GameObject {

    public MovingGameObject(LineSegment[] lines, Circle[] circles, double reflectionCoefficient) {
        super(lines, circles, reflectionCoefficient);
    }

    @Override
    protected double timeUntilLineCollision(LineSegment line, Circle ballCircle, Vect ballVelocity) {
        return 0;
    }

    @Override
    protected Vect velocityOfLineCollision(LineSegment line, Circle ballCircle, Vect ballVelocity) {
        return null;
    }

    @Override
    protected double timeUntilCircleCollision(Circle circle, Circle ballCircle, Vect ballVelocity) {
        return 0;
    }

    @Override
    protected Vect velocityOfCircleCollision(Circle circle, Circle ballCircle, Vect ballVelocity) {
        return null;
    }

    @Override
    protected boolean isStatic() {
        return false;
    }

    @Override
    public GameObject clone() {
        return new MovingGameObject(getLines(), getCircles(), reflectionCoefficient);
    }
}
