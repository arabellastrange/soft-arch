package controller;

import model.Model;
import model.Tile;
import model.TileCoordinatesNotValid;
import model.gizmo.GizmoType;
import view.*;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;

public class FindEditorListener implements MouseListener {
    private Model m;
    private JFrame frame;
    private JPanel panel;

    FindEditorListener(JFrame f, Model model, JPanel p){
        System.out.println("hi");
        m = model;
        frame = f;
        panel = p;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Clicked: " + e.getX() + ", " + e.getY());
        try {
            int offsetx = (frame.getWidth() - panel.getWidth())/2;
            int offsety = (frame.getHeight() - panel.getHeight())/3;

            int x = e.getX() - offsetx;
            int y = e.getY() - offsety;

            int[] xy = getXYNear(x,y);

            System.out.println("Getting tile at: " + xy[0] + ", " +  xy[1]);
            Tile t = m.getTileAt(xy[0], xy[1]);

            if(t.isOccupied()){
               GizmoType g =  t.getGizmo().getType();
               switch (g){
                   case BALL:
                       new EditBallDialogue(frame, "Edit", m);
                       break;
                   case ABSORBER:
                       new EditAbsorberDialogue(frame, "Edit", m);
                   case FLIPPER:
                       new EditFlipperDialogue(frame, "Edit", m);
                   default:
                       new EditShapeDialogue(frame, g.toString(), "Edit", m);
               }
            }
        } catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
            tileCoordinatesNotValid.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public int[] getXYNear(double x, double y){
        Double px = Math.floor(x/25) - 1;
        Double py = Math.floor(y/25) - 1;

        if(px >= 0 && py >= 0){
            return new int[]{px.intValue(),py.intValue()};
        }else {
            return new int[]{0,0};
        }
    }
}
