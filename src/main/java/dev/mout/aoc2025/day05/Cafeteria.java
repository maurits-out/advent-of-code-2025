package dev.mout.aoc2025.day05;

import support.InputSupport;

import java.util.List;

import static java.util.Comparator.comparingLong;

public class Cafeteria {

    private final List<Range> ranges;
    private final List<Long> ids;

    private Cafeteria(List<Range> ranges, List<Long> ids) {
        this.ranges = ranges;
        this.ids = ids;
    }

    private long part1() {
        return ids.stream().filter(this::isFresh).count();
    }

    private long part2() {
        var sortedRanges = ranges.stream().sorted(comparingLong(Range::from)).toList();
        long count = 0;
        long last = sortedRanges.getFirst().from() - 1;
        for (Range current : sortedRanges) {
            long overlap = calculateOverlap(current, last);
            count += current.to() - current.from() + 1 - overlap;
            last = Math.max(current.to(), last);
        }
        return count;
    }

    private long calculateOverlap(Range current, long last) {
        if (current.from() > last) {
            return 0;
        }
        return Math.min(current.to(), last) - current.from() + 1;
    }

    private boolean isFresh(long id) {
        return ranges.stream().anyMatch(range -> range.isInRange(id));
    }

    private record Range(long from, long to) {
        private boolean isInRange(long id) {
            return id >= from && id <= to;
        }
    }

    private static List<Long> parseIds(String section) {
        return section.lines()
                .map(Long::parseLong)
                .toList();
    }

    private static List<Range> parseRanges(String ranges) {
        return ranges.lines()
                .map(Cafeteria::parseRange)
                .toList();
    }

    private static Range parseRange(String line) {
        var idx = line.indexOf('-');
        var from = Long.parseLong(line.substring(0, idx));
        var to = Long.parseLong(line.substring(idx + 1));
        return new Range(from, to);
    }

    public static void main(String[] args) {
        List<String> sections = InputSupport.readSections(5);
        List<Range> ranges = parseRanges(sections.getFirst());
        List<Long> ids = parseIds(sections.getLast());
        var cafeteria = new Cafeteria(ranges, ids);
        System.out.printf("Part 1: %d%n", cafeteria.part1());
        System.out.printf("Part 2: %d%n", cafeteria.part2());
    }
}
