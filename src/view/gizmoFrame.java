package view;

import controller.MainController;
import controller.buildViewListener;
import controller.runViewListener;
import model.Model;

import javax.swing.*;
import java.awt.*;

public class gizmoFrame {
     private JFrame frMain;
     private JMenuBar top;
     private JMenu mFile;
     private JMenu mView;
     private JMenu mTools;
     private Model model;
     private MainController controller;
     private Board board;

    public gizmoFrame(Model m){
        model = m;
        controller = new MainController(model);
        board = new Board(500, 500, model);

        top = new JMenuBar();
        mFile = new JMenu("File");
        mView = new JMenu("View");

        JMenuItem load = new JMenuItem("Load");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem quit = new JMenuItem("Quit");

        JMenuItem run = new JMenuItem("Run View");
        run.addActionListener(new runViewListener(this, board, controller));

        JMenuItem build = new JMenuItem("Build View");
        build.addActionListener(new buildViewListener(this));

        mFile.add(load);
        mFile.add(save);
        mFile.add(quit);

        mView.add(run);
        mView.add(build);

        top.add(mFile);
        top.add(mView);

        gameView view = new runView(controller, board);

        drawFrame(view);
    }


    public void drawFrame(gameView g){
        //open running view by default then user can change to build view
        frMain = new JFrame("Gizmoball");
        frMain.setContentPane(g.getPanel());
        frMain.setJMenuBar(top);
        frMain.setVisible(true);
        frMain.setMinimumSize(new Dimension(550,600));
        frMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void extendMenu(){
        mTools = new JMenu("Tools");

        JMenu add = new JMenu("add a gizmo");

        JMenu shape = new JMenu("add a shape");
        JMenuItem circle = new JMenuItem("circle");
        JMenuItem square = new JMenuItem("square");
        JMenuItem triangle = new JMenuItem("triangle");
        shape.add(circle);
        shape.add(square);
        shape.add(triangle);

        JMenuItem ball = new JMenuItem("add a ball");
        JMenuItem absorber = new JMenuItem("add an absorber");

        add.add(shape);
        add.add(ball);
        add.add(absorber);

        JMenuItem rotate = new JMenuItem("rotate");
        JMenuItem edit = new JMenuItem("edit");

        JMenu settings = new JMenu("settings");
        JMenuItem gravity = new JMenuItem("change gravity");
        JMenuItem friction = new JMenuItem("change friction");
        settings.add(gravity);
        settings.add(friction);

        JMenuItem delete = new JMenuItem("delete");

        mTools.add(add);
        mTools.add(rotate);
        mTools.add(edit);
        mTools.add(delete);
        mTools.addSeparator();
        mTools.add(settings);

        top.add(mTools);

    }

    public void compressMenu(){
        top.remove(mTools);
    }

    public JFrame getFrame() {
        return frMain;
    }

}
