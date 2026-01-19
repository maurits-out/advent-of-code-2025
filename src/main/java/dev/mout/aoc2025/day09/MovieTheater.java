package dev.mout.aoc2025.day09;

import support.InputSupport;
import support.Pair;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class MovieTheater {

    private final List<RedTile> redTiles;

    private MovieTheater(List<RedTile> redTiles) {
        this.redTiles = redTiles;
    }

    private long part1() {
        return generateRedTilePairs()
                .mapToLong(pair -> calculateArea(pair.first(), pair.second()))
                .max().orElseThrow();
    }

    private long calculateArea(RedTile tile1, RedTile tile2) {
        return (long) (Math.abs(tile2.x - tile1.x) + 1) * (Math.abs(tile2.y - tile1.y) + 1);
    }

    private record RedTile(int x, int y) {
    }

    private Stream<Pair<RedTile>> generateRedTilePairs() {
        return IntStream.range(0, redTiles.size() - 1)
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, redTiles.size())
                        .mapToObj(j -> new Pair<>(redTiles.get(i), redTiles.get(j))));
    }

    private static RedTile parseLine(String line) {
        int idx = line.indexOf(',');
        return new RedTile(
                Integer.parseInt(line.substring(0, idx)),
                Integer.parseInt(line.substring(idx + 1))
        );
    }

    public static void main(String[] args) {
        List<RedTile> redTiles = InputSupport.readAndParseLines(9, MovieTheater::parseLine);
        MovieTheater movieTheater = new MovieTheater(redTiles);
        System.out.printf("Part 1: %d\n", movieTheater.part1());
    }
}
