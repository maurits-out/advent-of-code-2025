package dev.mout.aoc2025.day10;

import java.util.*;
import java.util.stream.Stream;

abstract class BFSTemplate<T> {

    final int minimalDistance() {
        T start = startState();
        Queue<T> queue = new LinkedList<>();
        Set<T> visited = new HashSet<>();
        Map<T, Integer> distance = new HashMap<>();

        queue.offer(start);
        visited.add(start);
        distance.put(start, 0);

        while (!queue.isEmpty()) {
            T current = queue.poll();
            Integer distanceOfCurrent = distance.get(current);
            if (isTarget(current)) {
                return distanceOfCurrent;
            }
            neighbors(current)
                    .filter(neighbor -> !visited.contains(neighbor))
                    .forEach(neighbor -> {
                        visited.add(neighbor);
                        distance.put(neighbor, distanceOfCurrent + 1);
                        queue.offer(neighbor);
                    });
        }
        throw new IllegalStateException("No path exists");
    }

    abstract T startState();

    abstract boolean isTarget(T state);

    abstract Stream<T> neighbors(T current);
}
