package Problem8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ResonantCollinearity {
    private final String path;
    private final Map<Character, List<Location>> characterLocationsMap;
    private final Set<Location> antinodesLocations;
    private int rowSize;
    private int columnSize;

    public ResonantCollinearity(String path){
        this.path = path;
        this.characterLocationsMap = new HashMap<>();
        this.antinodesLocations = new HashSet<>();
        this.rowSize = 0;
        this.columnSize = 0;
        getMap();
    }

    private void getMap() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            int i = 0;
            while ( (line = bufferedReader.readLine()) != null) {
                int j = 0;
                for (char c : line.toCharArray()) {
                    if (c != '.'){
                        characterLocationsMap.computeIfAbsent(c, (_) -> new ArrayList<>()).add(new Location(i, j));
                    }
                    j++;
                }
                columnSize = j;
                i++;
            }
            rowSize = i;
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }

    }

    private boolean isInbound(Location location) {
        return !(location.i() < 0 || location.i() >= rowSize || location.j() < 0 || location.j() >= columnSize);
    }

    private List<Location> getInboundAntinodes(Location location1, Location location2) {
        List<Location> res = new ArrayList<>();

        int diffI = Math.abs(location1.i() - location2.i());
        int diffJ = Math.abs(location1.j() - location2.j());

        Location antinode1 = new Location(
                location1.i() == Math.min(location1.i(), location2.i()) ? location1.i() - diffI : location1.i() + diffI,
                location1.j() == Math.min(location1.j(), location2.j()) ? location1.j() - diffJ : location1.j() + diffJ);
        Location antinode2 = new Location(
                location2.i() == Math.min(location1.i(), location2.i()) ? location2.i() - diffI : location2.i() + diffI,
                location2.j() == Math.min(location1.j(), location2.j()) ? location2.j() - diffJ : location2.j() + diffJ);

        if (isInbound(antinode1))
            res.add(antinode1);
        if (isInbound(antinode2))
            res.add(antinode2);

        return res;
    }

    private List<Location> getInboundAntinodesExtended(Location location1, Location location2) {
        List<Location> res = new ArrayList<>();

        int diffI = Math.abs(location1.i() - location2.i());
        int diffJ = Math.abs(location1.j() - location2.j());

        Location antinode1Temp = location1;
        while (isInbound(antinode1Temp)) {
            res.add(antinode1Temp);
            antinode1Temp = new Location(
                    location1.i() == Math.min(location1.i(), location2.i()) ? antinode1Temp.i() - diffI : antinode1Temp.i() + diffI,
                    location1.j() == Math.min(location1.j(), location2.j()) ? antinode1Temp.j() - diffJ : antinode1Temp.j() + diffJ);
        }
        Location antinode2Temp = location2;
        while (isInbound(antinode2Temp)) {
            res.add(antinode2Temp);
            antinode2Temp = new Location(
                    location2.i() == Math.min(location1.i(), location2.i()) ? antinode2Temp.i() - diffI : antinode2Temp.i() + diffI,
                    location2.j() == Math.min(location1.j(), location2.j()) ? antinode2Temp.j() - diffJ : antinode2Temp.j() + diffJ);
        }

        return res;
    }

    public int getUniqueAntinodes1() {
        for (List<Location> locations : characterLocationsMap.values()) {
            for (int i = 0; i < locations.size()-1; i++) {
                for (int j = i+1; j < locations.size(); j++) {
                    List<Location> inboundAntinodes = getInboundAntinodes(locations.get(i), locations.get(j));
                    antinodesLocations.addAll(inboundAntinodes);
                }
            }
        }

        return antinodesLocations.size();
    }

    public int getUniqueAntinodes2() {
        for (List<Location> locations : characterLocationsMap.values()) {
            for (int i = 0; i < locations.size()-1; i++) {
                for (int j = i+1; j < locations.size(); j++) {
                    List<Location> inboundAntinodes = getInboundAntinodesExtended(locations.get(i), locations.get(j));
                    antinodesLocations.addAll(inboundAntinodes);
                }
            }
        }

        return antinodesLocations.size();
    }

}
