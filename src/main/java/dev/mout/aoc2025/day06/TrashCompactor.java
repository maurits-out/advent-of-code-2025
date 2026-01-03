package dev.mout.aoc2025.day06;

import support.InputSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.LongStream;

class TrashCompactor {

    private final List<Column> columns;

    private TrashCompactor(List<Column> columns) {
        this.columns = columns;
    }

    private long part1() {
        return calculateGrantTotal(this::convertPart1);
    }

    private long part2() {
        return calculateGrantTotal(this::convertPart2);
    }

    private long calculateGrantTotal(Function<List<String>, List<Integer>> converter) {
        return columns.stream()
                .mapToLong(column -> {
                    List<Integer> numbers = converter.apply(column.values());
                    return applyOperator(column.operator(), numbers);
                })
                .sum();
    }

    private List<Integer> convertPart1(List<String> values) {
        return values.stream()
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }

    private List<Integer> convertPart2(List<String> values) {
        int width = values.getFirst().length();
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            int number = 0;
            for (String value : values) {
                char ch = value.charAt(i);
                if (ch != ' ') {
                    number = (number * 10) + (ch - '0');
                }
            }
            numbers.add(number);
        }
        return numbers;
    }

    private long applyOperator(char operator, List<Integer> arguments) {
        LongStream stream = arguments.stream().mapToLong(a -> a);
        return switch (operator) {
            case '+' -> stream.reduce(0, Long::sum);
            case '*' -> stream.reduce(1, (a, b) -> a * b);
            default -> throw new IllegalArgumentException("unknown operator: " + operator);
        };
    }

    private record Column(List<String> values, char operator) {
    }

    private static List<Column> extractColumns(List<String> lines) {
        List<String> numberLines = lines.subList(0, lines.size() - 1);
        String operatorLine = lines.getLast();
        char currentOperator = operatorLine.charAt(0);
        int offsetCurrentOperator = 0;
        int offset = 1;
        List<Column> columns = new ArrayList<>();
        while (offset < operatorLine.length()) {
            char ch = operatorLine.charAt(offset);
            if (ch != ' ') {
                columns.add(createColumn(numberLines, offsetCurrentOperator, offset - 1, currentOperator));
                currentOperator = ch;
                offsetCurrentOperator = offset;
            }
            offset++;
        }
        columns.add(createColumn(numberLines, offsetCurrentOperator, offset, currentOperator));
        return columns;
    }

    private static Column createColumn(List<String> numberLines, int beginIndex, int endIndex, char operator) {
        var numbers = numberLines.stream()
                .map(line -> line.substring(beginIndex, endIndex))
                .toList();
        return new Column(numbers, operator);
    }

    public static void main(String[] args) {
        List<String> lines = InputSupport.readLines(6);
        List<Column> columns = extractColumns(lines);
        TrashCompactor trashCompactor = new TrashCompactor(columns);
        System.out.printf("Part 1: %s%n", trashCompactor.part1());
        System.out.printf("Part 2: %s%n", trashCompactor.part2());
    }
}
