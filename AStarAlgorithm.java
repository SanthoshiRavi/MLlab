import java.util.*;

class Node implements Comparable<Node> {
    public String name;
    public ArrayList<Edge> neighbors;
    public double cost; // g cost
    public double heuristic; // h cost
    public double f; // f cost
    public Node parent;

    public Node(String name, double heuristic) {
        this.name = name;
        this.neighbors = new ArrayList<>();
        this.cost = Double.MAX_VALUE;
        this.heuristic = heuristic;
        this.f = Double.MAX_VALUE;
        this.parent = null;
    }

    public void addNeighbor(Edge edge) {
        neighbors.add(edge);
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.f, other.f);
    }
}

class Edge {
    public Node target;
    public double cost;

    public Edge(Node target, double cost) {
        this.target = target;
        this.cost = cost;
    }
}

public class AStarAlgorithm {

    public static List<Node> aStar(Node start, Node goal) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<Node> closedList = new HashSet<>();

        start.cost = 0;
        start.f = start.heuristic;
        openList.add(start);

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.equals(goal)) {
                return reconstructPath(current);
            }

            closedList.add(current);

            for (Edge edge : current.neighbors) {
                Node neighbor = edge.target;
                if (closedList.contains(neighbor)) {
                    continue;
                }

                double tentativeG = current.cost + edge.cost;

                if (!openList.contains(neighbor) || tentativeG < neighbor.cost) {
                    neighbor.parent = current;
                    neighbor.cost = tentativeG;
                    neighbor.f = neighbor.cost + neighbor.heuristic;

                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }
        }
        return new ArrayList<>(); // empty list means no path found
    }

    private static List<Node> reconstructPath(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(0, node);
            node = node.parent;
        }
        return path;
    }

    public static void main(String[] args) {
        // Create graph nodes
        Node S = new Node("S", 6);
        Node A = new Node("A", 4);
        Node B = new Node("B", 4);
        Node C = new Node("C", 4);
        Node D = new Node("D", 3.5);
        Node E = new Node("E", 1);
        Node F = new Node("F", 1);
        Node G = new Node("G", 0);

        // Add edges
        S.addNeighbor(new Edge(A, 2));
        S.addNeighbor(new Edge(B, 3));
        B.addNeighbor(new Edge(C, 1));
        B.addNeighbor(new Edge(D, 3));
        A.addNeighbor(new Edge(C, 3));
        C.addNeighbor(new Edge(E, 3));
        C.addNeighbor(new Edge(D, 1));
        D.addNeighbor(new Edge(F, 2));
        E.addNeighbor(new Edge(G, 2));
        F.addNeighbor(new Edge(G, 1));

        // Find path using A* algorithm
        List<Node> path = aStar(S, G);
        for (Node node : path) {
            System.out.println(node.name);
        }
    }
}
