import java.awt.* ;
import javax.swing.* ;

public class GUI extends JPanel {

    private final Color BACKGROUND_COLOR = Color.darkGray;
    private final Color RECTANGLE_COLOR = Color.black;
    private final Color BORDER_COLOR = Color.white;
    private final int   TEXT_OFFSET = 6;
    private int width, height, pixels;

    protected GUI(int w, int h, int p) {
        this.width = w ;
        this.height = h ;
        this.pixels = p ;
        setPreferredSize(new Dimension(width*pixels,height*pixels));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g) ;
        g.setColor(BACKGROUND_COLOR) ;
        g.fillRect(0,0,width*pixels,height*pixels) ;
    }

    protected void drawRectangle(int x, int y, int width, int height, String label) {
        Graphics g = this.getGraphics() ;
        g.setColor(RECTANGLE_COLOR) ;
        g.fillRect(x*pixels,y*pixels,width*pixels,height*pixels) ;
        g.setColor(BORDER_COLOR) ;
        g.drawRect(x*pixels,y*pixels,width*pixels,height*pixels) ;
        g.drawString(label,x*pixels+TEXT_OFFSET,y*pixels+2*TEXT_OFFSET) ;
    }
}

