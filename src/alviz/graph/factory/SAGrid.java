package alviz.graph.factory;

import alviz.base.graph.BaseGraph;
import alviz.graph.*;

import alviz.main.ALVISDesktop;
import alviz.main.ALVISDesktop.*;

public class SAGrid {

    private SAGrid() {}

    private static int getIndex(String s, int index) {
        switch(s.charAt(index)) {
            case 'A':
                return 0;
            case 'T':
                return 1;
            case 'G':
                return 2;
            default:
                return 3;
        }
    }

    private static float getCost(String org, String mod, int ix, int iy) {
        /*    A T G C
         *  A 0 2 4 2
         *  T 2 0 3 2
         *  G 4 3 0 2
         *  C 2 2 2 0
         */
        float costMatrix[][] = {
                {0.0f, 2.0f, 4.0f, 2.0f},
                {2.0f, 0.0f, 3.0f, 2.0f},
                {4.0f, 3.0f, 0.0f, 2.0f},
                {2.0f, 2.0f, 2.0f, 0.0f}
        };

        return costMatrix[getIndex(org, ix)][getIndex(mod, iy)];
    }

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

                float weight = getCost(ALVISDesktop.getSeqGen().getGenerated().getSeq(),
                        ALVISDesktop.getSeqGen().getModified().getSeq(),
                        ix, iy);

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
