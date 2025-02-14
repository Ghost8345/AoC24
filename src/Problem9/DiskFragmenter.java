package Problem9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
}
