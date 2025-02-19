package Problem12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Stream;

public class GardenGroups {
    private final String path;
    private final Map<Character, List<RegionData>> regionMap;
    private char[][] grid;

    private static final int[][] DIRECTIONS = new int[][] {{-1,0}, {1,0}, {0,-1}, {0,1}};

    public GardenGroups(String path) {
        this.path = path;
        this.regionMap = new HashMap<>();
        getInput();
    }

    private void getInput() {
        try(Stream<String> linesStream = Files.lines(Path.of(path))) {
            List<String> lines = linesStream.toList();
            int n = lines.size();
            int m = lines.getFirst().length();
            grid = new char[n][m];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    char c = lines.get(i).charAt(j);
                    grid[i][j] = c;
                    regionMap.putIfAbsent(c, new ArrayList<>());
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public int getTotalPrice1() {
        int res = 0;
        int n = grid.length;
        int m = grid[0].length;
        boolean[][] visited = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (visited[i][j])
                    continue;

                visited[i][j] = true;
                char c = grid[i][j];
                List<RegionData> regionDataList = regionMap.get(c);
                RegionData regionData = new RegionData();
                Queue<int[]> queue = new LinkedList<>();
                queue.offer(new int[] {i, j});

                while (!queue.isEmpty()) {
                    regionData.incrementArea();

                    int[] loc = queue.poll();

                    for (int[] direction : DIRECTIONS) {
                        int newRow = loc[0] + direction[0];
                        int newCol = loc[1] + direction[1];

                        if (newRow < 0 || newRow >= n || newCol < 0 || newCol >= m){
                            regionData.incrementPerimeter();
                            continue;
                        }

                        if (grid[newRow][newCol] != c){
                            regionData.incrementPerimeter();
                        }
                        else {
                            if (visited[newRow][newCol])
                                continue;

                            queue.offer(new int[] {newRow, newCol});
                            visited[newRow][newCol] = true;
                        }
                    }
                }
                regionDataList.add(regionData);
            }
        }

        for (List<RegionData> regionDataList : regionMap.values()) {
            for (RegionData regionData : regionDataList)
                res += regionData.getArea() * regionData.getPerimeter();
        }

        return res;
    }
}
