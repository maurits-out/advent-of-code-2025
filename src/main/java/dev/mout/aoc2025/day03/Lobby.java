package dev.mout.aoc2025.day03;

import support.InputSupport;

import java.util.List;

public class Lobby {

    private final List<String> batteryBanks;

    private Lobby(List<String> batteryBanks) {
        this.batteryBanks = batteryBanks;
    }

    private long part1() {
        return sumLargestPossibleJoltagse(2);
    }

    private long part2() {
        return sumLargestPossibleJoltagse(12);
    }

    private long sumLargestPossibleJoltagse(int batteriesToActivate) {
        return batteryBanks
                .parallelStream()
                .mapToLong(bank -> largestPossibleJoltage(bank, batteriesToActivate))
                .sum();
    }

    private long largestPossibleJoltage(String bank, int batteriesToActivate) {
        var startIndex = 0;
        var result = 0L;
        for (var i = 0; i < batteriesToActivate; i++) {
            var maxJoltage = 0;
            var remainingBatteries = batteriesToActivate - i - 1;
            var searchLimit = bank.length() - remainingBatteries;
            var bestIndex = startIndex;
            for (var j = startIndex; j < searchLimit; j++) {
                var joltage = bank.charAt(j) - '0';
                if (joltage > maxJoltage) {
                    maxJoltage = joltage;
                    bestIndex = j;
                }
            }
            startIndex = bestIndex + 1;
            result = (result * 10) + maxJoltage;
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> batteryBanks = InputSupport.readLines(3);
        var lobby = new Lobby(batteryBanks);
        System.out.printf("Part 1: %d%n", lobby.part1());
        System.out.printf("Part 2: %d%n", lobby.part2());
    }
}
