package dev.mout.aoc2025.day10;

import dev.mout.aoc2025.day10.Machine.Button;
import support.InputSupport;

import java.util.*;

import static java.util.stream.Collectors.toSet;

public class Factory {

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
        String start = machine.allLightsOff();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, Integer> distance = new HashMap<>();

        queue.offer(start);
        visited.add(start);
        distance.put(start, 0);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            Integer distanceOfCurrent = distance.get(current);
            if (current.equals(machine.targetIndicatorLights())) {
                return distanceOfCurrent;
            }
            unvisitedNeighbors(current, visited, machine.buttons()).forEach(neighbor -> {
                visited.add(neighbor);
                distance.put(neighbor, distanceOfCurrent + 1);
                queue.offer(neighbor);
            });
        }
        throw new IllegalStateException("No sequence exists");
    }

    private Set<String> unvisitedNeighbors(String current, Set<String> visited, Set<Button> buttons) {
        return buttons.stream()
                .map(button -> button.apply(current))
                .filter(neighbor -> !visited.contains(neighbor))
                .collect(toSet());
    }

    public static void main(String[] args) {
        MachineParser parser = new MachineParser();
        List<Machine> machines = InputSupport.readAndParseLines(10, parser::parse);
        Factory factory = new Factory(machines);
        System.out.printf("Part 1: %d%n", factory.part1());
    }
}
