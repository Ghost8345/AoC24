package Problem3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MullItOver {
    private final String path;

    public MullItOver(String path){
        this.path = path;
    }


    private String getMemory() {
        try{
            return Files.readString(Paths.get(path));
        } catch (IOException e) {
            System.err.println("File is not found.");
            return "error";
        }
    }

    private List<String> findAllOccurrences(String text, String patternToFind, int groupIndex) {
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(patternToFind);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            if (matcher.group(groupIndex) != null)
                matches.add(matcher.group(groupIndex));
            else
                matches.add(matcher.group());
        }

        return matches;
    }

    private int getProductOfMul(String match){
        int[] arguments = Arrays.stream(match.split(",")).mapToInt(Integer::parseInt).toArray();
        return arguments[0] * arguments[1];
    }

    public int getSumOfMulInstructions1(){
        String memory = getMemory();
        List<String> matches = findAllOccurrences(memory, "mul\\((\\d+,\\d+)\\)", 1);

        int sumOfProducts = 0;

        for (String match : matches) {
            sumOfProducts += getProductOfMul(match);
        }
        return sumOfProducts;
    }

    public int getSumOfEnabledMulInstructions2(){
        String memory = getMemory();
        List<String> matches = findAllOccurrences(memory, "mul\\((\\d+,\\d+)\\)|(do\\(\\))|(don't\\(\\))", 1);

        int sumOfProducts = 0;
        boolean enabled = true;

        for (String match: matches){
            if (match.startsWith("do("))
                enabled = true;
            else if (match.startsWith("don't("))
                enabled = false;
            else {
                if (!enabled)
                    continue;
                sumOfProducts += getProductOfMul(match);
            }
        }


        return sumOfProducts;
    }
    
}
