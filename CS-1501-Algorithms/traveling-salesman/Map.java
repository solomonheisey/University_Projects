import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class Map extends JPanel {

    private int width, height;
    public int CITY_SIZE = 10;
    public Color BACKGROUND_COLOR = Color.white;
    public Color CITY_COLOR = Color.red;
    public Color EDGE_COLOR = Color.blue;


    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width,height));
    }

    private void paintCity(City city) {
        Graphics g = this.getGraphics();
        g.setColor(CITY_COLOR);
        int intX = (int)city.x;
        int intY = (int)city.y;
        g.fillOval(intX-CITY_SIZE/2,intY-CITY_SIZE/2,CITY_SIZE,CITY_SIZE);
    }

    private void paintEdge(City city1, City city2) {
        Graphics g = this.getGraphics();
        g.setColor(EDGE_COLOR);
        int city1X, city1Y, city2X, city2Y;
        city1X = (int)city1.x;
        city1Y = (int)city1.y;
        city2X = (int)city2.x;
        city2Y = (int)city2.y;
        g.drawLine(city1X,city1Y,city2X,city2Y);
    }

    public void paintTour(ArrayList<City> tour) {
        for (int c=0 ; c<tour.size()-1 ; c++) {
            paintCity(tour.get(c));
            paintEdge(tour.get(c),tour.get(c+1));
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(BACKGROUND_COLOR);
    }
}
