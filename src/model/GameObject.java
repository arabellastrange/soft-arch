package model;

import physics.*;

import java.util.ArrayList;

/**
 * A GameObject is a collection of LineSegments and Circles that
 * represent the physical properties an object in the game.
 */
public abstract class GameObject implements Drawable, Cloneable {

    private LineSegment[] lines;
    private Circle[] circles;

    double reflectionCoefficient;

    public GameObject(LineSegment[] lines, Circle[] circles, double reflectionCoefficient){
        this.lines = lines == null ? new LineSegment[0] : lines;
        this.circles = circles == null ? new Circle[0] : circles;

        this.reflectionCoefficient = reflectionCoefficient;
    }

    public LineSegment[] getLines() {
        return lines;
    }

    public Circle[] getCircles() {
        return circles;
    }

    @Override
    public abstract GameObject clone();

    /**
     * This method moves increases the x-y positions of each component of the object by the given amount.
     *
     * e.g. this.translate( {10, -4} ); will increase the x value by ten and
     * decrease the y value by 4 for every component of this object.
     *
     * @param translation An array of two doubles that represent changes in the x and y directions.
     */
    public GameObject translate(double[] translation) {
        for(int i = 0; i < lines.length; i++){
            double[] newX = {lines[i].p1().x() + translation[0], lines[i].p2().x() + translation[0]};
            double[] newY = {lines[i].p1().y() + translation[1], lines[i].p2().y() + translation[1]};
            lines[i] = new LineSegment(newX[0], newY[0], newX[1], newY[1]);
        }

        for(int i = 0; i < circles.length; i++){
            double newX = circles[i].getCenter().x() + translation[0];
            double newY = circles[i].getCenter().y() + translation[1];
            circles[i] = new Circle(newX, newY, circles[i].getRadius());
        }

        return this;
    }

    public GameObject translate(int[] translation) {
        return translate(new double[]{translation[0], translation[1]});
    }

    public GameObject rotateAroundPointByAngle(Vect vect, Angle angle) {

        for(int i = 0; i < lines.length; i++){
            lines[i] = Geometry.rotateAround(lines[i], vect, angle);
        }

        for(int i = 0; i < circles.length; i++){
            circles[i] = Geometry.rotateAround(circles[i], vect, angle);
        }

        return this;

    }

    @Override
    public DrawingData getDrawingData() {

        DrawingData data = new DrawingData();

        for(LineSegment line : lines){
            ArrayList<Double[]> poly = new ArrayList<>();
            poly.add(new Double[]{line.p1().x(), line.p1().y()});
            poly.add(new Double[]{line.p2().x(), line.p2().y()});
            data.addPolygon(poly);
        }

        for(Circle circle : circles){
            data.addCircle(new Double[]{
                    circle.getCenter().x(),
                    circle.getCenter().y(),
                    circle.getRadius()
            });
        }

        return data;
    }

    public CollisionDetails timeUntilCollisionWithBall(GameObject ball, Vect ballVelocity) {

        Circle ballCircle = ball.getCircles()[0];
        double shortestTime = Double.MAX_VALUE;
        double time;
        Vect newVelocity = new Vect(0, 0);

        for(LineSegment line : lines){
            time = timeUntilLineCollision(line, ballCircle, ballVelocity);
            if(time < shortestTime) {
                shortestTime = time;
                newVelocity = velocityOfLineCollision(line, ballCircle, ballVelocity);
            }
        }

        for(Circle circle: circles){
            time = timeUntilCircleCollision(circle, ballCircle, ballVelocity);
            if(time < shortestTime) {
                shortestTime = time;
                newVelocity = velocityOfCircleCollision(circle, ballCircle, ballVelocity);
            }
        }

        CollisionDetails cd = new CollisionDetails(shortestTime, newVelocity);
        cd.setIsCollidingWithStaticObject(this.isStatic());
        return cd;

    }
    protected abstract double timeUntilLineCollision(LineSegment line, Circle ballCircle, Vect ballVelocity);
    protected abstract Vect velocityOfLineCollision(LineSegment line, Circle ballCircle, Vect ballVelocity);

    protected abstract double timeUntilCircleCollision(Circle circle, Circle ballCircle, Vect ballVelocity);
    protected abstract Vect velocityOfCircleCollision(Circle circle, Circle ballCircle, Vect ballVelocity);

    protected abstract boolean isStatic();

}
