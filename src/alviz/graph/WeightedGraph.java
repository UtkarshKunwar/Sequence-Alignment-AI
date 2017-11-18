package alviz.graph;

import alviz.base.graph.BaseGraph;

public class WeightedGraph extends BaseGraph<WeightedGraph.Node, WeightedGraph.Edge> {

    public WeightedGraph() {
        super();
    }
    public WeightedGraph(int nodeCount) {
        super(nodeCount);
    }

    static public WeightedGraph createGraph() {
        return new WeightedGraph();
    }

    public WeightedGraph createGraph(int nodeCount) {
        return new WeightedGraph(nodeCount);
    }

    public Node createNode(int x, int y) {
        Node n = new Node(x, y);
        super.createNode(n);
        return n;
    }

    @Override
    public Edge createEdge(Node n1, Node n2) {
        return null;
    }

    public Edge createEdge(Node n1, Node n2, float weight) {
        Edge e = new Edge(n1, n2, weight);
        super.createEdge(e);
        return e;
    }

    public class Node extends BaseGraph<WeightedGraph.Node, WeightedGraph.Edge>.Node {

        public Node (int x, int y) {
            super(x,y);
        }
    }

    public class Edge extends BaseGraph<WeightedGraph.Node, WeightedGraph.Edge>.Edge {

        public Edge(Node n1, Node n2) {
            super(n1, n2);
        }

        public Edge(Node n1, Node n2, float weight) {
            super(n1, n2, weight);
        }
    }

}