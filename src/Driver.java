import Problem11.PlutonianPebbles;

public class Driver {

    public static void main(String[] args) {
        PlutonianPebbles plutonianPebbles = new PlutonianPebbles(args[0]);
        System.out.println(plutonianPebbles.getTotalStonesNum(6));
        System.out.println(plutonianPebbles.getTotalStonesNum(25));
        System.out.println(plutonianPebbles.getTotalStonesNum(75));
    }
}
