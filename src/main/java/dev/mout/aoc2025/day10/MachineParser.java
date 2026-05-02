package dev.mout.aoc2025.day10;

import dev.mout.aoc2025.day10.Machine.Button;

import java.util.Set;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

class MachineParser {

    private static final Pattern DIGITS_PATTERN = Pattern.compile("\\d+");
    private static final Pattern PARTS_PATTERN = Pattern.compile(" ");

    Machine parse(String line) {
        String[] parts = PARTS_PATTERN.split(line);
        return new Machine(parseIndicatorLights(parts[0]), parseButtons(parts));
    }

    private String parseIndicatorLights(String part) {
        return part.substring(1, part.length() - 1);
    }

    private Set<Button> parseButtons(String[] parts) {
        return stream(parts, 1, parts.length - 1)
                .map(this::parseButton)
                .collect(toSet());
    }

    private Button parseButton(String part) {
        Set<Integer> wiring = DIGITS_PATTERN.matcher(part).results()
                .map(result -> parseInt(result.group()))
                .collect(toSet());
        return new Button(wiring);
    }
}
