package Problem1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistorianHysteria {

    private final String path;

    public HistorianHysteria(String path){
        this.path = path;
    }

    private List<List<Integer>> getArrays() {
        List<List<Integer>> arrays = new ArrayList<>(List.of(new ArrayList<>(), new ArrayList<>()));

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ( (line = bufferedReader.readLine()) != null){
                String[] numbers = line.split("\\s+");
                arrays.get(0).add(Integer.parseInt(numbers[0]));
                arrays.get(1).add(Integer.parseInt(numbers[1]));
            }
        } catch (IOException e) {
            System.err.println("File Not Found, please enter a valid path to a valid text file.");
        }
        return arrays;
    }

    public long getTotalDistance1(){
        List<List<Integer>> arrays = getArrays();

        arrays.get(0).sort(Integer::compareTo);
        arrays.get(1).sort(Integer::compareTo);

        long result = 0L;

        for (int i = 0; i < arrays.get(0).size(); i++) {
            result += Math.abs(arrays.get(0).get(i) - arrays.get(1).get(i));
        }

        return result;
    }

    public long getSimilarityScore2(){
        List<List<Integer>> arrays = getArrays();

        Map<Integer, Integer> occurences = new HashMap<>();

        for (int num : arrays.get(1)) {
            occurences.put(num, occurences.getOrDefault(num, 0) + 1);
        }

        long result = 0L;

        for (int num : arrays.get(0)) {
            int frequency = occurences.getOrDefault(num, 0);
            result += (long) frequency * num;
        }

        return result;
    }

}
