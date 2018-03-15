package model;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

public class RotatingGameObject extends GameObject {

    private Vect rotationPivot;
    private double angularVelocity;

    public RotatingGameObject(LineSegment[] lines, Circle[] circles, double reflectionCoefficient, Vect rotationPivot, double angularVelocity) {
        super(lines, circles, reflectionCoefficient);
        this.rotationPivot = rotationPivot;
        this.angularVelocity = angularVelocity;
    }

    @Override
    public GameObject translate(double[] translation){
        rotationPivot = new Vect(rotationPivot.x() + translation[0], rotationPivot.y() + translation[1]);
        return super.translate(translation);
    }

    @Override
    protected double timeUntilLineCollision(LineSegment line, Circle ballCircle, Vect ballVelocity) {
        return Geometry.timeUntilRotatingWallCollision(line, rotationPivot, angularVelocity, ballCircle, ballVelocity);
    }

    @Override
    protected Vect velocityOfLineCollision(LineSegment line, Circle ballCircle, Vect ballVelocity) {
        return Geometry.reflectRotatingWall(line, rotationPivot, angularVelocity, ballCircle, ballVelocity, reflectionCoefficient);
    }

    @Override
    protected double timeUntilCircleCollision(Circle circle, Circle ballCircle, Vect ballVelocity) {
        return Geometry.timeUntilRotatingCircleCollision(circle, rotationPivot, angularVelocity, ballCircle, ballVelocity);
    }

    @Override
    protected Vect velocityOfCircleCollision(Circle circle, Circle ballCircle, Vect ballVelocity) {
        return Geometry.reflectRotatingCircle(circle, rotationPivot, angularVelocity, ballCircle, ballVelocity, reflectionCoefficient);
    }

    @Override
    protected boolean isStatic() {
        return false;
    }

    @Override
    public GameObject clone() {
        return new RotatingGameObject(getLines(), getCircles(), reflectionCoefficient, rotationPivot, angularVelocity);
    }
}
