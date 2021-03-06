package model.gizmo;

import model.*;
import physics.Angle;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;
import java.util.Map;

public class Bumper extends Gizmo implements Collidable{

    public Bumper(GizmoType type, Map<GizmoPropertyType, String> properties){
        super(properties);
        this.type = type;
    }

    @Override
    public boolean isTilePlacable() {
        return true;
    }

    @Override
    public Tile[] findAnnexedTiles(Tile anchorTile) {
        return new Tile[0];
    }

    @Override
    public GameObject getGameObject() {
        return getPrototypeGameObject()
                .rotateAroundPointByAngle( new Vect(0.5,0.5), new Angle(getCurrentRotationInRadians()) )
                .translate(getPosition());
    }

    @Override
    public Object clone() {
        return super.clone();
    }

    @Override
    public boolean isAbsorber() {
        return false;
    }

    @Override
    public GameObject getPrototypeGameObject() {
        switch (type) {
            case SQUARE_BUMPER:
                return getSquareGameObject();
            case TRIANGLE_BUMPER:
                return getTriangleGameObject();
            case CIRCLE_BUMPER:
                return getCircleGameObject();
            default:
                return null;
        }
    }

    private GameObject getSquareGameObject(){
        LineSegment[] lines = {
                new LineSegment(0, 0, 1, 0), //North
                new LineSegment(1, 0, 1, 1), //West
                new LineSegment(1, 1, 0, 1), //South
                new LineSegment(0, 1, 0, 0) //East
        };

        // These are the circles with radius 0 to help with collisions at the ends of LineSegments.
        Circle[] circles = {
                new Circle(0, 0, 0), //NE corner
                new Circle(1, 0, 0), //NW corner
                new Circle(0, 1, 0), //SE corner
                new Circle(1, 1, 0) //SW corner
        };

        return new StaticGameObject(lines, circles, 1.0);
    }

    private GameObject getTriangleGameObject() {
        LineSegment[] lines = {
                new LineSegment(0, 0, 0, 1), //East
                new LineSegment(0, 0, 1, 0), //North
                new LineSegment(0, 1, 1, 0) //Diagonal
        };

        // These are the circles with radius 0 to help with collisions at the ends of LineSegments.
        Circle[] circles = {
                new Circle(0, 0, 0), //NE corner
                new Circle(0, 1, 0), //SE corner
                new Circle(1, 0, 0) // NW corner
        };

        return new StaticGameObject(lines, circles, 1.0);
    }

    private GameObject getCircleGameObject() {
        Circle[] circles = {
                new Circle(0.5, 0.5, 0.5)
        };
        return new StaticGameObject(null, circles,1.0);
    }

    @Override
    public DrawingData getGizmoDrawingData(){
        DrawingData data = new DrawingData();

        switch (type){
            case CIRCLE_BUMPER:
                data.addCircle(new Double[]{0.5, 0.5, 0.5});
                break;
            case SQUARE_BUMPER:
                ArrayList<Double[]> squarePoly = new ArrayList<>();
                squarePoly.add(new Double[]{0.0,0.0}); //NE
                squarePoly.add(new Double[]{1.0,0.0}); //NW
                squarePoly.add(new Double[]{1.0,1.0}); //SW
                squarePoly.add(new Double[]{0.0,1.0}); //SE
                data.addPolygon(squarePoly);
                break;
            case TRIANGLE_BUMPER:
                ArrayList<Double[]> trianglePoly = new ArrayList<>();
                trianglePoly.add(new Double[]{0.0,0.0}); //NE
                trianglePoly.add(new Double[]{1.0,0.0}); //SW
                trianglePoly.add(new Double[]{0.0,1.0}); //SE
                data.addPolygon(trianglePoly);
                break;
            default:
                break;
        }

        data.rotateAroundPivotByRadians(new double[]{0.5, 0.5}, getCurrentRotationInRadians());

        data.setColour(getProperty(GizmoPropertyType.CURRENT_COLOUR));

        return data;
    }

}
