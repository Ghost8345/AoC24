package Problem13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class ClawContraption {
    private final String path;
    private final List<Machine> machines;

    public ClawContraption(String path) {
        this.path = path;
        this.machines = new ArrayList<>();
        getInput();
    }

    private Location getCoordinates(String line) {
        String[] lineSplitted = line.split("\\s+");
        String xCoordinate = lineSplitted[2].split("\\+")[1];
        xCoordinate = xCoordinate.substring(0, xCoordinate.length()-1);
        String yCoordinate = lineSplitted[3].split("\\+")[1];
        return new Location(Integer.parseInt(xCoordinate), Integer.parseInt(yCoordinate));
    }

    private Location getPrizeCoordinates(String line) {
        String[] lineSplitted = line.split("\\s+");
        String xCoordinate = lineSplitted[1].split("=")[1];
        xCoordinate = xCoordinate.substring(0, xCoordinate.length()-1);
        String yCoordinate = lineSplitted[2].split("=")[1];
        return new Location(Integer.parseInt(xCoordinate), Integer.parseInt(yCoordinate));
    }

    private void getInput(){
        try (Stream<String> linesStream = Files.lines(Path.of(path))) {
            List<String> lines = linesStream.toList();
            for (int i = 0; i < lines.size(); i+=4) {
                machines.add(new Machine(new Button(getCoordinates(lines.get(i)), 3),
                        new Button(getCoordinates(lines.get(i+1)), 1),
                                getPrizeCoordinates(lines.get(i+2))
                ));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private int dfs(List<Button> buttons, Location currentLoc, Map<Location, Integer> memo) {
        if (currentLoc.x() == 0 && currentLoc.y() == 0)
            return 0;

        if (memo.containsKey(currentLoc))
            return memo.get(currentLoc);

        int res = Integer.MAX_VALUE;

        for (Button button : buttons){
            int newX = currentLoc.x() - button.location().x();
            int newY = currentLoc.y() - button.location().y();
            if (newX < 0 || currentLoc.y() - newY < 0)
                continue;

            int minTokens = dfs(buttons, new Location(newX, newY), memo);
            if (minTokens != Integer.MAX_VALUE)
                res = Math.min(res, minTokens + button.cost());
        }

        memo.put(currentLoc, res);
        return res;
    }

    private int getMinTokens(Machine machine) {
        Map<Location, Integer> memo = new HashMap<>();
        int minTokens = dfs(List.of(machine.a(), machine.b()), machine.prize(), memo);
        return minTokens == Integer.MAX_VALUE ? 0 : minTokens;
    }

    public int getMinTokensForPossiblePrizes1() {
        int res = 0;

        for (Machine machine : machines) {
            res += getMinTokens(machine);
        }

        return res;
    }


}
