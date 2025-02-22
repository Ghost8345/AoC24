package Problem13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class ClawContraption {
    private final String path;
    private final List<Machine> machines;

    private static final long OFFSET = 10_000_000_000_000L;

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
        return new Location(Long.parseLong(xCoordinate), Long.parseLong(yCoordinate));
    }

    private Location getPrizeCoordinates(String line) {
        String[] lineSplitted = line.split("\\s+");
        String xCoordinate = lineSplitted[1].split("=")[1];
        xCoordinate = xCoordinate.substring(0, xCoordinate.length()-1);
        String yCoordinate = lineSplitted[2].split("=")[1];
        return new Location(Long.parseLong(xCoordinate), Long.parseLong(yCoordinate));
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
        if (currentLoc.getX() == 0 && currentLoc.getY() == 0)
            return 0;

        if (memo.containsKey(currentLoc))
            return memo.get(currentLoc);

        int res = Integer.MAX_VALUE;

        for (Button button : buttons){
            long newX = currentLoc.getX() - button.location().getX();
            long newY = currentLoc.getY() - button.location().getY();
            if (newX < 0 || newY < 0)
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

    private long solveLinearly(Machine machine) {
        long Ax = machine.a().location().getX(), Ay = machine.a().location().getY();
        long Bx = machine.b().location().getX(), By = machine.b().location().getY();
        long Px = machine.prize().getX(), Py = machine.prize().getY();
        long costA = machine.a().cost(), costB = machine.b().cost();

        long D = Ax * By - Ay * Bx;
        long D_X = Px * By - Py * Bx;
        long D_Y = Ax * Py - Px * Ay;

        if (D_X % D != 0 || D_Y % D != 0)
            return 0;

        long nA = D_X / D;
        long nB = D_Y / D;

        if (nA < 0 || nB < 0)
            return 0;

        return (nA * costA + nB * costB);
    }

    public long getMinTokensForPossiblePrizes2() {
        long res = 0;

        for (Machine machine : machines) {
            Location prizeLocation = machine.prize();
            prizeLocation.setX(prizeLocation.getX() + OFFSET);
            prizeLocation.setY(prizeLocation.getY() + OFFSET);
        }

        for (Machine machine : machines) {
            res += solveLinearly(machine);
        }

        return res;
    }


}
