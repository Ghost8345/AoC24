import Problem8.ResonantCollinearity;

public class Driver {

    public static void main(String[] args) {
        ResonantCollinearity resonantCollinearity = new ResonantCollinearity(args[0]);
        System.out.println(resonantCollinearity.getUniqueAntinodes1());
        System.out.println(resonantCollinearity.getUniqueAntinodes2());

    }
}
