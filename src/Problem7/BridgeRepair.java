package Problem7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BridgeRepair {
    private final String path;
    private final List<Deque<Long>> equations;
    private final List<Long> sums;


    public BridgeRepair(String path){
        this.path = path;
        equations = new ArrayList<>();
        sums = new ArrayList<>();
        getInput();
    }

    public void getInput(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ( (line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(":");
                String sum = splitLine[0];
                String numsString = splitLine[1].trim();
                sums.add(Long.parseLong(sum));
                String[] numbersSplitted = numsString.split("\\s+");

                Deque<Long> deque = new LinkedList();
                for (String number : numbersSplitted){
                    deque.addLast(Long.parseLong(number));
                }
                equations.add(deque);
            }
        } catch (IOException exception){
            System.err.println(exception.getMessage());
        }
    }

    private boolean isCorrectEquation(Deque<Long> deque, long sum){
        if (deque.size() == 1) {
            return deque.peekFirst().equals(sum);
        }
        if (deque.peekFirst() > sum)
            return false;

        long first = deque.removeFirst();
        long second = deque.removeFirst();

        deque.addFirst(first + second);
        boolean correctFromSum = isCorrectEquation(deque, sum);

        deque.removeFirst();
        deque.addFirst(first * second);
        boolean correctFromProduct = isCorrectEquation(deque, sum);

        deque.removeFirst();
        deque.addFirst(Long.parseLong(Long.toString(first) + Long.toString(second)));
        boolean correctFromConcatenation = isCorrectEquation(deque, sum);

        deque.removeFirst();
        deque.addFirst(second);
        deque.addFirst(first);

        return  correctFromSum || correctFromProduct || correctFromConcatenation;
    }

    public long getSumOfCorrectEquations(){
        long result = 0;

        for (int i = 0; i < sums.size(); i++) {
            if (isCorrectEquation(equations.get(i), sums.get(i)))
                result += sums.get(i);
        }

        return result;
    }

}
