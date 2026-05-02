package dev.mout.aoc2025.day10;

import dev.mout.aoc2025.day10.Machine.Button;
import support.InputSupport;

import java.util.List;
import java.util.stream.Stream;

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
        BFSTemplate<String> bfs = new BFSTemplate<>() {
            @Override
            String startState() {
                return machine.allLightsOff();
            }

            @Override
            boolean isTarget(String state) {
                return state.equals(machine.targetIndicatorLights());
            }

            @Override
            Stream<String> neighbors(String current) {
                return machine.buttons().stream().map(button -> press(button, current));
            }

            private String press(Button button, String indicatorLights) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < indicatorLights.length(); i++) {
                    sb.append(switchIfWired(indicatorLights.charAt(i), button.wiring().contains(i)));
                }
                return sb.toString();
            }

            private char switchIfWired(char current, boolean isWired) {
                if (isWired) {
                    if (current == '.') {
                        return '#';
                    }
                    return '.';
                }
                return current;
            }
        };
        return bfs.minimalDistance();
    }

    public static void main(String[] args) {
        MachineParser parser = new MachineParser();
        List<Machine> machines = InputSupport.readAndParseLines(10, parser::parse);
        Factory factory = new Factory(machines);
        System.out.printf("Part 1: %d%n", factory.part1());
    }
}
