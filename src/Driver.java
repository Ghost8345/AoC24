import Problem10.HoofIt;

public class Driver {

    public static void main(String[] args) {
        HoofIt hoofIt = new HoofIt(args[0]);
        System.out.println(hoofIt.getTotalScoreOfTrailheads1());
        System.out.println(hoofIt.getTotalRatingOfTrailhead2());
    }
}
