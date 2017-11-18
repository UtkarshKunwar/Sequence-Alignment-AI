package alviz.algorithm;

import alviz.base.algorithm.Algorithm;
import alviz.base.graph.BaseGraph;
import alviz.graph.Graph;
import alviz.graph.WeightedGraph;
import alviz.util.Pair;

import java.util.*;

/**
 *
 * @author baskaran
 */
public class BestFS extends Algorithm {
    private BaseGraph graph;

    private LinkedList<Pair<BaseGraph.Node,BaseGraph.Node>> open;
    private LinkedList<Pair<BaseGraph.Node,BaseGraph.Node>> closed;

    private HashMap<BaseGraph.Node, Float> distances;

    private boolean done=false;

    public BestFS(BaseGraph graph) {
        super();
        this.graph = graph;
        this.open = null;
        this.closed = null;
    }

    public void execute() throws Exception {
        open = new LinkedList<Pair<BaseGraph.Node,BaseGraph.Node>>();
        closed = new LinkedList<Pair<BaseGraph.Node,BaseGraph.Node>>();

        // Store distances to nodes in a hashmap
        distances = new HashMap<>();
        BaseGraph.Node startingNode = graph.getStartNode();
        for (int i = 0; i < graph.getNodeCount(); i++) {
            BaseGraph.Node currentNode = graph.getNode(i);
            if (currentNode == startingNode) {
                distances.put(currentNode, 0.0f);
            } else {
                distances.put(currentNode, Float.POSITIVE_INFINITY);
            }
        }

        openNode(graph.getStartNode(), null);
        BestFS();
        generatePath();
        setStateEnded();
        show();
    }

    private void openNode(BaseGraph.Node n, BaseGraph.Node p) {
        if (n != null) {
            graph.openNode(n, p);
            open.offer(new Pair(n, p));
        }
    }

    private void closeNode(Pair<BaseGraph.Node,BaseGraph.Node> pn) {
        BaseGraph.Node n = pn.fst;
        BaseGraph.Node p = pn.snd;
        if (n != null) {
            graph.closeNode(n);
            closed.add(pn);

            if (p != null) {
                distances.replace(n, distances.get(p) + new Float(graph.getEdge(p, n).cost));
            }
        }
    }

    private void BestFS() throws Exception {
        if (!open.isEmpty()) {
            Pair<BaseGraph.Node, BaseGraph.Node> bestPair = null;
            float bestPairCost = Float.POSITIVE_INFINITY;
            for (Pair<BaseGraph.Node, BaseGraph.Node> pn : open) {
                float curCost = 0;
                if (pn.snd != null) {
                    curCost += distances.get(pn.snd);
                }

                BaseGraph.Edge edge = graph.getEdge(pn.snd, pn.fst);
                if (edge != null) {
                    curCost += (float) edge.cost;
                }

                if (curCost < bestPairCost) {
                    bestPair = pn;
                    bestPairCost = curCost;
                }
            }
            open.remove(bestPair);
            BaseGraph.Node h = bestPair.fst;
            if (graph.goalTest(h)){
                closeNode(bestPair);
                h.setGoal();
                done = true;
            }
            else {
                List<BaseGraph.Node> neighbours = graph.moveGen(h);
                if (neighbours != null) {
                    for (BaseGraph.Node n : neighbours) {
                        if (n.isCandidate() && n.x >= h.x && n.y >= h.y) {
                            openNode(n, h);
                        }
                    }
                }
                closeNode(bestPair);
            }
            show();
            if (!done) {
                BestFS();
            }
        }
    }

    private List<BaseGraph.Node> generatePath() {
        List<BaseGraph.Node> path=null;
        if (closed == null) return path;
        if (closed.isEmpty()) return path;

        Iterator<Pair<BaseGraph.Node,BaseGraph.Node>> closedList = ((LinkedList)closed).descendingIterator();
        if (!closedList.hasNext()) return path;

        Pair<BaseGraph.Node,BaseGraph.Node> pair = closedList.next();
        if (!pair.fst.isGoal()) {
            //System.out.printf("generatePath: pair.fst %s\n", pair.fst.getState().toString());
            return path;
        }

        path = new LinkedList<BaseGraph.Node>();
        // add to path
        pair.fst.setPath();
        path.add(pair.fst);
        while (pair.snd != null) {

            // add to path
            pair.snd.setPath();
            path.add(pair.snd);

            // add edge to path
            BaseGraph.Edge e = graph.getEdge(pair.fst, pair.snd);
            if (e != null) {
                e.setPath();
            }

            // search for predecessor pair
            BaseGraph.Node n = pair.snd;
            while (closedList.hasNext()) {
                pair = closedList.next();
                if (pair.fst == n) break;
            }
        }

        return path;
    }

}
