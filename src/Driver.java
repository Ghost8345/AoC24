import Problem11.PlutonianPebbles;
import Problem12.GardenGroups;
import Problem13.ClawContraption;

public class Driver {

    public static void main(String[] args) {
        ClawContraption clawContraption = new ClawContraption(args[0]);
        System.out.println(clawContraption.getMinTokensForPossiblePrizes1());

    }
}
