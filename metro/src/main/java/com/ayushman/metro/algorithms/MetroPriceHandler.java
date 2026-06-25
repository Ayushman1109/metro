package com.ayushman.metro.algorithms;

import com.ayushman.metro.repository.StationRepository;
import com.ayushman.metro.entity.Station;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class MetroPriceHandler {
    private final StationRepository stationRepository;

    private static class Node implements Comparable<Node> {
        private final Long id;
        private final Double distance;

        public Node(Long id, Double distance) {
            this.id = id;
            this.distance = distance;
        }

        public Long id() { return id; }
        public Double distance() { return distance; }

        @Override
        public int compareTo(Node o) {
            return this.distance.compareTo(o.distance());
        }
    }

    public static class PathResult {
        private final List<Long> path;
        private final Double distance;

        public PathResult(List<Long> path, Double distance) {
            this.path = path;
            this.distance = distance;
        }

        public List<Long> path() { return path; }
        public Double distance() { return distance; }
    }

    public Integer getPrice(Long station1Id, Long station2Id){
        Double distance = getShortestPath(station1Id, station2Id).distance();
        
        int baseFare = 10;
        double baseDistance = 2.0;
        int ratePerExtraKm = 5;
        int maxFare = 60;
        
        if (distance <= baseDistance) {
            return baseFare;
        }
        
        double extraDistance = distance - baseDistance;
        int additionalFare = (int) Math.ceil(extraDistance) * ratePerExtraKm;
        
        int totalFare = baseFare + additionalFare;
        return Math.min(totalFare, maxFare);
    }

    public PathResult getShortestPath(Long station1Id, Long station2Id) {
        if (station1Id.equals(station2Id)) {
            return new PathResult(Collections.singletonList(station1Id), 0.0);
        }

        Map<Long, Double> shortestDistances = new HashMap<>();
        shortestDistances.put(station1Id, 0.0);

        Map<Long, Long> previousNodes = new HashMap<>();

        PriorityQueue<Node> q = new PriorityQueue<>();
        q.offer(new Node(station1Id, 0.0));
        
        Set<Long> visited = new HashSet<>();

        while (!q.isEmpty()) {
            Node curr = q.poll();
            Long id = curr.id();
            Double distance = curr.distance();

            if (id.equals(station2Id)) {
                List<Long> path = new ArrayList<>();
                Long current = station2Id;
                while (current != null) {
                    path.add(current);
                    current = previousNodes.get(current);
                }
                Collections.reverse(path);
                return new PathResult(path, distance);
            }

            if (visited.contains(id)) {
                continue;
            }
            visited.add(id);

            Station station = stationRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Station not found with id: " + id));

            Map<Long, Double> adj = station.getAdj();
            if (adj == null) continue;

            for (Map.Entry<Long, Double> neighbor : adj.entrySet()) {
                Long neighbourId = neighbor.getKey();
                Double weight = neighbor.getValue();
                
                if (weight == null || visited.contains(neighbourId)) continue;

                Double newDist = distance + weight;
                if (newDist < shortestDistances.getOrDefault(neighbourId, Double.MAX_VALUE)) {
                    shortestDistances.put(neighbourId, newDist);
                    previousNodes.put(neighbourId, id);
                    q.offer(new Node(neighbourId, newDist));
                }
            }
        }
        throw new NoSuchElementException("No path found between the selected stations");
    }
}
