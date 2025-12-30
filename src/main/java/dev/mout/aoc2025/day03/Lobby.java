package dev.mout.aoc2025.day03;

import support.InputSupport;

import java.util.List;

public class Lobby {

    private final List<String> banks;

    private Lobby(List<String> banks) {
        this.banks = banks;
    }

    private int part1() {
        return banks.stream().mapToInt(this::largestPossibleJoltage).sum();
    }

    private int largestPossibleJoltage(String bank) {
        var firstBattery = 0;
        var firstIndex = -1;

        for (var i = 0; i < bank.length() - 1; i++) {
            var joltage = digitAt(bank, i);
            if (joltage > firstBattery) {
                firstBattery = joltage;
                firstIndex = i;
            }
        }

        var secondBattery = 0;
        for (var i = firstIndex + 1; i < bank.length(); i++) {
            var joltage = digitAt(bank, i);
            if (joltage > secondBattery) {
                secondBattery = joltage;
            }
        }

        return firstBattery * 10 + secondBattery;
    }

    private static int digitAt(String bank, int index) {
        return bank.charAt(index) - '0';
    }

    public static void main(String[] args) {
        List<String> banks = InputSupport.readLines(3);
        Lobby lobby = new Lobby(banks);
        System.out.printf("Part 1: %d%n", lobby.part1());
    }
}
