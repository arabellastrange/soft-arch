package controller;

import model.*;
import model.gizmo.Gizmo;
import model.gizmo.GizmoPropertyType;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class EditFlipperListener {
    private IModel model;
    private Gizmo gizmo;
    private String color;
    private String di;
    private int x;
    private int y;
    private HashMap<GizmoPropertyType, String> properties = new HashMap<>();

    public EditFlipperListener(Gizmo g, String position, String direction, Color c, Model m){
        model = m;
        gizmo = g;

        color = c.toString();
        color = color.substring(color.indexOf("["));

        String pos = position.replace("(", "");
        pos = pos.replace(")", "");

        String posX = pos.substring(0, pos.indexOf(","));
        String posY = pos.substring(pos.indexOf(","));
        posY = posY.replace(",", "");
        x = Integer.valueOf(posX);
        y = Integer.valueOf(posY);

        if(direction.equals("Left")){
            di = "true";
        } else {
            di = "false";
        }

        edit();

    }

    private void edit() {
        try {
            model.moveGizmo(gizmo.getProperty(GizmoPropertyType.NAME), model.getTileAt(x,y));
            properties.put(GizmoPropertyType.NAME, gizmo.getProperty(GizmoPropertyType.NAME));
            properties.put(GizmoPropertyType.ROTATION_DEG, String.valueOf(0));
            properties.put(GizmoPropertyType.IS_LEFT_ORIENTATED, di);
            properties.put(GizmoPropertyType.CURRENT_COLOUR, color);
            properties.put(GizmoPropertyType.DEFAULT_COLOUR, color);
            properties.put(GizmoPropertyType.ALT_COLOUR, "");
            model.setAllProperties(gizmo.getProperty(GizmoPropertyType.NAME), properties);
        } catch (GizmoNotFoundException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Cannot find gizmo", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (GizmoPlacementNotValidException e) {
            e.printStackTrace();
        } catch (TileCoordinatesNotValid tileCoordinatesNotValid) {
            tileCoordinatesNotValid.printStackTrace();
        }

    }
}
