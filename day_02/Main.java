import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.HashSet;



class DayTwo {
    static public long double_number(long in) {
        return (long) Math.floor(in + in * Math.pow(10, Math.floor(Math.log10(in) + 1)));
    }

    static public long get_min_doubler(long min_normal) {
        // use a binary search method
        long bottom = (long) Math.floor(min_normal / Math.pow(10, Math.floor(Math.log10(min_normal)) / 2 + 1));
        long top =    (long) Math.floor(min_normal / Math.pow(10, Math.floor(Math.log10(min_normal)) / 2));
        if (double_number(bottom) > min_normal) throw new IllegalArgumentException("Invalid age: " + min_normal + " " + bottom + " " + top);
        if (double_number(top) < min_normal) throw new IllegalArgumentException("Invalid age: " + min_normal + " " + bottom + " " + top);
        
        // System.out.printf("bottom: %d, guess: %d, top: %d, normal: %d", bottom, -1, top, min_normal);

        while (true) {
            long guess = (bottom + top + 1) / 2;
            // System.out.printf("bottom: %d, guess: %d, top: %d, normal: %d\n", bottom, guess, top, min_normal);
            long doubled = double_number(guess);
            if (doubled < min_normal) {
                bottom = guess;
            } else if (doubled == min_normal || guess == top) {
                return guess;
            } else if (doubled > min_normal) {
                top = guess;
            }
        }
    }
    
    static public long get_max_doubler(long max_normal) {
        // use a binary search method
        long bottom = (long) Math.floor(max_normal / Math.pow(10, Math.floor(Math.log10(max_normal)) / 2 + 1));
        long top =    (long) Math.floor(max_normal / Math.pow(10, Math.floor(Math.log10(max_normal)) / 2));
        if (double_number(bottom) > max_normal) throw new IllegalArgumentException("Invalid age: " + max_normal + " " + bottom + " " + top);
        if (double_number(top) < max_normal) throw new IllegalArgumentException("Invalid age: " + max_normal + " " + bottom + " " + top);
        
        // System.out.printf("bottom: %d, guess: %d, top: %d, normal: %d", bottom, -1, top, min_normal);

        while (true) {
            long guess = (bottom + top) / 2;
            // System.out.printf("bottom: %d, guess: %d, top: %d, normal: %d\n", bottom, guess, top, max_normal);
            long doubled = double_number(guess);
            if (doubled > max_normal) {
                top = guess;
            } else if (doubled == max_normal || guess == bottom) {
                return guess;
            } else if (doubled < max_normal) {
                bottom = guess;
            }
        }
    }

    static long pr_2_range(long bottom, long top) {
        Set<Long> collected_numbers = new HashSet<>();
        // System.out.printf("\n%d-%d\n", bottom, top);

        long base = 1;
        while (base <= bottom) {
            for (long duplicate_count = 2;;++duplicate_count) {
                // build duplicated base
                long duplicated_base = 1;
                for (long i = 1; i < duplicate_count; ++i) {
                    // shift top
                    duplicated_base *= 10 * base;
                    // System.out.println(base + " " +duplicate_count + " " + Math.pow(10, Math.floor(Math.log10(base))) + 1);
                    // add bottom
                    duplicated_base += 1;
                }

                if (duplicated_base > top) {
                    // System.out.printf("reached: %d\n", duplicated_base);
                    break;
                }

                long factored_bottom = bottom / duplicated_base;
                long factored_top = top / duplicated_base + 1;

                // System.out.printf("duplicated_base: %d, factored_bottom: %d, factored_top: %d\n", duplicated_base, factored_bottom, factored_top);

                for (long i = factored_bottom; i <= factored_top; ++i) {
                    long check_num = i * duplicated_base;
                    if(i < 10 * base && check_num >= bottom && check_num <= top) {
                        // check for leading zeros

                        if (Math.floor(Math.log10(check_num)) + 1 == (Math.floor(Math.log10(base)) + 1) * duplicate_count) {
                            collected_numbers.add(check_num);
                        }
                        // System.out.printf("dupbase: %d, num: %d\n", duplicated_base, check_num);
                    }
                }
            }

            base *= 10;
        }

        long result = 0;
        for (long d : collected_numbers) {
            // System.out.printf("%d\n", d);
            result += d;
        }
        return result;
    }
}

public class Main {
    public static void main(String[] args) {
        
        try {
            // read lines from inputs/01.txt
            String[] ranges = Files.readAllLines(Paths.get("inputs/02.txt")).get(0).split(",");
            
            long pt_1_count = 0;
            long pt_2_count = 0;

            // step through all the ranges
            for (String range : ranges) {
                long bottom = Long.parseLong(range.split("-")[0]);
                long top = Long.parseLong(range.split("-")[1]);
                for (long i = DayTwo.get_min_doubler(bottom); i <= DayTwo.get_max_doubler(top); ++i) {
                    pt_1_count += DayTwo.double_number(i);
                }

                pt_2_count += DayTwo.pr_2_range(bottom, top);
            }

            // print result
            System.out.printf("Part 1: %d\nPart 2: %d\n", pt_1_count, pt_2_count);
        } catch (IOException e) {
            System.err.print("Failed to read input file: " + e.getMessage());
        }
    }
}
