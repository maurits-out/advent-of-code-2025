package dev.mout.aoc2025.day10;

import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;

class MachineParser {

    private static final Pattern DIGITS_PATTERN = Pattern.compile("\\d+");
    private static final Pattern PARTS_PATTERN = Pattern.compile(" ");

    record Machine(int targetIndicatorLights, int[] buttons) {

    }

    Machine parse(String line) {
        String[] parts = PARTS_PATTERN.split(line);
        return new Machine(parseIndicatorLights(parts[0]), parseButtons(parts));
    }

    private int parseIndicatorLights(String part) {
        int v = 0;
        for (int i = 0; i < part.length() - 2; i++) {
            if (part.charAt(i + 1) == '#') {
                v |= 1 << i;
            }
        }
        return v;
    }

    private int[] parseButtons(String[] parts) {
        return stream(parts, 1, parts.length - 1)
                .mapToInt(this::parseButton)
                .toArray();
    }

    private int parseButton(String part) {
        return DIGITS_PATTERN.matcher(part).results()
                .mapToInt(result -> parseInt(result.group()))
                .reduce(0, (acc, wire) -> acc | (1 << wire));
    }
}
