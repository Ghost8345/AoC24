package Problem5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PrintQueue {

    private final String path;
    private final Map<Integer, List<Integer>> ruleMap;
    private final List<List<Integer>> updates;

    public PrintQueue(String path){
        this.path = path;
        ruleMap = new HashMap<>();
        updates = new ArrayList<>();
        getRulesAndUpdates();
    }

    private void getRulesAndUpdates(){

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ( (line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                String[] splitLine = line.split("\\|");
                int page = Integer.parseInt(splitLine[0].trim());
                int shouldBeAfterPage = Integer.parseInt(splitLine[1].trim());
                ruleMap.computeIfAbsent(page, _ -> new ArrayList<>()).add(shouldBeAfterPage);
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
    }

    private boolean isValidUpdate(List<Integer> updateList){

        Map<Integer, Integer> positionMap = new HashMap<>();

        for (int i = 0; i < updateList.size(); i++) {
            positionMap.put(updateList.get(i), i);
        }

        for (Integer currentPage : positionMap.keySet()){
            if (ruleMap.containsKey(currentPage)){
                for (Integer shouldBeAfterPage : ruleMap.get(currentPage)){
                    if (positionMap.containsKey(shouldBeAfterPage) && positionMap.get(currentPage) > positionMap.get(shouldBeAfterPage))
                        return false;
                }
            }
        }
        return true;
    }

    private List<Integer> topologicalSort(List<Integer> updateList) {
        Set<Integer> pages = new HashSet<>(updateList);

        Map<Integer, List<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        for (Integer page : pages) {
            graph.put(page, new ArrayList<>());
            inDegree.put(page, 0);
        }

        for (Integer currentPage : pages) {
            if (ruleMap.containsKey(currentPage)) {
                for (Integer shouldBeAfterPage : ruleMap.get(currentPage)) {
                    if (pages.contains(shouldBeAfterPage)) {
                        graph.get(currentPage).add(shouldBeAfterPage);
                        inDegree.put(shouldBeAfterPage, inDegree.get(shouldBeAfterPage) + 1);
                    }
                }
            }
        }
        // Now do Kahn's algorithm for topological sorting.
        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }
        List<Integer> sortedList = new ArrayList<>();
        while (!queue.isEmpty()) {
            Integer currentPage = queue.poll();
            sortedList.add(currentPage);
            for (Integer neighbor : graph.get(currentPage)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (sortedList.size() != pages.size()) {
            throw new IllegalStateException("Cycle detected in update List.");
        }
        return sortedList;
    }

    private int getMiddlePage(List<Integer> updateList) {
        return updateList.get(updateList.size() / 2);
    }

    public int getSumOfMiddleCorrectUpdates1(){
        int result = 0;

        for (List<Integer> updateList : updates) {
            if (isValidUpdate(updateList))
                result += getMiddlePage(updateList);
        }

        return result;
    }

    public int getSumOfMiddleIncorrectUpdates2(){
        int result = 0;

        for (List<Integer> updateList : updates) {
            if (!isValidUpdate(updateList)){
                List<Integer> validUpdateList = topologicalSort(updateList);
                result += getMiddlePage(validUpdateList);
            }
        }

        return result;
    }

}
