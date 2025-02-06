import Problem4.CeresSearch;

public class Driver {

    public static void main(String[] args) {
        CeresSearch ceresSearch = new CeresSearch(args[0]);
        System.out.println(ceresSearch.countOccurrences1("XMAS"));
        System.out.println(ceresSearch.countOccurences2("MAS"));
    }
}
