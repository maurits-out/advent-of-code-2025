package dev.mout.aoc2025.day10;

import dev.mout.aoc2025.day10.MachineParser.Machine;
import support.InputSupport;

import java.util.*;

import static java.util.Arrays.stream;

class Factory {

    private final List<Machine> machines;

    private Factory(List<Machine> machines) {
        this.machines = machines;
    }

    private int part1() {
        return machines.parallelStream()
                .mapToInt(this::countFewestPresses)
                .sum();
    }

    private int countFewestPresses(Machine machine) {
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        Map<Integer, Integer> distance = new HashMap<>();

        queue.offer(0);
        visited.add(0);
        distance.put(0, 0);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            int distanceToCurrent = distance.get(current);
            if (current == machine.targetIndicatorLights()) {
                return distanceToCurrent;
            }
            stream(machine.buttons())
                    .map(button -> current ^ button)
                    .filter(next -> !visited.contains(next))
                    .forEach(next -> {
                        visited.add(next);
                        distance.put(next, distanceToCurrent + 1);
                        queue.offer(next);
                    });
        }
        throw new IllegalStateException("No path exists");
    }

    public static void main(String[] args) {
        MachineParser parser = new MachineParser();
        List<Machine> machines = InputSupport.readAndParseLines(10, parser::parse);
        Factory factory = new Factory(machines);
        System.out.printf("Part 1: %d%n", factory.part1());
    }
}
