package Problem14;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class RestroomRedoubt {
    private final String path;
    private int columnNumber;
    private int rowNumber;
    private final List<Robot> robots;
    private int iterNum = 0;

    public RestroomRedoubt(String path) {
        this.path = path;
        this.robots = new ArrayList<>();
        getInput();
    }

    private void parseGridSize(String line) {
        String[] gridSize = line.split(",");
        columnNumber = Integer.parseInt(gridSize[0]);
        rowNumber = Integer.parseInt(gridSize[1]);
    }

    private void parseRobot(String line) {
        String[] lineSplitted = line.split("\\s+");
        int[] position = Arrays.stream(lineSplitted[0].substring(2).split(","))
                .mapToInt(Integer::parseInt).toArray();
        int[] velocity = Arrays.stream(lineSplitted[1].substring(2).split(","))
                .mapToInt(Integer::parseInt).toArray();

        Robot robot = new Robot(position, velocity);
        robots.add(robot);
    }

    private void getInput() {
        try(Stream<String> linesStream = Files.lines(Path.of(path))) {
            List<String> lines = linesStream.toList();
            parseGridSize(lines.getFirst());

            for (String line : lines.subList(1, lines.size())) {
                parseRobot(line);
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private int getNewPosition(int pos, int v, int iters, int mod) {
        int newPos = (pos + v * iters) % mod;
        if (newPos < 0)
            newPos += mod;
        return newPos;
    }

    private int getQuadrant(int col, int row, int midCol, int midRow) {
        Quadrant quadrant;
        if (col < midCol && row < midRow)
            quadrant = Quadrant.TOP_LEFT;
        else if (col > midCol && row < midRow)
            quadrant = Quadrant.TOP_RIGHT;
        else if (col > midCol && row > midRow)
            quadrant = Quadrant.BOTTOM_LEFT;
        else
            quadrant = Quadrant.BOTTOM_RIGHT;

        return quadrant.ordinal();
    }

    public int getSafetyFactor1(int iterations) {

        for (Robot robot : robots) {
            int newX = getNewPosition(robot.position()[0], robot.velocity()[0], iterations, columnNumber);
            int newY = getNewPosition(robot.position()[1], robot.velocity()[1], iterations, rowNumber);
            robot.position()[0] = newX;
            robot.position()[1] = newY;
        }

        int[] quadrantCount = new int[Quadrant.values().length];
        int midCol = columnNumber / 2;
        int midRow = rowNumber / 2;

        for (Robot robot : robots) {
            int[] pos = robot.position();
            int col = pos[0];
            int row = pos[1];

            if (col == midCol || row == midRow)
                continue;

            int quadrant = getQuadrant(col, row, midCol, midRow);
            quadrantCount[quadrant]++;
        }

        return Arrays.stream(quadrantCount).reduce(1, (a, b) -> a*b);
    }

    private int getNewPos(int pos, int v, int mod) {
        int newPos = (pos + v) % mod;
        if (newPos < 0)
            newPos += mod;
        return newPos;
    }

    public void writeGridsToFile(int startingIterations, int maxIterations) {

        for (Robot robot : robots) {
            int newX = getNewPosition(robot.position()[0], robot.velocity()[0], startingIterations, columnNumber);
            int newY = getNewPosition(robot.position()[1], robot.velocity()[1], startingIterations, rowNumber);
            robot.position()[0] = newX;
            robot.position()[1] = newY;
        }
        iterNum += startingIterations;

        for (int i = 0; i < maxIterations; i++) {
            for (Robot robot : robots) {
                int newX = getNewPos(robot.position()[0], robot.velocity()[0], columnNumber);
                int newY = getNewPos(robot.position()[1], robot.velocity()[1], rowNumber);
                robot.position()[0] = newX;
                robot.position()[1] = newY;
            }
            iterNum++;
            writeGridToFile("src/Problem14/grids.txt");
        }
    }

    private void writeGridToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            char[][] grid = new char[rowNumber][columnNumber];

            for (char[] row : grid) {
                Arrays.fill(row, '.');
            }

            for (Robot robot : robots) {
                int[] pos = robot.position();
                grid[pos[1]][pos[0]] = '#';
            }

            for (char[] row : grid) {
                writer.write(row);
                writer.newLine();
            }
            writer.newLine();
            writer.write(String.valueOf(iterNum));
            writer.newLine();

        } catch (IOException e) {
            System.err.println("Error writing grid to file: " + e.getMessage());
        }
    }


}
