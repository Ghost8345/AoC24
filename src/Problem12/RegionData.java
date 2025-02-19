package Problem12;

import java.util.Objects;

public class RegionData {
    private int area;
    private int perimeter;
    private int sides;

    public int getArea() {
        return area;
    }

    public RegionData() {
        this. area = 0;
        this.perimeter = 0;
        this.sides = 0;
    }

    public int getSides() {
        return sides;
    }

    public void incrementSides() {
        this.sides++;
    }

    public void incrementArea() {
        this.area++;
    }

    public int getPerimeter() {
        return perimeter;
    }

    public void incrementPerimeter() {
        this.perimeter++;
    }
}
