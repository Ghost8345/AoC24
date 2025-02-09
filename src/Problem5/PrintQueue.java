package Problem5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PrintQueue {

    private final String path;
    private final Map<Integer, Set<Integer>> notAllowedBeforePages;

    public PrintQueue(String path){
        this.path = path;
        notAllowedBeforePages = new HashMap<>();
    }

    private RulesAndUpdates getRulesAndUpdates(){
        List<Rule> rules = new ArrayList<>();
        List<List<Integer>> updates = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ( (line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                String[] splitLine = line.split("\\|");
                rules.add(new Rule(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[1])));
            }

            while ( (line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(",");
                List<Integer> update = new ArrayList<>();
                for (String s : splitLine) {
                    update.add(Integer.parseInt(s));
                }
                updates.add(update);
            }
        } catch(IOException exception) {
            System.err.println(exception.getMessage());
        }

        return new RulesAndUpdates(rules, updates);
    }

    private int[] isValidUpdate(List<Integer> updateList){
        int index1 = -1;
        int index2 = -1;

        outerLoop:
        for (int i = 1; i < updateList.size(); i++) {
            int currentPage = updateList.get(i);
            Set<Integer> pagesToCheck = notAllowedBeforePages.get(currentPage);
            if (pagesToCheck == null)
                continue;
            for (int j = i-1; j >= 0 ; j--) {
                int toCheckPage = updateList.get(j);
                if (pagesToCheck.contains(toCheckPage)){
                    index1 = i;
                    index2 = j;
                    break outerLoop;
                }
            }
        }
        return new int[] {index1, index2};
    }

    private void swap(List<Integer> arr, int i, int j){
        int temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
    }

    public int getSumOfMiddleCorrectUpdates1(){
        RulesAndUpdates rulesAndUpdates = getRulesAndUpdates();
        for (Rule rule : rulesAndUpdates.rules()) {
            notAllowedBeforePages.computeIfAbsent(rule.page1(), _ -> new HashSet<>()).add(rule.page2());
        }

        int result = 0;

        for (List<Integer> updateList : rulesAndUpdates.updates()) {
            if (isValidUpdate(updateList)[0] == -1)
                result += updateList.get(updateList.size()/2);
        }

        return result;
    }

    public int getSumOfMiddleIncorrectUpdates2(){
        RulesAndUpdates rulesAndUpdates = getRulesAndUpdates();
        for (Rule rule : rulesAndUpdates.rules()) {
            notAllowedBeforePages.computeIfAbsent(rule.page1(), _ -> new HashSet<>()).add(rule.page2());
        }

        int result = 0;

        for (List<Integer> updateList : rulesAndUpdates.updates()) {
            boolean valid = true;
            int[] indices;
            while ((indices = isValidUpdate(updateList))[0] != -1){
                valid = false;
                swap(updateList, indices[0], indices[1]);
            }
            if (!valid)
                result += updateList.get(updateList.size()/2);
        }

        return result;
    }

}
