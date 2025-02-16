package Problem11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlutonianPebbles {
    private final String path;
    private final Map<Long, Long> initialStonesCounter;

    public PlutonianPebbles(String path) {
        this.path = path;
        this.initialStonesCounter = new HashMap<>();
        getInput();
    }

    private void getInput() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line = bufferedReader.readLine();
            String[] stones = line.split("\\s+");
            for (String stone : stones) {
                long stoneVal = Long.parseLong(stone);
                initialStonesCounter.put(stoneVal, initialStonesCounter.getOrDefault(stoneVal, 0L) + 1);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private List<Long> getResultingStone(Long stone) {
        if (stone == 0L) {
            return List.of(1L);
        }

        String s = String.valueOf(stone);
        int n = s.length();
        if (n % 2 == 0) {
            int midIndex = n/2;
            String first = s.substring(0, midIndex);
            String second = s.substring(midIndex, n);
            return List.of(Long.parseLong(first), Long.parseLong(second));
        }
        else {
            return List.of(stone * 2024);
        }
    }

    private Map<Long, Long> getStonesPerTurn(Map<Long, Long> initialStonesCounter) {
        Map<Long, Long> resultingStonesCounter = new HashMap<>();
        for (Map.Entry<Long, Long> entry : initialStonesCounter.entrySet()) {
            long stone = entry.getKey();
            long count = entry.getValue();
            for (long resultingStone : getResultingStone(stone)) {
                resultingStonesCounter.put(resultingStone, resultingStonesCounter.getOrDefault(resultingStone, 0L) + count);
            }
        }
        return resultingStonesCounter;
    }

    public long getTotalStonesNum(int blinkNum) {
        Map<Long, Long> currCounter = new HashMap<>(initialStonesCounter);
        for (int i = 0; i < blinkNum; i++) {
            currCounter = getStonesPerTurn(currCounter);
        }
        return currCounter.values().stream().mapToLong(Long::longValue).sum();
    }


}
