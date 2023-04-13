import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * A program to draw squares on the screen.
 * 
 * @author Ira Goldstein (Solution)
 * @author 
 * @version Spring 2023
 */

public class RecursiveSquares extends MouseAdapter implements Runnable {

    private Point pressPoint;
    private int side;
    private JPanel panel;

    private ArrayList<Square> squares = new ArrayList<>();

    @Override
    public void run() {
		
		// set up the GUI "look and feel" which should match
		// the OS on which we are running
        JFrame.setDefaultLookAndFeelDecorated(true);
		
		// create a JFrame in which we will build our program,
		// and give the window a name		
        JFrame frame = new JFrame("RecursiveSquares");
        frame.setPreferredSize(new Dimension(600, 600));
		
		// tell the JFrame that when someone closes the
		// window, the application should terminate		
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JPanel with a paintComponent method
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {

                // first, we should call the paintComponent method we are
                // overriding in JPanel
                super.paintComponent(g);

                // if we are "rubber banding", draw that square
                if (pressPoint != null) {
                    //int intSide = (int) side;
					g.drawRect(pressPoint.x, pressPoint.y, side, side);
                }
                // redraw each square
                for (Square c : squares) {
                    c.paint(g);
                }
            }
        };
        frame.add(panel);
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);

        // display the window we've created
        frame.pack();
        frame.setVisible(true);

    }

    @Override
    public void mousePressed(MouseEvent e) {

        pressPoint = e.getPoint();
        side = 0;
        panel.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        side = (int) pressPoint.distance(e.getPoint());
        panel.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        squares.add(new Square(pressPoint, (int) pressPoint.distance(e.getPoint())));
        pressPoint = null;
        panel.repaint();
    }

    public static void main(String args[]) {

        javax.swing.SwingUtilities.invokeLater(new RecursiveSquares());
    }

}
/**
 * The Square class, construct a square that can be drawn
 * on a Graphics area.
 */	
class Square {
	private static final int MIN_SIZE = 5;  //Added
    private Point topLeft;
    private int side;

	/**
	 * Construct a new Square.
	 * 
	 * @param topLeft The top left corner of the square
	 * @param side The length of the sides of the square
	 */
    public Square(Point topLeft, int side) {
        this.topLeft = topLeft;
        this.side = side;
    }


	/**
	 * Recursive method to draw the squares.
	 * 
	 * @param topLeft 	The top left corner
	 * @param side 		The length of the sides of the square
	 * @param g       	The Graphics object on which to draw
	 */
	protected static void drawSquare(Point topLeft, int side, Graphics g) {

		// Draw the squares as long as the sides are at least 5
		if (side >= MIN_SIZE ) {

			g.drawRect(topLeft.x, topLeft.y, side, side);
			int newSide = side / 3;
			// find starting point for new upper right square
			Point upperRight = new Point((topLeft.x + 2 * newSide), topLeft.y);
			
			// find starting point for new lower left square
			Point lowerLeft = new Point(topLeft.x, (topLeft.y + 2 * newSide));

			// find starting point for new lower right square
			Point lowerRight = new Point((topLeft.x + 2 * newSide), (topLeft.y + 2 * newSide));
			
			// starting point for the new upper left square is the same as the original square

			// call the new four smaller squares
			drawSquare(upperRight, newSide, g);
			drawSquare(lowerLeft, newSide, g);
			drawSquare(lowerRight, newSide, g);
			drawSquare(topLeft, newSide, g);

		}
	}

    public void paint(Graphics g) {
		drawSquare(topLeft, side, g);   //Added
//		g.drawRect(topLeft.x, topLeft.y, side, side);

    }
}