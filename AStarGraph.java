import java.util.*;

class Node {
    char name;
    double heuristic;
    Map<Node, Double> neighbors;

    public Node(char name, double heuristic) {
        this.name = name;
        this.heuristic = heuristic;
        this.neighbors = new HashMap<>(); //A map linking neighboring nodes and the cost to travel to them.
    }

    public void addNeighbor(Node neighbor, double cost) { //Adds a neighboring node and the cost to reach it.
        neighbors.put(neighbor, cost);
    }
}

public class AStarGraph {
    public static List<Character> aStar(Node start, Node goal) { 
        //Main method to find the shortest path from the start node to the goal node.
        Map<Node, Double> gScore = new HashMap<>(); //A map holding the cost of the cheapest path from the start to each node.
        Map<Node, Double> fScore = new HashMap<>(); //A map holding the estimated 
        //total cost (gScore + heuristic) from the start to the goal through each node.
        Map<Node, Node> cameFrom = new HashMap<>(); //A map used to reconstruct the path.

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(fScore::get));
        //A priority queue of nodes to be evaluated, sorted by their fScore.
        Set<Node> closedSet = new HashSet<>();
        // A set of nodes already evaluated.

        for (Node node : Arrays.asList(start, goal)) {
            gScore.put(node, Double.POSITIVE_INFINITY);
            fScore.put(node, Double.POSITIVE_INFINITY);
        }
        //Set gScore and fScore for all nodes to infinity, except the start node 
        //(gScore is 0 and fScore is its heuristic value).
        gScore.put(start, 0.0);
        fScore.put(start, start.heuristic);

        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current == goal) {
                return reconstructPath(cameFrom, current);
            }

            closedSet.add(current);

            for (Map.Entry<Node, Double> neighborEntry : current.neighbors.entrySet()) {
                Node neighbor = neighborEntry.getKey();
                double tentativeGScore = gScore.get(current) + neighborEntry.getValue();

                if (closedSet.contains(neighbor)) {
                    continue;
                }

                if (!openSet.contains(neighbor) || tentativeGScore < gScore.get(neighbor)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, tentativeGScore + neighbor.heuristic);

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return null; // No path found
    }

    public static List<Character> reconstructPath(Map<Node, Node> cameFrom, Node current) {
        //Reconstructs the path from the goal to the start node.
        //Starting from the goal node, the method traces back to the start node using the cameFrom map.
        //The path is built in reverse order and then reversed to get the correct start-to-goal sequence.
        List<Character> path = new ArrayList<>();
        path.add(current.name);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(0, current.name);
        }
        return path;
    }

    public static void main(String[] args) {
        Node S = new Node('S', 6);
        Node A = new Node('A', 4);
        Node B = new Node('B', 4);
        Node C = new Node('C', 4);
        Node D = new Node('D', 3.5);
        Node E = new Node('E', 1);
        Node F = new Node('F', 1);
        Node G = new Node('G', 0);

        S.addNeighbor(A, 2);
        S.addNeighbor(B, 3);
        B.addNeighbor(C, 1);
        B.addNeighbor(D, 3);
        A.addNeighbor(C, 3);
        C.addNeighbor(E, 3);
        C.addNeighbor(D, 1);
        D.addNeighbor(F, 2);
        E.addNeighbor(G, 2);
        F.addNeighbor(G, 1);

        List<Character> path = aStar(S, G);
        if (path != null) {
            System.out.println("Shortest path:" + path);
        } else {
            System.out.println("No path found from S to G");
        }
    }
}
