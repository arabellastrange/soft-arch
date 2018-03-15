package model;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

public class StaticGameObject extends GameObject {

    public StaticGameObject(LineSegment[] lines, Circle[] circles, double reflectionCoefficient) {
        super(lines, circles, reflectionCoefficient);
    }

    @Override
    protected double timeUntilLineCollision(LineSegment line, Circle ballCircle, Vect ballVelocity) {
        return Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
    }

    @Override
    protected Vect velocityOfLineCollision(LineSegment line, Circle ballCircle, Vect ballVelocity) {
        return Geometry.reflectWall(line, ballVelocity, reflectionCoefficient);
    }

    @Override
    protected double timeUntilCircleCollision(Circle circle, Circle ballCircle, Vect ballVelocity) {
        return  Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
    }

    @Override
    protected Vect velocityOfCircleCollision(Circle circle, Circle ballCircle, Vect ballVelocity) {
        return Geometry.reflectCircle(circle.getCenter(), ballCircle.getCenter(), ballVelocity, reflectionCoefficient);
    }

    @Override
    protected boolean isStatic() {
        return true;
    }

    @Override
    public GameObject clone() {
        return new StaticGameObject(getLines(), getCircles(), reflectionCoefficient);
    }
}
