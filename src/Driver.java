import Problem13.ClawContraption;

public class Driver {

    public static void main(String[] args) {
        ClawContraption clawContraption = new ClawContraption(args[0]);
        System.out.println(clawContraption.getMinTokensForPossiblePrizes1());
        System.out.println(clawContraption.getMinTokensForPossiblePrizes2());

    }
}
