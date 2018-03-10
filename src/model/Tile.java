package model;

import model.gizmo.Gizmo;

public class Tile {

    private Model model;
    private int x, y;
    private Gizmo gizmo;
    private boolean occupied;

    Tile(Model model, int x, int y){
        this.model = model;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[] getPosition() {
        return new int[]{x, y};
    }

    public Tile getNeighbour(int xOff, int yOff){
        try {
            return model.getTileAt(x+xOff, y+yOff);
        } catch (TileCoordinatesNotValid e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isOccupied(){
        return occupied;
    }

    public void setOccupied(boolean bool){
        occupied = bool;
    }

    void placeGizmo(Gizmo gizmo){
        this.gizmo = gizmo;
        this.gizmo.setAnchorTile(this);
        occupied = true;
    }

    public void removeGizmo(){
        if(gizmo != null) {
            gizmo.removeTile();
            this.gizmo = null;
            occupied = false;
        }
    }

    public Gizmo getGizmo() {
        return gizmo;
    }

    @Override
    public String toString(){
        return "<Tile(" + x + "," + y + ")>";
    }

}
