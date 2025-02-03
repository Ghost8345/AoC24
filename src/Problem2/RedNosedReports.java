package Problem2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RedNosedReports {
    private String path;

    public RedNosedReports(String path){
        this.path = path;
    }

    private List<List<Integer>> getLevels(){

        List<List<Integer>> levels = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            String line;
            while( (line = bufferedReader.readLine()) != null){
                List<Integer> level = Arrays.stream(line.split("\\s+"))
                        .map(Integer::parseInt)
                        .toList();
                levels.add(level);
            }
        } catch (IOException e) {
            System.err.println("File is not found.");
        }

        return levels;
    }

    private boolean isSafeLevel(List<Integer> level){
        boolean safe = true;

        int levelIncreasing = Integer.compare(level.get(1), level.get(0));
        for (int i = 0; i < level.size() - 1; i++) {
            int currentIncreasing = Integer.compare(level.get(i+1), level.get(i));
            if (levelIncreasing != currentIncreasing){
                return false;
            }

            int difference = Math.abs(level.get(i) - level.get(i+1));
            if (difference < 1 || difference > 3)
                return false;
        }

        return safe;
    }


    public int getSafeLevels1(){
        List<List<Integer>> levels = getLevels();
        int safeLevels = 0;

        for (List<Integer> level : levels){
            if (isSafeLevel(level))
                safeLevels++;
        }

        return safeLevels;
    }

    public int getSafeLevelsDampened2() {
        List<List<Integer>> levels = getLevels();
        int safeLevels = 0;

        for (List<Integer> level : levels){
            boolean isSafeDampened = isSafeLevel(level);

            for (int i = 0; i < level.size(); i++) {
                List<Integer> subList = new ArrayList<>(level.subList(0, i));
                subList.addAll(level.subList(i + 1, level.size()));
                isSafeDampened |= isSafeLevel(subList);
            }

            if (isSafeDampened)
                safeLevels++;
        }
        return safeLevels;
    }

}
