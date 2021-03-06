package model;

import model.gizmo.Bumper;
import model.gizmo.Gizmo;
import model.gizmo.GizmoType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TileTest {

    Tile t;
    Model m;
    Gizmo g;

    @BeforeEach
    void setUp() {
        m = new Model();
        t = new Tile(m,7,8);
    }

    @Test
    public void getX() {
        assertEquals(7,t.getX());
    }

    @Test
    public void getY() {
        assertEquals(8,t.getY());
    }

    @Test
    public void getPosition() {
        int[] pos = {7,8};
        assertEquals(pos[0], t.getPosition()[0]);
        assertEquals(pos[1], t.getPosition()[1]);
    }

    @Test
    public void getNeighbour() throws TileCoordinatesNotValid {
        assertEquals(10, t.getNeighbour(3,0).getX());
    }

    @Test
    public void isOccupied() {
        assertEquals(false, t.isOccupied());
    }

    @Test
    public void setOccupied() throws TileCoordinatesNotValid {
        g  = new Bumper(GizmoType.TRIANGLE_BUMPER, new HashMap<>());
        t.placeGizmo(g);
        assertEquals(true, t.isOccupied());
    }


    @Test
    public void removeGizmo() throws TileCoordinatesNotValid {
        g  = new Bumper(GizmoType.TRIANGLE_BUMPER, new HashMap<>());
        t.placeGizmo(g);
        t.removeGizmo();
        assertEquals(false,t.isOccupied());
    }

    @Test
    public void placeGizmo() throws TileCoordinatesNotValid {
        g = new Bumper(GizmoType.CIRCLE_BUMPER, new HashMap<>());
        t.placeGizmo(g);
        assertEquals(GizmoType.CIRCLE_BUMPER, t.getGizmo().getType());
    }

//    @Test
//    public void placeGizmoOccupied() throws TileCoordinatesNotValid {
//        g = new Bumper(GizmoType.CIRCLE_BUMPER, new HashMap<>());
//        t.placeGizmo(g);
//        assertThrows(Exception.class, () -> t.placeGizmo(g));
//    }

    @Test
    public void getGizmo() {
        assertEquals(g, t.getGizmo());
    }
}