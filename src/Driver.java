import Problem5.PrintQueue;
import Problem6.GuardGallivant;

public class Driver {

    public static void main(String[] args) {
        GuardGallivant guardGallivant = new GuardGallivant(args[0]);
        System.out.println(guardGallivant.getDistinctGuardPositions1());
        System.out.println(guardGallivant.getObstuctionPositionsForLoop2());;

    }
}
