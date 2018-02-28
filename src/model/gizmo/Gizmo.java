package model.gizmo;

import model.*;

import java.awt.*;
import java.util.Map;

public abstract class Gizmo implements GizmoEventListener, Collidable, Drawable {

    private Color colour;
    private Tile anchorTile;
    private Tile[] annexedTiles;

    protected GizmoType type;

    private Map<GizmoPropertyType, String> properties;

    Gizmo(Color colour, Map<GizmoPropertyType, String> props){
        properties = props;
        this.colour = colour;
    }

    public Color getColour() {
        return colour;
    }

    public String getProperty(GizmoPropertyType prop){
        return properties.getOrDefault(prop, null);
    }

    public void setProperty(GizmoPropertyType prop, String val) throws GizmoPropertyException {
        if(properties.containsKey(prop)){
            properties.put(prop, val);
        } else {
            throw new GizmoPropertyException("Attempted to set property that has not previously been set at compile time" +
                    "\n Property: " + prop +
                    "\n Value: " + val);
        }
    }

    public void setAnchorTile(Tile anchorTile) {
        this.anchorTile = anchorTile;
        setAnnexedTiles(findAnnexedTiles(anchorTile));
    }

    private void setAnnexedTiles(Tile[] tiles){
        annexedTiles = tiles;
        for(Tile t : annexedTiles){
            t.setOccupied(true);
        }
    }

    private void removeAnnexedTiles(){
        for(Tile t : annexedTiles){
            t.setOccupied(false);
        }
        annexedTiles = null;
    }

    public void removeTile(){
        removeAnnexedTiles();
        this.anchorTile = null;
    }

    public double[] getPosition(){
        if(anchorTile != null)
            return new double[] {anchorTile.getPosition()[0], anchorTile.getPosition()[1]};
        else
            return null;
    }

    public void rotateBy_Deg(double val) throws GizmoPropertyException {
        double rotation = Double.valueOf(getProperty(GizmoPropertyType.ROTATION_DEG));
        rotation = (rotation + val) % 360;
        setRotation_Deg(rotation);
    }

    public void setRotation_Deg(double val) throws GizmoPropertyException {
        setProperty(GizmoPropertyType.ROTATION_DEG, String.valueOf(val));
    }

    public DrawingData getDrawingData(){
        return this.getGizmoDrawingData().translate(getPosition());
    }

    public abstract Tile[] findAnnexedTiles(Tile anchorTile);

    public abstract GameObject getPrototypeGameObject();
    public abstract GameObject getGameObject();

    protected abstract DrawingData getGizmoDrawingData();




    public static String[] getPropertyDefaults(GizmoType type){
        switch (type){
            case FLIPPER:
                //Name, Rotation_Deg
                return new String[]{ "leftFlipper0", "0", "true" };
            case BALL:
                //Name, Vel_X, Vel_Y
                return new String[]{ "ball0", "0", "0" };
            case CIRCLE_BUMPER:
                //Name, Rotation_Deg
                return new String[]{ "circleBumper0", "0" };
            case SQUARE_BUMPER:
                //Name, Rotation_Deg
                return new String[]{ "squareBumper0", "0" };
            case TRIANGLE_BUMPER:
                //Name, Rotation_Deg
                return new String[]{ "triangleBumper0", "0" };
            case ABSORBER:
                //Name, width, height
                return new String[]{ "absorber0", "20", "1" };
        }

        return null;
    }
}
