import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class Ticker {
    private static final int MOD = 100;
    private static final int START_POS = 50;
    private int position, pt_1_count, pt_2_count;

    public Ticker() {
        position = START_POS;
        pt_1_count = 0;
        pt_2_count = 0;
    }

    // one move of the safe
    public void step(String move) {
        // get the new number, without modulus
        int new_position = position;
        if (move.charAt(0) == 'L') {
            new_position -= Integer.parseInt(move.substring(1));
        } else {
            new_position += Integer.parseInt(move.substring(1));
        }

        // pt 1, just check if we land on 0
        if (Math.floorMod(new_position, MOD) == 0) {
            ++pt_1_count;
        }

        // part 2: calculate rotations
        int rotations = Math.abs(new_position / MOD);

        pt_2_count += rotations;

        // if we moved left, this might be off by one if we started above zero
        if (new_position <= 0 && position != 0) ++pt_2_count;


        // update position
        position = Math.floorMod(new_position, MOD);
    }

    public int get_pt_1_count() {
        return pt_1_count;
    }

    public int get_pt_2_count() {
        return pt_2_count;
    }
}

public class Main {
    public static void main(String[] args) {
        
        try {
            // read lines from inputs/01.txt
            List<String> lines = Files.readAllLines(Paths.get("inputs/01.txt"));

            // make a new ticker
            Ticker ticker = new Ticker();

            // step through all the lines and feed to ticker
            for (String line : lines) {
                ticker.step(line);
            }

            // print result
            System.out.printf("Part 1: %d\nPart 2: %d\n", ticker.get_pt_1_count(), ticker.get_pt_2_count());
        } catch (IOException e) {
            System.err.println("Failed to read input file: " + e.getMessage());
        }
    }
}
