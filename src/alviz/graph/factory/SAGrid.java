package alviz.graph.factory;

import alviz.base.graph.BaseGraph;
import alviz.graph.*;

public class SAGrid {

    private SAGrid() {}

    static public BaseGraph create(BaseGraph g, int cols, int rows, int width, int height, GraphShape shape) {

        BaseGraph.Node nodes[][] = new BaseGraph.Node[cols][rows];

        int dx = width/(cols+1);
        int dy = height/(rows+1);

        int x0 = (width  - dx * (cols-1))/2;
        int y0 = (height - dy * (rows-1))/2;

        for (int y=y0, iy=0, xsign=1; iy<rows; ++iy, xsign*=-1) {
            int x = x0;
            for (int ix=0,ysign=1; ix<cols; ++ix, ysign*=-1) {
                nodes[ix][iy] = g.createNode(x, y);
                x += dx;
            }
            y += dy;
        }

        WeightedGraph wg = (WeightedGraph) g;
        for (int iy=0, xsign=1; iy<rows; ++iy, xsign *=-1) {
            for (int ix=0; ix<cols; ++ix) {

                // TODO Add weight matrix for the sequences.

                float weight = 1.0f;
                if (ix < (cols-1)) {
                    wg.createEdge((WeightedGraph.Node) nodes[ix][iy], (WeightedGraph.Node) nodes[ix+1][iy], weight);
                }
                if (iy < (rows-1)) {
                    wg.createEdge((WeightedGraph.Node) nodes[ix][iy], (WeightedGraph.Node) nodes[ix][iy+1], weight);
                }
                if (ix < (cols - 1) && iy < (rows - 1)) {
                    wg.createEdge((WeightedGraph.Node) nodes[ix][iy], (WeightedGraph.Node) nodes[ix+1][iy+1], weight);
                }
            }
        }

        return wg;
    }
}
