import Problem5.PrintQueue;

public class Driver {

    public static void main(String[] args) {
        PrintQueue printQueue = new PrintQueue(args[0]);
        System.out.println(printQueue.getSumOfMiddleCorrectUpdates1());
        System.out.println(printQueue.getSumOfMiddleIncorrectUpdates2());

    }
}
