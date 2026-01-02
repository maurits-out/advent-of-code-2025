package dev.mout.aoc2025.day04;

import support.InputSupport;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class PrintingDepartment {

    private static final List<Location> OFFSETS = List.of(
            new Location(-1, -1), new Location(-1, 0), new Location(-1, 1),
            new Location(0, -1), new Location(0, 1),
            new Location(1, -1), new Location(1, 0), new Location(1, 1)
    );

    private final Set<Location> paperRolls;

    private PrintingDepartment(Set<Location> paperRolls) {
        this.paperRolls = paperRolls;
    }

    private long part1() {
        return paperRolls.stream()
                .filter(current -> isAccessible(current, paperRolls))
                .count();
    }

    private int part2() {
        var remaining = new HashSet<>(paperRolls);
        var count = 0;
        while (true) {
            var accessible = remaining.stream()
                    .filter(current -> isAccessible(current, remaining))
                    .collect(Collectors.toSet());
            if (accessible.isEmpty()) {
                break;
            }
            count += accessible.size();
            remaining.removeAll(accessible);
        }
        return count;
    }

    private boolean isAccessible(Location current, Set<Location> rolls) {
        return countNeighbors(current, rolls) < 4;
    }

    private long countNeighbors(Location current, Set<Location> rolls) {
        return neighbors(current).stream()
                .filter(rolls::contains)
                .count();
    }

    private Set<Location> neighbors(Location location) {
        var row = location.row();
        var col = location.column();
        return OFFSETS.stream()
                .map(o -> new Location(row + o.row(), col + o.column()))
                .collect(Collectors.toSet());
    }

    record Location(int row, int column) {
    }

    private static Set<Location> findPaperRollLocations() {
        Set<Location> rolls = new HashSet<>();
        var row = 0;
        for (var line : InputSupport.readLines(4)) {
            for (var column = 0; column < line.length(); column++) {
                if (line.charAt(column) == '@') {
                    rolls.add(new Location(row, column));
                }
            }
            row++;
        }
        return rolls;
    }

    public static void main(String[] args) {
        var paperRolls = findPaperRollLocations();
        var printingDepartment = new PrintingDepartment(paperRolls);
        System.out.printf("Part 1: %d%n", printingDepartment.part1());
        System.out.printf("Part 2: %d%n", printingDepartment.part2());
    }
}
