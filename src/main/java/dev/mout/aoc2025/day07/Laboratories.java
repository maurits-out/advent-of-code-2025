package dev.mout.aoc2025.day07;

import support.InputSupport;

import java.util.*;

class Laboratories {

    private final char[][] diagram;
    private final int height;
    private final int width;
    private final int startCol;

    private Laboratories(char[][] diagram) {
        this.diagram = diagram;
        this.height = diagram.length;
        this.width = diagram[0].length;
        this.startCol = findStartColumnOnTopRow();
    }

    private int part1() {
        Location start = new Location(0, startCol);
        Deque<Location> stack = new LinkedList<>();
        stack.push(start);
        Set<Location> visited = new HashSet<>();
        visited.add(start);
        int splitCount = 0;

        while (!stack.isEmpty()) {
            Location location = stack.pop();
            char ch = diagram[location.row][location.column];
            switch (ch) {
                case 'S', '.':
                    if (location.row < diagram.length - 1) {
                        Location down = location.down();
                        if (!visited.contains(down)) {
                            stack.push(down);
                            visited.add(down);
                        }
                    }
                    break;
                case '^':
                    Location left = location.left();
                    if (!visited.contains(left)) {
                        stack.push(left);
                        visited.add(left);
                    }
                    Location right = location.right();
                    if (!visited.contains(right)) {
                        stack.push(right);
                        visited.add(right);
                    }
                    splitCount++;
            }
        }

        return splitCount;
    }

    private long part2() {
        long[][] res = new long[height][width];
        res[0][startCol] = 1;
        for (int r = 1; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (diagram[r][c] == '.') {
                    if (diagram[r - 1][c] == '.' || diagram[r - 1][c] == 'S') {
                        res[r][c] = res[r - 1][c];
                    }
                    if (c > 0 && diagram[r][c - 1] == '^') {
                        res[r][c] += res[r - 1][c - 1];
                    }
                    if (c < width - 1 && diagram[r][c + 1] == '^') {
                        res[r][c] += res[r - 1][c + 1];
                    }
                }
            }
        }
        return Arrays.stream(res[height - 1]).sum();
    }

    private int findStartColumnOnTopRow() {
        int c = 0;
        while (diagram[0][c] != 'S') {
            c++;
        }
        return c;
    }

    private record Location(int row, int column) {
        private Location left() {
            return new Location(row, column - 1);
        }

        private Location right() {
            return new Location(row, column + 1);
        }

        private Location down() {
            return new Location(row + 1, column);
        }
    }

    public static void main(String[] args) {
        char[][] diagram = InputSupport.readAsGrid(7);
        Laboratories laboratories = new Laboratories(diagram);
        System.out.printf("Part 1: %d%n", laboratories.part1());
        System.out.printf("Part 2: %d%n", laboratories.part2());
    }
}
