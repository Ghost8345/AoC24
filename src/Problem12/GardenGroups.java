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

    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;

    private static final int[] DR = new int[] {-1, 1, 0, 0};
    private static final int[] DC = new int[] {0, 0, -1, 1};

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

    public void calculateRegionData(){
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
                List<Edge> edges = new ArrayList<>();

                while (!queue.isEmpty()) {
                    regionData.incrementArea();

                    int[] loc = queue.poll();
                    int x = loc[0];
                    int y = loc[1];

                    for (int d = 0; d < 4; d++) {
                        int newRow = x + DR[d];
                        int newCol = y + DC[d];

                        if (newRow < 0 || newRow >= n || newCol < 0 || newCol >= m || grid[newRow][newCol] != c){
                            regionData.incrementPerimeter();

                            int fixed, segStart, segEnd;

                            if (d == UP) {
                                fixed = x;
                                segStart = y;
                                segEnd = y + 1;
                            } else if (d == DOWN) {
                                fixed = x + 1;
                                segStart = y;
                                segEnd = y + 1;
                            } else if (d == LEFT) {
                                fixed = y;
                                segStart = x;
                                segEnd = x + 1;
                            } else {
                                fixed = y + 1;
                                segStart = x;
                                segEnd = x + 1;
                            }

                            boolean merged = false;
                            for (int k = 0; k < edges.size(); k++) {
                                Edge edge = edges.get(k);
                                if (edge.direction() == d) {
                                    int[] segData = edge.loc();
                                    if (segData[0] == fixed) {
                                        if (segStart <= segData[2] && segEnd >= segData[1]) {
                                            segData[1] = Math.min(segData[1], segStart);
                                            segData[2] = Math.max(segData[2], segEnd);
                                            merged = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!merged){
                                edges.add(new Edge(new int[] {fixed, segStart, segEnd}, d));
                                regionData.incrementSides();
                            }

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
    }

    public int getTotalPrice1() {
        int res = 0;

        for (List<RegionData> regionDataList : regionMap.values()) {
            for (RegionData regionData : regionDataList)
                res += regionData.getArea() * regionData.getPerimeter();
        }

        return res;
    }

    public int getTotalPrice2() {
        int res = 0;

        for (List<RegionData> regionDataList : regionMap.values()) {
            for (RegionData regionData : regionDataList)
                res += regionData.getArea() * regionData.getSides();
        }

        return res;
    }
}
