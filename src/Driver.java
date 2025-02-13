import Problem5.PrintQueue;
import Problem6.GuardGallivant;
import Problem7.BridgeRepair;

public class Driver {

    public static void main(String[] args) {
        BridgeRepair bridgeRepair = new BridgeRepair(args[0]);
        System.out.println(bridgeRepair.getSumOfCorrectEquations());

    }
}
