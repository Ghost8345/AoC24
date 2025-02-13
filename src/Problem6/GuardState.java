package Problem6;

import java.util.Objects;

public class GuardState {
    private int locationI;
    private int locationJ;
    private int direction;

    public GuardState(int locationI, int locationJ, int direction) {
        this.locationI = locationI;
        this.locationJ = locationJ;
        this.direction = direction;
    }

    public GuardState(){
        this(0, 0, 0);
    }

    public GuardState(GuardState guardState){
        this(guardState.getLocationI(), guardState.getLocationJ(), guardState.getDirection());
    }

    public int getLocationI() {
        return locationI;
    }

    public void setLocationI(int locationI) {
        this.locationI = locationI;
    }

    public int getLocationJ() {
        return locationJ;
    }

    public void setLocationJ(int locationJ) {
        this.locationJ = locationJ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuardState that = (GuardState) o;
        return locationI == that.locationI && locationJ == that.locationJ && direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationI, locationJ, direction);
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
