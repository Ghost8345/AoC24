package Problem4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CeresSearch {

    private final String path;
    private final Set<AbstractMap.SimpleEntry<Integer, Integer>> indexSet;

    public CeresSearch(String path) {
        this.path = path;
        this.indexSet = new HashSet<>();
    }

    private List<List<Character>> getArray(){

        List<List<Character>> array = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            String line;
            while( (line = bufferedReader.readLine()) != null){
                List<Character> charList = line.chars()
                        .mapToObj(c -> (char) c)
                        .collect(Collectors.toList());
                array.add(charList);
            }
        } catch (IOException e) {
            System.err.println("File is not found.");
        }

        return array;
    }

    private boolean existsInPath(List<List<Character>> array, String word, int wordIndex, int i, int j, int k, int l) {

        if (wordIndex == word.length() - 1)
            return true;

        for (int m = wordIndex+1; m < word.length(); m++) {
            i += k;
            j += l;
            if (i < 0 || i >= array.size() || j < 0 || j >= array.getFirst().size())
                return false;
            if (word.charAt(m) != array.get(i).get(j))
                return false;
        }

        return true;
    }

    public int countOccurrences1(String word){
        int res = 0;
        List<List<Character>> array = getArray();

        for (int i = 0; i < array.size(); i++) {
            for (int j = 0; j < array.getFirst().size(); j++) {
                if (array.get(i).get(j) != word.charAt(0))
                    continue;

                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if (k == 0 && l == 0)
                            continue;
                        if (existsInPath(array, word, 0, i, j, k, l))
                            res++;
                    }
                }
            }
        }

        return res;
    }

    public int countOccurences2(String word){
        int res = 0;
        List<List<Character>> array = getArray();

        for (int i = 0; i < array.size(); i++) {
            for (int j = 0; j < array.getFirst().size(); j++) {
                if (array.get(i).get(j) != word.charAt(0))
                    continue;

                for (int k = -1; k <= 1; k+=2) {
                    for (int l = -1; l <= 1; l+=2) {
                        if (!existsInPath(array, word, 0, i, j, k, l))
                            continue;
                        int midCharLength = word.length()/2;
                        int midCharI = i + midCharLength * k;
                        int midCharJ = j + midCharLength * l;
                        AbstractMap.SimpleEntry<Integer, Integer> midCharIndex = new AbstractMap.SimpleEntry<>(midCharI, midCharJ);
                        if (indexSet.contains(midCharIndex))
                            res++;
                        else {
                            indexSet.add(midCharIndex);
                        }
                    }
                }
            }
        }

        return res;
    }
}
