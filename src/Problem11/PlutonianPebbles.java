package Problem11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlutonianPebbles {
    private final String path;
    private final List<Long> initialStones;

    public PlutonianPebbles(String path) {
        this.path = path;
        this.initialStones = new ArrayList<>();
        getInput();
    }

    private void getInput() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line = bufferedReader.readLine();
            String[] stones = line.split("\\s+");
            for (String stone : stones)
                initialStones.add(Long.parseLong(stone));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private List<Long> getStonesPerTurn(List<Long> initialStones) {
        List<Long> resultingStones = new ArrayList<>();
        for (long stone : initialStones) {
            if (stone == 0L) {
                resultingStones.add(1L);
                continue;
            }

            String s = String.valueOf(stone);
            int n = s.length();
            if (n % 2 == 0) {
                int midIndex = n/2;
                String first = s.substring(0, midIndex);
                String second = s.substring(midIndex, n);
                resultingStones.add(Long.parseLong(first));
                resultingStones.add(Long.parseLong(second));
            }
            else {
                resultingStones.add(stone * 2024);
            }
        }

        return resultingStones;
    }

    public int getTotalStonesNum1(int blinkNum) {
        List<Long> curr = initialStones;
        for (int i = 0; i < blinkNum; i++) {
            curr = getStonesPerTurn(curr);
        }
        return curr.size();
    }


}
