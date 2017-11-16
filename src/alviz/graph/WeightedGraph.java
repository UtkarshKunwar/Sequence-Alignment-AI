package alviz.graph;

import alviz.base.graph.BaseGraph;

public class WeightedGraph extends Graph {
    public WeightedGraph() {
        super();
    }
    public WeightedGraph(int nodeCount) {
        super(nodeCount);
    }

    static public WeightedGraph creategraph() {
        return new WeightedGraph();
    }
    public Graph createGraph(int nodeCount) {
        return new Graph(nodeCount);
    }

    public Graph.Node createNode(int x, int y) {
        Graph.Node n = new Graph.Node(x, y);
        super.createNode(n);
        return n;
    }

    public Edge createEdge(Node n1, Node n2, float weight) {
        Edge e = new Edge(n1, n2, weight);
        super.createEdge(e);
        return e;
    }

    public class Node extends Graph.Node {

        public Node (int x, int y) {
            super(x,y);
        }
    }

    public class Edge extends Graph.Edge {

        float weight;

        public Edge(Node n1, Node n2, float weight) {
            super(n1, n2);
            this.weight = weight;
        }

        public float getWeight() {
            return weight;
        }

        public void setWeight(float weight) {
            this.weight = weight;
        }
    }
}
