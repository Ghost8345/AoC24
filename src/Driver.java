import Problem2.RedNosedReports;
import Problem3.MullItOver;

public class Driver {

    public static void main(String[] args) {
        MullItOver mullItOver = new MullItOver(args[0]);
        System.out.println(mullItOver.getSumOfMulInstructions1());
        System.out.println(mullItOver.getSumOfEnabledMulInstructions2());
    }
}
