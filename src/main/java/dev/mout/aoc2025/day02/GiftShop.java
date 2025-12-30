package dev.mout.aoc2025.day02;

import support.InputSupport;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.LongStream;

public class GiftShop {

    private final List<IdRange> idRanges;

    private GiftShop(List<IdRange> idRanges) {
        this.idRanges = idRanges;
    }

    private long part1() {
        return sumInvalidIds(Pattern.compile("^(\\d+)\\1$"));
    }

    private long part2() {
        return sumInvalidIds(Pattern.compile("^(\\d+)(\\1)+$"));
    }

    private long sumInvalidIds(Pattern pattern) {
        return idRanges
                .parallelStream()
                .flatMapToLong(range -> LongStream.rangeClosed(range.firstId(), range.lastId()))
                .filter(id -> pattern.matcher(Long.toString(id)).matches())
                .sum();
    }

    private record IdRange(long firstId, long lastId) {
        private static IdRange of(String s) {
            var idx = s.indexOf('-');
            var firstId = Long.parseLong(s.substring(0, idx));
            var lastId = Long.parseLong(s.substring(idx + 1));
            return new IdRange(firstId, lastId);
        }
    }

    public static void main(String[] args) {
        var input = InputSupport.readString(2);
        var ranges = Arrays.stream(input.split(",")).map(IdRange::of).toList();
        var giftShop = new GiftShop(ranges);
        System.out.printf("Part 1: %d%n", giftShop.part1());
        System.out.printf("Part 2: %d%n", giftShop.part2());
    }
}
