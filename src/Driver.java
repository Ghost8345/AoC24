import Problem1.HistorianHysteria;
import Problem2.RedNosedReports;

public class Driver {

    public static void main(String[] args) {
        RedNosedReports redNosedReports = new RedNosedReports(args[0]);
        System.out.println(redNosedReports.getSafeLevels1());
        System.out.println(redNosedReports.getSafeLevelsDampened2());
    }
}
