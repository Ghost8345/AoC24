package Problem9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiskFragmenter {
    private final String path;
    private final ArrayList<String> disk;
    private static final String FREESPACE = ".";

    public DiskFragmenter(String path) {
        this.path = path;
        this.disk = new ArrayList<>();
        getInput();
    }

    private void getInput(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line = bufferedReader.readLine();
            int index = 0;
            for (int i = 0; i < line.length(); i+=2) {
                for (int k = 0; k < Character.getNumericValue(line.charAt(i)); k++)
                    disk.add(String.valueOf(index));

                if (i+1 < line.length()) {
                    for (int k = 0; k < Character.getNumericValue(line.charAt(i+1)); k++) {
                        disk.add(FREESPACE);
                    }
                }
                index++;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public long getChecksum1(){
        List<String> disk = new ArrayList<>(this.disk);
        long res = 0;
        int l = 0;
        int r = disk.size()-1;

        while (l < r) {
            if (!disk.get(l).equals(FREESPACE)) {
                l++;
                continue;
            }

            String toReplace = disk.get(r);
            while (toReplace.equals(FREESPACE)){
                r--;
                toReplace = disk.get(r);
            }
            disk.set(l, toReplace);
            disk.set(r, FREESPACE);

            l++;
            r--;
        }

        for (int i = 0; i < disk.size(); i++) {
            String s = disk.get(i);
            if (s.equals(FREESPACE))
                break;

            res += (long) i * Integer.parseInt(s);
        }

        return res;
    }

    public long getChecksum2(){
        List<String> disk = new ArrayList<>(this.disk);
        int maxFileId = -1;
        for (String block : disk) {
            if (!FREESPACE.equals(block)) {
                maxFileId = Math.max(maxFileId, Integer.parseInt(block));
            }
        }

        for (int fileId = maxFileId; fileId >= 0; fileId--) {
            String targetFileId = String.valueOf(fileId);
            List<Integer> fileIndices = new ArrayList<>();
            for (int i = 0; i < disk.size(); i++) {
                if (disk.get(i).equals(targetFileId)) {
                    fileIndices.add(i);
                }
            }

            int fileSize = fileIndices.size();
            int bestFreeSpaceIndex = -1;

            for (int i = 0; i < fileIndices.getFirst(); i++) {
                if (FREESPACE.equals(disk.get(i))) {
                    int currentFreeSpaceSize = 0;
                    int currentFreeSpaceIndex = i;
                    while (i < disk.size() && FREESPACE.equals(disk.get(i))) {
                        currentFreeSpaceSize++;
                        i++;
                    }
                    if (currentFreeSpaceSize >= fileSize) {
                        bestFreeSpaceIndex = currentFreeSpaceIndex;
                        break;
                    }
                }
            }

            if (bestFreeSpaceIndex != -1) {
                String id = disk.get(fileIndices.getFirst());

                for (int i = fileIndices.size() - 1; i >= 0; i--) {
                    disk.set(fileIndices.get(i), FREESPACE);
                }

                for (int i = 0; i < fileSize; i++) {
                    disk.set(bestFreeSpaceIndex + i, id);
                }
            }
        }

        long res = 0;
        for (int i = 0; i < disk.size(); i++) {
            String s = disk.get(i);
            if (s.equals(FREESPACE))
                continue;

            res += (long) i * Integer.parseInt(s);
        }
        return res;
    }
}
