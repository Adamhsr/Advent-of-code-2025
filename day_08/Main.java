package day_08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.Objects;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

class Point {
    long x, y, z;

    public Point(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double distance(Point other) {
        long dx = x - other.x;
        long dy = y - other.y;
        long dz = z - other.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}

class Connection {
    Point p1, p2;

    public Connection(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Connection other = (Connection) obj;
        return (p1 == other.p1 && p2 == other.p2) || (p1 == other.p2 && p2 == other.p1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p1, p2);
    }
}

class DayEight {
    Vector<Point> points;
    Vector<Set<Integer>> islands;
    boolean connections[][];

    public DayEight(String lines[]) {
        points = new Vector<Point>();
        connections = new boolean[lines.length][lines.length];
        islands = new Vector<>();

        for (String line: lines) {
            String coords[] = line.split(",");
            points.add(new Point(Long.parseLong(coords[0]), Long.parseLong(coords[1]), Long.parseLong(coords[2])));

            // each island is initally just of size one
            Set<Integer> island = new HashSet<>();
            island.add(points.size() - 1);
            islands.add(island);
        }
    }

    private int[] connect_two_clostest_points() {
        double shortest_dist = -1;
        int close_points[] = {-1, -1};

        for (int i = 0; i < points.size(); ++i) {
            for (int j = i + 1; j < points.size(); ++j) {
                if (i == j || connections[i][j]) {
                    continue;
                }
                double distance = points.get(i).distance(points.get(j));
                if (shortest_dist < 0 || distance < shortest_dist) {
                    shortest_dist = distance;
                    close_points[0] = i;
                    close_points[1] = j;
                }
            }
        }
        connections[close_points[0]][close_points[1]] = true;
        connections[close_points[1]][close_points[0]] = true;

        // merge islands
        int island_one_index = -1;
        int island_two_index = -1;
        for (int i = 0; i < islands.size(); ++i) {
            if (islands.get(i).contains(close_points[0])) island_one_index = i;
            if (islands.get(i).contains(close_points[1])) island_two_index = i;
        }
        if (island_one_index != island_two_index) {
            // island_one U= island_two
            islands.get(island_one_index).addAll(islands.get(island_two_index));
            // delete island_two
            islands.remove(island_two_index);
        }

        return new int[] {close_points[0], close_points[1]};
    }

    private void connect() {
        for (int connects = 0; connects < 1000; ++connects) {
            connect_two_clostest_points();
        }
    }

    private boolean all_connected() {
        return (islands.size() == 1);
    }

    public long part_one() {
        connect();
        

        Vector<Integer> sizes = islands.stream().map(island -> island.size()).collect(Collectors.toCollection(Vector::new));
        Collections.sort(sizes, Collections.reverseOrder());

        long prod = 1;
        for (int i = 0; i < 3; ++i) {
            prod *= sizes.get(i);
        }

        return prod;
    }

    public long part_two() {
        while (true) {
            int connected[] = connect_two_clostest_points();
            if (all_connected()) {
                return points.get(connected[0]).x * points.get(connected[1]).x;
            }
        }
    }

}


public class Main {
    public static void main(String[] args) {
        
        try {
            // read input
            String lines[] = Files.readString(Paths.get("inputs/08.txt")).split("\n");
            


            DayEight day_eight = new DayEight(lines);
            
            System.out.printf("pt 1: %d\npt 2: %d\n", day_eight.part_one(), day_eight.part_two());
            
        } catch (IOException e) {
            System.err.print("Failed to read input file: " + e.getMessage());
        }
    }
}
