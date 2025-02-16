package Problem10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class HoofIt {
    private final String path;
    private int[][] grid;
    private static final int[][] DIRECTIONS = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public HoofIt(String path) {
        this.path = path;
        getInput();
    }

    private void getInput(){
        try(Stream<String> lines = Files.lines(Path.of(path))) {
            List<String> lineList = lines.toList();
            int numRows = lineList.size();
            int numCols = lineList.isEmpty() ? 0 : lineList.getFirst().length();

            grid = new int[numRows][numCols];
            for (int i = 0; i < numRows; i++) {
                String line = lineList.get(i);
                for (int j = 0; j < numCols; j++) {
                    grid[i][j] = Character.getNumericValue(line.charAt(j));
                }
            }
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private boolean isInbounds(int i, int j) {
        return !(i < 0 || i >= grid.length || j < 0 || j >= grid[0].length);
    }

    private void getScore(int i, int j, boolean[][] visited, Set<Location> endLocations) {
        if (grid[i][j] == 9){
            endLocations.add(new Location(i, j));
            return;
        }

        visited[i][j] = true;


        for (int[] directions : DIRECTIONS) {
            int nextI = i + directions[0];
            int nextJ = j + directions[1];

            if (!isInbounds(nextI, nextJ))
                continue;

            if (visited[nextI][nextJ])
                continue;

            if (grid[nextI][nextJ] - grid[i][j] != 1)
                continue;

            getScore(nextI, nextJ, visited, endLocations);
        }

        visited[i][j] = false;
    }

    public int getTotalScoreOfTrailheads1() {
        int res = 0;
        int numRows = grid.length;
        int numCols = grid[0].length;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (grid[i][j] == 0) {
                    boolean[][] visited = new boolean[numRows][numCols];
                    Set<Location> endLocations = new HashSet<>();
                    getScore(i, j, visited, endLocations);
                    res += endLocations.size();
                }
            }
        }
            return res;
    }

    private int getRating(int i, int j, boolean[][] visited) {
        int res = 0;

        if (grid[i][j] == 9){
            return 1;
        }

        visited[i][j] = true;


        for (int[] directions : DIRECTIONS) {
            int nextI = i + directions[0];
            int nextJ = j + directions[1];

            if (!isInbounds(nextI, nextJ))
                continue;

            if (visited[nextI][nextJ])
                continue;

            if (grid[nextI][nextJ] - grid[i][j] != 1)
                continue;

            res += getRating(nextI, nextJ, visited);
        }

        visited[i][j] = false;

        return res;
    }

    public int getTotalRatingOfTrailhead2(){
        int res = 0;
        int numRows = grid.length;
        int numCols = grid[0].length;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (grid[i][j] == 0) {
                    boolean[][] visited = new boolean[numRows][numCols];
                    res += getRating(i, j, visited);
                }
            }
        }
        return res;
    }


}
