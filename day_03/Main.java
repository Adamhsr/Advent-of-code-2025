package day_03;

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;



class DayThree {
    public static int get_joltage(int[] batteries) {
        int max_first = 0;
        int max_second = 0;

        for (int i = 0; i < batteries.length - 1; ++i) {
            if (batteries[i] > max_first) {
                max_first = batteries[i];
                max_second = 0;
            } else if (batteries[i] > max_second) {
                max_second = batteries[i];
            }
        }
        if (batteries[batteries.length - 1] > max_second) {
            max_second = batteries[batteries.length - 1];
        }

        return 10 * max_first + max_second;
    }

    public static int get_max_index(int[] batteries) {
        int max_index = -1;
        for (int i = 0; i < batteries.length; ++i) {
            if (max_index == -1 || batteries[i] > batteries[max_index]) {
                max_index = i;
            }
        }
        return max_index;
    }

    // [987654321111111] i = 1
    // [000000000000] indicies
    // [8765] sub

    public static long get_second_joltage(int[] batteries) {
        int indicies[] = new int[12];

        for (int i = 0; i < 12; ++i) {
            if (i == 0) {
                int subarray[] = new int[batteries.length - 11];
                for (int j = 0; j < batteries.length - 11; ++j) {
                    subarray[j] = batteries[j];
                }
                indicies[0] = get_max_index(subarray);
            } else {

                int subarray[] = new int[batteries.length - (12 - (i + 1)) - (1 + indicies[i - 1])];

                for (int j = 0; j < subarray.length; ++j) {
                    subarray[j] = batteries[j + (1 + indicies[i - 1])];
                }

                indicies[i] = get_max_index(subarray) + (1 + indicies[i - 1]);
            }
        }

        long result = 0;

        for (int i : indicies) {
            result *= 10;
            result += batteries[i];
        }

        return result;
    }
}


public class Main {
    public static void main(String[] args) {
        
        try {
            // read lines from inputs/01.txt
            List<String> lines = Files.readAllLines(Paths.get("inputs/03.txt"));
            
            long pt_1_count = 0;
            long pt_2_count = 0;

            // step through all the ranges
            for (String line : lines) {
                System.out.println(line);
                int batteries[] = new int[line.length()];
                for (int i = 0; i < line.length(); ++i) {
                    batteries[i] = Integer.parseInt(line.substring(i, i + 1));
                }
                
                pt_1_count += DayThree.get_joltage(batteries);
                System.out.printf("%d\n\n", DayThree.get_second_joltage(batteries));
                pt_2_count += DayThree.get_second_joltage(batteries);
            }

            // print result
            System.out.printf("Part 1: %d\nPart 2: %d\n", pt_1_count, pt_2_count);
        } catch (IOException e) {
            System.err.print("Failed to read input file: " + e.getMessage());
        }
    }
}
