package Problem9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DiskFragmenter {
    private final String path;
    private final StringBuilder disk;
    private static final char FREESPACE = '.';

    public DiskFragmenter(String path) {
        this.path = path;
        this.disk = new StringBuilder();
        getInput();
    }

    private void getInput(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line = bufferedReader.readLine();
            int index = 0;
            for (int i = 0; i < line.length(); i+=2) {
                disk.append(String.valueOf(index).repeat(Character.getNumericValue(line.charAt(i))));
                if (i+1 < line.length())
                    disk.append(String.valueOf(FREESPACE).repeat(Character.getNumericValue(line.charAt(i+1))));
                index++;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public long getChecksum1(){
        long res = 0;
        int l = 0;
        int r = disk.length()-1;

        while (l < r) {
            if (disk.charAt(l) != FREESPACE) {
                l++;
                continue;
            }

            char toReplace = disk.charAt(r);
            while (toReplace == FREESPACE){
                r--;
                toReplace = disk.charAt(r);
            }
            disk.setCharAt(l, toReplace);
            disk.setCharAt(r, FREESPACE);

            l++;
            r--;
        }

        for (int i = 0; i < disk.length(); i++) {
            char c = disk.charAt(i);
            if (c == FREESPACE)
                break;

            res += (long) i * Character.getNumericValue(c);
        }

        return res;
    }
}
