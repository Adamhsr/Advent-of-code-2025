package day_07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


class DaySeven {
    int part_one;
    long part_two;

    public DaySeven(String lines[]) {
        part_one = 0;

        // start out with one time line
        part_two = 1;
        
        long positions[] = new long[lines[0].length()];

        // find first stream position
        positions[lines[0].indexOf('S', 0)] = 1;

        for (String line : lines) {
            
            // loop through each line
            long new_positions[] = new long[lines[0].length()];
            // loop through each character
            for (int i = 0; i < line.length(); ++i) {
                if (line.charAt(i) == '^' && positions[i] >= 1) {
                    // if a stream gets split:
                    new_positions[i - 1] += positions[i];
                    new_positions[i + 1] += positions[i];
                    
                    // each stream splitting adds one new stream, but as many new timelines as were split
                    ++part_one;
                    part_two += positions[i];

                    positions[i] = 0;
                }
            }

            // get positions ready for next row
            for (int j = 0; j < positions.length; ++j) {
                positions[j] = new_positions[j] + positions[j];
            }
        }
    }
}


public class Main {
    public static void main(String[] args) {
        
        try {
            // read input
            String lines[] = Files.readString(Paths.get("inputs/07.txt")).split("\n");
            


            DaySeven day_seven = new DaySeven(lines);
            
            System.out.printf("pt 1: %d\npt 2: %d\n", day_seven.part_one, day_seven.part_two);
            
        } catch (IOException e) {
            System.err.print("Failed to read input file: " + e.getMessage());
        }
    }
}
