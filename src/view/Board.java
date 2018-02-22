package view;

import model.Drawable;
import model.GameObject;
import model.Model;
import model.Tile;
import model.gizmo.Absorber;
import model.gizmo.Bumper;
import model.gizmo.Gizmo;
import physics.Circle;
import physics.LineSegment;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Observable;
import java.util.Observer;

public  class Board extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private Model gm;

	Board(int w, int h, Model m) {
		// Observe changes in Model
		m.addObserver(this);
		width = w;
		height = h;
		gm = m;
		this.setOpaque(false);
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
	}

	// Fix onscreen size
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	public void setModel(Model model){ gm = model; }

	public void paintComponent2(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

        for(Drawable drawable : gm.getDrawables()){
            //TODO: This will eventually paint a sprite in position instead of drawing a Shape.
            GameObject obj = drawable.getShapeToDraw();
            if(obj != null){
                //If the object exists, draw it scaled up to pixel size.
                for (LineSegment ls : obj.getLines()) {
                    g2.draw(toPixels(ls.toLine2D()));
                }
                for (Circle circle : obj.getCircles()) {
                    g2.draw(toPixels(circle.toEllipse2D()));
                }
            }
        }

	}

    public void paintComponent(Graphics g) {
	    //Need to separate this form model later
        Graphics2D gg = (Graphics2D) g;
        Tile[][] tiles = gm.getTiles();
        for (int x = 0; x < 20; x++){
            for (int y = 0; y < 20; y++) {
                if(tiles[x][y].getTypeOfGizmo() != null) {
                    switch (tiles[x][y].getTypeOfGizmo()) {
                        case LEFT_FLIPPER:
                            gg.setColor(Color.BLACK);
                            RoundRectangle2D.Double lSquare = new RoundRectangle2D.Double(tiles[x][y].getX(), tiles[x][y].getY(), 2, 0.5, 0.5, 0.5);
                            RoundRectangle2D newLSquare = toPixels(lSquare);
                            gg.fill(newLSquare);
                            break;
                        case RIGHT_FLIPPER:
                            gg.setColor(Color.BLACK);
                            RoundRectangle2D.Double rSquare = new RoundRectangle2D.Double(tiles[x][y].getX() + 1, tiles[x][y].getY(), 2, 0.5, 0.5, 0.5);
                            RoundRectangle2D newRSquare = toPixels(rSquare);
                            gg.fill(newRSquare);
                            break;
                        case CIRCLE_BUMPER:
                            gg.setColor(Color.BLACK);
                            Ellipse2D.Double circle = new Ellipse2D.Double(tiles[x][y].getX(), tiles[x][y].getY(), 1, 1);
                            Ellipse2D newCircle = toPixels(circle);
                            gg.fill(newCircle);
                            break;
                        case SQUARE_BUMPER:
                            gg.setColor(Color.BLACK);
                            Rectangle2D.Double square = new Rectangle2D.Double(tiles[x][y].getX(), tiles[x][y].getY(), 1, 1);
                            Rectangle2D newSquare = toPixels(square);
                            gg.fill(newSquare);
                            break;
                        case TRIANGLE_BUMPER:
                            gg.setColor(Color.BLACK);
							Bumper bumper = (Bumper) tiles[x][y].getGizmo();
//							int[] xpoints = new int[] {tiles[x][y].getX(), tiles[x][y].getX() + 1, tiles[x][y].getX() + 1};
//							int[] ypoints = new int[] {tiles[x][y].getY(), tiles[x][y].getY(), tiles[x][y].getY() + 1};
//
//							Point2D[] points = {new Point(xpoints[0], ypoints[0]), new Point(xpoints[1], ypoints[1]), new Point(xpoints[2], ypoints[2])};
//                         	Point2D[] rotatedPoints = new Point2D[3];
							Polygon triangle = new Polygon(new int[] {tiles[x][y].getX(), tiles[x][y].getX() + 1, tiles[x][y].getX() + 1}, new int[] {tiles[x][y].getY(), tiles[x][y].getY(), tiles[x][y].getY() + 1}, 3);
							Shape shape = AffineTransform.getRotateInstance(bumper.rotation.radians(), toPixels(tiles[x][y].getX()) + toPixels(0.5), toPixels(tiles[x][y].getY()) + toPixels(0.5)).createTransformedShape(toPixels(triangle));

//                            Polygon newTriangle = toPixels(new Polygon(
//                            		new int[] {
//											(int) rotatedPoints[0].getX(),
//											(int) rotatedPoints[1].getX(),
//											(int) rotatedPoints[2].getX()
//									},
//									new int[]{
//											(int) rotatedPoints[0].getX(),
//											(int) rotatedPoints[1].getY(),
//											(int) rotatedPoints[2].getY(),
//									},
//									3
//							));
                            gg.fill(shape);
                            break;
                        case ABSORBER:
                            g.setColor(Color.BLACK);
                            Absorber absorber = (Absorber) tiles[x][y].getGizmo();
                            Rectangle2D.Double absorberShape = new Rectangle2D.Double(tiles[x][y].getX(), tiles[x][y].getY(), absorber.getlength(), absorber.getWidth());
                            Rectangle2D newAbsroberShape = toPixels(absorberShape);
                            gg.fill(newAbsroberShape);
                            break;
                    }
                }
            }
    }

    }


	public Point rotatePoint(Point pt, Point center, double angleDeg) {
		// http://en.wikipedia.org/wiki/Rotation_matrix

		double angleRad = Math.toRadians(angleDeg);
		double cosThetha = Math.cos(angleRad); //The angle COS
		double sinThetha = Math.sin(angleRad); //The angle SIN
		double dx = (pt.x - center.x); //Difference (Point in transformed to origo)
		double dy = (pt.y - center.y); //Difference -- || --

		int ptX = center.x + (int) (dx * cosThetha - dy * sinThetha);
		int ptY = center.y + (int) (dx * sinThetha + dy * cosThetha);

		return new Point(ptX, ptY);
	}

    public class TriangleShape extends Path2D.Double {

        public TriangleShape(double x1, double y1, double x2, double y2, double x3, double y3) {
            moveTo(x1, y1);
            lineTo(x2, y2);
            lineTo(x3, y3);
            closePath();
        }

    }



    private double toPixels(double coord){
	    return coord * 25;
    }

    private Polygon toPixels(Polygon poly){
        int[] newX = poly.xpoints;
        int[] newY = poly.ypoints;
        for(int i = 0; i < poly.npoints; i++){
            newX[i] = (int) toPixels( (double) newX[i]);
            newY[i] = (int) toPixels( (double) newY[i]);
        }
        return new Polygon(newX, newY, poly.npoints);
    }

    private RoundRectangle2D toPixels(RoundRectangle2D rect){
        return new RoundRectangle2D.Double(
                toPixels(rect.getX()),
                toPixels(rect.getY()),
                toPixels(rect.getWidth()),
                toPixels(rect.getHeight()),
                toPixels(rect.getArcWidth()),
                toPixels(rect.getArcHeight())
        );
    }

    private Rectangle2D toPixels(Rectangle2D rect){
        return new Rectangle2D.Double(
                toPixels(rect.getX()),
                toPixels(rect.getY()),
                toPixels(rect.getWidth()),
                toPixels(rect.getHeight())
        );
    }

    private Line2D toPixels(Line2D line){
        return new Line2D.Double(
                toPixels(line.getX1()),
                toPixels(line.getY1()),
                toPixels(line.getX2()),
                toPixels(line.getY2())
        );
    }

    private Ellipse2D toPixels(Ellipse2D ellipse) {

        Rectangle2D originalBounds = ellipse.getBounds2D();
        double newX = toPixels(originalBounds.getX());
        double newY = toPixels(originalBounds.getY());
        double newWidth = toPixels(originalBounds.getWidth());
        double newHeight = toPixels(originalBounds.getHeight());

        return new Ellipse2D.Double(newX, newY, newWidth, newHeight);
    }

    @Override
	public void update(Observable arg0, Object arg1) {
			repaint();
		}
}
