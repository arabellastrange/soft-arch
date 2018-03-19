package controller;

import model.GizmoballFileReader;
import view.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MenuActionListener implements ActionListener {

    private MainController controller;
    private JFrame frame;

    MenuActionListener(MainController controller, JFrame frame){
        this.controller = controller;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        AllMouseListeners mouse = controller.getMouseListener();

        mouse.setType(e.getActionCommand());

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("GIZMO FILES", "gizmo");
        fileChooser.setFileFilter(filter);

        switch (e.getActionCommand()){
            case "Load":
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        GizmoballFileReader fileReader = new GizmoballFileReader(selectedFile);
                        controller.setModel(fileReader.getModel());
                        controller.switchToRunView();
                    } catch (Exception ex) {

                        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Loading failed", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case "Save":
                int status = fileChooser.showSaveDialog(null);
                if (status == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        String fileName = selectedFile.getCanonicalPath();
                        String game = controller.getBuildModeSave();
                          if (!fileName.endsWith(".gizmo")) {
                              fileName = fileName + ".gizmo";
                          }
                        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                        writer.write(game);
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            case "Circle":
            case "Square":
            case "Triangle":
                new EditShapeDialogue(frame, e.getActionCommand(), "Add", controller.getModel(), null);
                break;
            case "Ball":
                new EditBallDialogue(frame, "Add", controller.getModel(), null);
                break;
            case "Absorber":
                new EditAbsorberDialogue(frame, "Add", controller.getModel(), null);
                break;
            case "Flipper":
                new EditFlipperDialogue(frame, "Add", controller.getModel(), null);
                break;
            case "Rotate":
            case "Delete":
            case "Edit":
                mouse.setMode("Edit");
                break;
            case "Gravity":
                new GravitySlider(frame, controller.getModel());
                break;
            case "Friction":
                new FrictionSlider(frame, controller.getModel());
                break;
            case "Quit":
                System.exit(0);
                break;
            case "Build":
                controller.switchToBuildView();
                break;
            case "Run":
                controller.switchToRunView();
                break;
        }
    }
}
