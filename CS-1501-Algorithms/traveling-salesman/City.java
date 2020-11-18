public class City {

    String name;
    double x, y;

    City(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    double distance(City c) {
        return ((this.x-c.x)*(this.x-c.x)+(this.y-c.y)*(this.y-c.y));
    }

    public String toString() {
        return name;
    }
}
