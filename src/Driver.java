import Problem11.PlutonianPebbles;
import Problem12.GardenGroups;

public class Driver {

    public static void main(String[] args) {
        GardenGroups gardenGroups = new GardenGroups(args[0]);
        gardenGroups.calculateRegionData();
        System.out.println(gardenGroups.getTotalPrice1());
        System.out.println(gardenGroups.getTotalPrice2());
    }
}
