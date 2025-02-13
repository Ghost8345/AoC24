package Problem6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GuardGallivant {
    private final String path;
    private final List<List<Integer>> grid;
    private final Map<Character, Integer> characterMap;
    private final Map<Integer, int[]> stepsMap;
    private final GuardState guardInitialState;

    private static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
    private static final int FREE_SPACE = 4;
    private static final int OBSTACLE = 5;


    public GuardGallivant(String path){
        this.path = path;
        grid = new ArrayList<>();
        characterMap = Map.of(
                '^', UP,
                '>', RIGHT,
                'v', DOWN,
                '<', LEFT,
                '.', FREE_SPACE,
                '#', OBSTACLE
        );
        stepsMap = Map.of(
                UP, new int[] {-1, 0},
                RIGHT, new int[] {0, 1},
                DOWN, new int[] {1, 0},
                LEFT, new int[] {0, -1}
        );
        guardInitialState = new GuardState();
        getGrid();
    }

    private void getGrid(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            int i = 0;
            while( (line = bufferedReader.readLine()) != null) {
                List<Integer> row = new ArrayList<>();
                int j = 0;
                for (char c : line.toCharArray()){
                    int charType = characterMap.get(c);
                    if (charType != FREE_SPACE && charType != OBSTACLE){
                        guardInitialState.setLocationI(i);
                        guardInitialState.setLocationJ(j);
                        guardInitialState.setDirection(charType);
                    }
                    row.add(charType);
                    j++;
                }
                grid.add(row);
                i++;
            }
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public int getDistinctGuardPositions1(){

        boolean[][] visited = new boolean[grid.size()][grid.getFirst().size()];

        GuardState guardState = new GuardState(guardInitialState);

        while (guardState.getLocationI() < grid.size() && guardState.getLocationJ() < grid.getFirst().size()){
            visited[guardState.getLocationI()][guardState.getLocationJ()] = true;
            int [] step = stepsMap.get(guardState.getDirection());
            int [] positionToBe = new int[] {guardState.getLocationI() + step[0], guardState.getLocationJ() + step[1]};
            if (positionToBe[0] >= grid.size() || positionToBe[0] < 0 || positionToBe[1] >= grid.getFirst().size() || positionToBe[1] < 0)
                break;
            int typeInPositionToBe = grid.get(positionToBe[0]).get(positionToBe[1]);

            if (typeInPositionToBe == OBSTACLE){
                // Rotate Right
                int newGuardDirection = (guardState.getDirection() + 1) % 4;
                guardState.setDirection(newGuardDirection);
                continue;
            }

            guardState.setLocationI(positionToBe[0]);
            guardState.setLocationJ(positionToBe[1]);
        }

        int result = 0;

        for (boolean[] booleans : visited) {
            for (int j = 0; j < visited[0].length; j++) {
                if (booleans[j])
                    result++;
            }

        }
        return result;
    }

    private boolean checkObstructionCausesLoop(int i, int j){

        Set<GuardState> guardStateSet = new HashSet<>();
        GuardState guardState = new GuardState(guardInitialState);

        while (guardState.getLocationI() < grid.size() && guardState.getLocationJ() < grid.getFirst().size()){
            if (guardStateSet.contains(guardState))
                return true;
            guardStateSet.add(new GuardState(guardState));
            int [] step = stepsMap.get(guardState.getDirection());
            int [] positionToBe = new int[] {guardState.getLocationI() + step[0], guardState.getLocationJ() + step[1]};
            if (positionToBe[0] >= grid.size() || positionToBe[0] < 0 || positionToBe[1] >= grid.getFirst().size() || positionToBe[1] < 0)
                break;
            int typeInPositionToBe = grid.get(positionToBe[0]).get(positionToBe[1]);

            if (typeInPositionToBe == OBSTACLE || (positionToBe[0] == i && positionToBe[1] == j)){
                // Rotate Right
                int newGuardDirection = (guardState.getDirection() + 1) % 4;
                guardState.setDirection(newGuardDirection);
                continue;
            }

            guardState.setLocationI(positionToBe[0]);
            guardState.setLocationJ(positionToBe[1]);
        }

        return false;
    }

    public int getObstuctionPositionsForLoop2() {
        int res = 0;

        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.getFirst().size(); j++) {
                if (grid.get(i).get(j) != FREE_SPACE)
                    continue;

                if (checkObstructionCausesLoop(i, j))
                    res++;
            }
        }

        return res;
    }
}
