package view;

import controller.EditAbsorberListener;
import controller.MainController;
import controller.PlaceAbsorberListener;
import model.gizmo.Gizmo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditAbsorberDialogue {

    private JPanel panDI;
    private JDialog edit;
    private String startPosition;
    private String widthS;
    private String heightS;
    private Color color;

    public EditAbsorberDialogue(MainController controller, JFrame f, String mode, Gizmo g){
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(getClass().getResource("/Images/fillRectangleSmall.png")));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel spos = new JLabel("Start position (top left): ");
        JTextField sposition = new JTextField("(0,0)");

        JLabel w = new JLabel("Width: ");
        JTextField width = new JTextField();

        JLabel h = new JLabel("Height: ");
        JTextField height = new JTextField();

        JColorChooser shapeColour = new JColorChooser();
        shapeColour.setPreviewPanel(new JPanel()); // removes preview pane;
        shapeColour.setOpaque(false);

        JPanel panShape = new JPanel();
        panShape.setLayout(new GridLayout(0,2));

        JPanel panForm = new JPanel();
        panForm.setLayout(new GridLayout(0,1));
        panForm.setOpaque(false);

        panForm.add(label);
        panForm.add(spos);
        panForm.add(sposition);
        panForm.add(w);
        panForm.add(width);
        panForm.add(h);
        panForm.add(height);

        panShape.add(panForm);
        panShape.add(shapeColour);
        panShape.setOpaque(false);

        JPanel panControls = new JPanel();
        JButton ok = new JButton("OK");

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startPosition = sposition.getText();
                widthS  = width.getText();
                heightS = height.getText();
                color = shapeColour.getColor();
                if(mode.equals("Add")){
                    new PlaceAbsorberListener(controller, startPosition, widthS, heightS, color);
                } else {
                    new EditAbsorberListener(controller, g, startPosition, widthS, heightS, color);
                }
                edit.dispose();
            }
        });

        panControls.add(ok);
        panControls.setOpaque(false);
        panDI = new JPanel();
        panDI.add(panShape);
        panDI.add(panControls);
        panDI.setBackground(Color.ORANGE);
        panDI.setLayout(new BoxLayout(panDI, BoxLayout.Y_AXIS));
        edit =  new JDialog(f, "Ball", true);
        edit.setContentPane(panDI);
        edit.setMinimumSize(new Dimension(900,350));
        edit.setVisible(true);
    }
}
