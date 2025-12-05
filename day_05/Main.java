package day_05;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

class Range {
    public long bottom, top;
    public Range(long bottom, long top) {
        this.bottom = bottom;
        this.top = top;
    }
    public boolean overlaps(Range other) {
        if (bottom <= other.top && bottom >= other.bottom) return true;
        if (top <= other.top && top >= other.bottom) return true;
        if (other.bottom <= top && other.bottom >= bottom) return true;
        if (other.top <= top && other.top >= bottom) return true;
        else return false;
    }
    public Range getunion(Range other) {
        return new Range(Math.min(bottom, other.bottom), Math.max(top, other.top));
    }
    public Range clone() {
        return new Range(bottom, top);
    }
}

class DayFour {
    private long [][] ranges;
    public long part_one_count = 0;

    public DayFour(String[] ranges_in) {
        ranges = new long[ranges_in.length][2];
        for (int i = 0; i < ranges.length; ++i) {
            String split[] = ranges_in[i].split("-");
            ranges[i][0] = Long.parseLong(split[0]);
            ranges[i][1] = Long.parseLong(split[1]);
        }
    }

    public void process_item(long item) {
        for (long range[]: ranges) {
            if (range[0] <= item && range[1] >= item) {
                ++part_one_count;
                return;
            }
        }
    }

    public long part_two() {
        Vector<Range> final_range = new Vector<Range>();

        for (long range[]: ranges) {
            final_range.add(new Range(range[0], range[1]));
        }

        int i = 0;
        int j = 0;
        while (i < final_range.size()) {
            while (j < final_range.size()) {
                if (i >= j) {++j; continue;}
                else {
                    Range ith = final_range.get(i).clone();
                    Range jth = final_range.get(j).clone();
                    if (ith.overlaps(jth)) {
                        final_range.remove(Math.max(i, j));
                        final_range.remove(Math.min(i, j));
                        final_range.add(ith.getunion(jth));
                        i = 0;
                        j = 0;
                    }
                }
                ++j;
            }
            ++i;
            j = 0;
        }

        long count = 0;
        for (Range range: final_range) {
            count += range.top - range.bottom + 1;
        }
        return count;
    }
}


public class Main {
    public static void main(String[] args) {
        
        try {
            // read input
            String parts[] = Files.readString(Paths.get("inputs/05.txt")).split("\n\n");
            String ranges[] = parts[0].split("\n");
            String items[] = parts[1].split("\n");

            DayFour day_four = new DayFour(ranges);

            for (String item: items) {
                day_four.process_item(Long.parseLong(item));
            }

            long part_two_count = day_four.part_two();

            System.out.printf("pt 1: %d\npt 2: %d\n", day_four.part_one_count, part_two_count);
            
        } catch (IOException e) {
            System.err.print("Failed to read input file: " + e.getMessage());
        }
    }
}
