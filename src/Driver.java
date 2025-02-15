import Problem9.DiskFragmenter;

public class Driver {

    public static void main(String[] args) {
        DiskFragmenter diskFragmenter = new DiskFragmenter(args[0]);
        System.out.println(diskFragmenter.getChecksum1());
        System.out.println(diskFragmenter.getChecksum2());
    }
}
