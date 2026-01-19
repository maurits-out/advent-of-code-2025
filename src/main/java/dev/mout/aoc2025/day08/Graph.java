package dev.mout.aoc2025.day08;

import java.util.*;

class Graph<V> {

    private final Map<V, List<V>> adjacencyList = new HashMap<>();

    Graph(Collection<V> vertices) {
        vertices.forEach(vertex -> adjacencyList.put(vertex, new ArrayList<>()));
    }

    void addEdge(V v1, V v2) {
        adjacencyList.get(v1).add(v2);
        adjacencyList.get(v2).add(v1);
    }

    List<Set<V>> getComponents() {
        Set<V> remaining = new HashSet<>(adjacencyList.keySet());
        List<Set<V>> components = new ArrayList<>();
        while (!remaining.isEmpty()) {
            V start = remaining.iterator().next();
            Set<V> component = getComponent(start);
            remaining.removeAll(component);
            components.add(component);
        }
        return components;
    }

    boolean hasOneComponent() {
        V start = adjacencyList.keySet().iterator().next();
        Set<V> component = getComponent(start);
        return component.size() == adjacencyList.size();
    }

    private Set<V> getComponent(V start) {
        Set<V> component = new HashSet<>();
        Set<V> visited = new HashSet<>();
        Queue<V> remaining = new LinkedList<>();
        remaining.offer(start);
        while (!remaining.isEmpty()) {
            V current = remaining.poll();
            if (!visited.contains(current)) {
                component.add(current);
                for (V neighbor : adjacencyList.get(current)) {
                    remaining.offer(neighbor);
                }
                visited.add(current);
            }
        }
        return component;
    }
}
