package dev.mout.aoc2025.day10;

import java.util.Set;

record Machine(String targetIndicatorLights, Set<Button> buttons) {

    String allLightsOff() {
        return ".".repeat(targetIndicatorLights.length());
    }

    record Button(Set<Integer> wiring) {

        String apply(String indicatorLights) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < indicatorLights.length(); i++) {
                sb.append(switchIfWired(indicatorLights.charAt(i), wiring.contains(i)));
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
    }
}

