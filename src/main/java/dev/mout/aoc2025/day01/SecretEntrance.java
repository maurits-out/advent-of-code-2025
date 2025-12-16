package dev.mout.aoc2025.day01;

import support.InputSupport;

import java.util.List;

public class SecretEntrance {

    private static final char DIRECTION_RIGHT = 'R';
    private static final int START_POSITION = 50;
    private static final int DIAL_SIZE = 100;

    private final List<Integer> movements;

    public SecretEntrance(List<Integer> movements) {
        this.movements = movements;
    }

    private int part1() {
        var currentPosition = START_POSITION;
        var password = 0;
        for (var movement : movements) {
            currentPosition += movement;
            currentPosition = normalizePosition(currentPosition);
            if (currentPosition == 0) {
                password++;
            }
        }
        return password;
    }

    private int part2() {
        var position = START_POSITION;
        var password = 0;
        for (var movement : movements) {
            var newPosition = position + movement;
            password += countZeros(position, newPosition);
            position = normalizePosition(newPosition);
        }
        return password;
    }

    private int normalizePosition(int currentPosition) {
        var normalized = currentPosition % DIAL_SIZE;
        if (normalized < 0) {
            normalized += DIAL_SIZE;
        }
        return normalized;
    }

    private int countZeros(int position, int newPosition) {
        var count = Math.abs(newPosition) / DIAL_SIZE;
        if (position > 0 && newPosition <= 0) {
            count++;
        }
        return count;
    }

    private static int parseInstruction(String instruction) {
        var direction = instruction.charAt(0);
        var distance = Integer.parseInt(instruction.substring(1));
        return direction == DIRECTION_RIGHT ? distance : -distance;
    }

    static void main() {
        var movements = InputSupport.readAndParseLines(1, SecretEntrance::parseInstruction);
        var secretEntrance = new SecretEntrance(movements);
        System.out.printf("Part 1: %d\n", secretEntrance.part1());
        System.out.printf("Part 2: %d\n", secretEntrance.part2());
    }
}
