package dev.mout.aoc2025.day08;

import support.InputSupport;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

class Playground {

    private final List<Box> boxes;
    private final List<Pair<Box>> boxPairsSortedByDistance;

    private Playground(List<Box> boxes) {
        this.boxes = boxes;
        this.boxPairsSortedByDistance = getBoxPairsSortedByDistance();
    }

    private int part1() {
        Graph<Box> graph = new Graph<>(boxes);
        boxPairsSortedByDistance.stream()
                .limit(1000)
                .forEach(pair -> graph.addEdge(pair.first(), pair.second()));
        return graph.getComponents().stream()
                .sorted(Comparator.<Set<Box>>comparingInt(Set::size).reversed())
                .limit(3)
                .mapToInt(Set::size)
                .reduce(1, (a, b) -> a * b);
    }

    private long part2() {
        Graph<Box> graph = new Graph<>(boxes);
        Pair<Box> pair;
        Iterator<Pair<Box>> iterator = boxPairsSortedByDistance.iterator();
        do {
            pair = iterator.next();
            graph.addEdge(pair.first(), pair.second());
        } while (graph.getComponents().size() > 1);
        return (long) pair.first().x * pair.second().x;
    }

    private List<Pair<Box>> getBoxPairsSortedByDistance() {
        return IntStream.range(0, boxes.size() - 1)
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, boxes.size())
                        .mapToObj(j -> new Pair<>(boxes.get(j), boxes.get(i))))
                .sorted(Comparator.comparingDouble(pair -> pair.first().distanceTo(pair.second())))
                .toList();
    }

    private record Box(int x, int y, int z) {
        private double distanceTo(Box other) {
            return (long) (x - other.x) * (x - other.x)
                    + (long) (y - other.y) * (y - other.y)
                    + (long) (z - other.z) * (z - other.z);
        }
    }

    private static Box parseLine(String line) {
        int first = line.indexOf(',');
        int last = line.lastIndexOf(',');
        return new Box(
                Integer.parseInt(line.substring(0, first)),
                Integer.parseInt(line.substring(first + 1, last)),
                Integer.parseInt(line.substring(last + 1))
        );
    }

    public static void main(String[] args) {
        List<Box> boxes = InputSupport.readAndParseLines(8, Playground::parseLine);
        Playground playground = new Playground(boxes);
        System.out.printf("Part 1: %d%n", playground.part1());
        System.out.printf("Part 2: %s%n", playground.part2());
    }
}
