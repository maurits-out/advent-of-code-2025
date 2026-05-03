package dev.mout.aoc2025.day10;

import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;

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
        return range(0, part.length() - 2)
                .filter(i -> part.charAt(i + 1) == '#')
                .reduce(0, (acc, i) -> acc | (1 << i));
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
