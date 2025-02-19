package Problem12;

import java.util.Objects;

public class RegionData {
    private int area;
    private int perimeter;

    public int getArea() {
        return area;
    }

    public RegionData(int area, int perimeter) {
        this.area = area;
        this.perimeter = perimeter;
    }

    public RegionData() {
        this. area = 0;
        this.perimeter = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegionData that = (RegionData) o;
        return area == that.area && perimeter == that.perimeter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(area, perimeter);
    }

    public void setArea(int area) {
        this.area = area;
    }

    public void incrementArea() {
        this.area++;
    }

    public int getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(int perimeter) {
        this.perimeter = perimeter;
    }

    public void incrementPerimeter() {
        this.perimeter++;
    }
}
