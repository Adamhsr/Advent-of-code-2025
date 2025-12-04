package day_04;

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;



class DayFour {
    private boolean[][] grid;
    private int part_1_result = -1;

    public DayFour(String[] lines) {
        grid = new boolean[lines.length][lines[0].length()];

        for (int i = 0; i < lines.length; ++i) {
            char current_line[] = lines[i].toCharArray();
            for (int j = 0; j < current_line.length; ++j) {
                if (current_line[j] == '@') grid[i][j] = true;
                else grid[i][j] = false;
            }
        }
    }
    
    private boolean try_access(int i, int j) {
        if (0 <= i && i < grid.length && 0 <= j && j < grid[0].length) {
            return grid[i][j];
        }
        else return false;
    }

    private int get_neighbours(int i, int j) {
        int sum = 0;
        for (int di = -1; di <= 1; ++di) {
            for (int dj = -1; dj <= 1; ++dj) {
                if (!(di == 0 && dj == 0) && try_access(i + di, j + dj)) {
                    ++sum;
                }
            }
        }
        return sum;
    }

    public int pt_1() {
        if (part_1_result != -1) return part_1_result;

        int sum = 0;
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[0].length; ++j) {
                if (try_access(i, j) && get_neighbours(i, j) < 4) {
                    // System.out.printf("(%d, %d)\n", i, j);
                    ++sum;
                }
            }
        }
        return sum;
    }

    public int pt_2() {
        // we will mess up the board, so if pt_1 hasn't been run, we should run it and save result
        if (part_1_result == -1) pt_1();

        int sum = 0;
        int to_add = 0;

        // this is my first time actually needing to use a do while loop!
        do {
            to_add = 0;
            boolean grid_copy[][] = grid.clone();

            for (int i = 0; i < grid.length; ++i) {
                for (int j = 0; j < grid[0].length; ++j) {
                    if (try_access(i, j) && get_neighbours(i, j) < 4) {
                        // System.out.printf("(%d, %d)\n", i, j);
                        grid_copy[i][j] = false;
                        ++to_add;
                    }
                }
            }

            grid = grid_copy.clone();

            sum += to_add;
        } while (to_add > 0);

        return sum;
    }
}


public class Main {
    public static void main(String[] args) {
        
        try {
            // read input
            String lines[] = Files.readAllLines(Paths.get("inputs/04.txt")).toArray(new String[0]);
            DayFour d4 = new DayFour(lines);

            // print result
            System.out.printf("Part 1: %d\nPart 2: %d\n", d4.pt_1(), d4.pt_2());
        } catch (IOException e) {
            System.err.print("Failed to read input file: " + e.getMessage());
        }
    }
}
