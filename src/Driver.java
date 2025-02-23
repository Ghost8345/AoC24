import Problem14.RestroomRedoubt;

public class Driver {

    public static void main(String[] args) {
        RestroomRedoubt restroomRedoubt = new RestroomRedoubt(args[0]);
//        System.out.println(restroomRedoubt.getSafetyFactor1(100));
        restroomRedoubt.writeGridsToFile(7772, 3);

    }
}
