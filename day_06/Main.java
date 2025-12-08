package day_06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;


class DaySix {
    private Vector<Vector<Integer>> numbers;
    private Vector<Character> operations;
    public long part_two;

    public DaySix(String lines[]) {
        numbers = new Vector<Vector<Integer>>();
        operations = new Vector<Character>();
        
        for (String line : lines) {
            if (Character.isDigit(line.toCharArray()[0]) || line.toCharArray()[0] == ' ') {
                Vector<Integer> number_line = new Vector<Integer>();
                String nums[] = line.split(" ");
                for (String num: nums) {
                    if (num.length() < 1) continue;
                    number_line.add(Integer.parseInt(num));
                }
                numbers.add(number_line);
            } else {
                for (char c : line.toCharArray()) {
                    if (c == ' ' || c == '\n') continue;
                    operations.add(c);
                }
            }
        }


        // part two
        part_two = 0;
        boolean is_start = true;
        long partial_sum = 0;
        long partial_prod = 1;
        char operation = '\0';
        long curr_num = -1;
        for (int i = 0; i < lines[0].length(); ++i) {
            if (is_start) {
                is_start = false;
                partial_sum = 0;
                partial_prod = 1;
                operation = '\0';
            }
            
            // -1 indicates no number
            curr_num = -1;

            // loop through column
            for (int j = 0; j < lines.length; ++j) {
                char c = lines[j].charAt(i);

                if (j == lines.length - 1) {
                    if (operation == '\0') operation = c;
                    if (operation == '*') partial_prod *= (curr_num == -1)? 1: curr_num;
                    else partial_sum += (curr_num == -1)? 0: curr_num;
                    if (curr_num != -1) System.out.printf("%d %c ", curr_num, operation);
                } else {
                    // on number
                    if (c != ' ') {
                        if (curr_num == -1) curr_num = 0;
                        curr_num = 10 * curr_num + c - '0';
                    }
                }
                
            }

            if (curr_num == -1) {
                // we are at the end of the equasion
                part_two += (operation == '*')? partial_prod: partial_sum;
                System.out.printf("= %d\n", (operation == '*')? partial_prod: partial_sum);
                is_start = true;
            }
        }
        if (curr_num != -1) {
            // we are at the end of the equasion and didnt add the answer so do that now
            part_two += (operation == '*')? partial_prod: partial_sum;
            System.out.printf("= %d\n", (operation == '*')? partial_prod: partial_sum);
        }


    }

    public long part_one() {
        long total = 0;
        for (int i = 0; i < operations.size(); ++i) {
            if (operations.get(i) == '*') {
                long prod = 1;
                for (int j = 0; j < numbers.size(); ++j) {
                    prod *= numbers.get(j).get(i);
                }
                total += prod;
            } else {
                long sum = 0;
                for (int j = 0; j < numbers.size(); ++j) {
                    sum += numbers.get(j).get(i);
                }
                total += sum;
            }
        }
        return total;
    }
}


public class Main {
    public static void main(String[] args) {
        
        try {
            // read input
            String parts[] = Files.readString(Paths.get("inputs/06.txt")).split("\n");
            


            DaySix day_six = new DaySix(parts);

            // for (String item: items) {
            //     day_four.process_item(Long.parseLong(item));
            // }

            // long part_two_count = day_four.part_two();

            System.out.printf("pt 1: %d\npt 2: %d\n", day_six.part_one(), day_six.part_two);
            // 26252425089 too low
            
        } catch (IOException e) {
            System.err.print("Failed to read input file: " + e.getMessage());
        }
    }
}
