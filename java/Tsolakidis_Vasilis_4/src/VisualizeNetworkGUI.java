import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.event.GraphEvent.Edge;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.samples.SimpleGraphDraw;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.EdgeArrowRenderingSupport;

public class VisualizeNetworkGUI<V, E> extends JFrame {

    private Registry registry;

    public VisualizeNetworkGUI(Registry registry) {

        this.registry = registry;

        Graph<Integer, String> g = new DirectedSparseGraph<>();

        // /*
        // * g.addVertex("Vertex1");
        // * g.addVertex("Vertex2");
        // * g.addVertex("Vertex3");
        // * g.addEdge("Edge1", "Vertex1", "Vertex2");
        // * g.addEdge("Edge2", "Vertex1", "Vertex3");
        // * g.addEdge("Edge3", "Vertex3", "Vertex1");
        // */

        // for (Suspect suspect : registry.getSuspectList()) {
        // g.addVertex(suspect.getCodeName());
        // // ToStringLabeller label = new ToStringLabeller();
        // }

        // int number = 1;
        // // for (Suspect suspect : registry.getSuspectList()) {
        // // for (Suspect partern : suspect.getConnectedList()) {
        // // String edge = "Edge" + Integer.toString(number);
        // // g.addEdge("Edge1", suspect.getCodeName(), partern.getCodeName());
        // // }
        // // number++;
        // // }

        // g.addEdge("Edge1", registry.getSuspectList().get(0).getCodeName(),
        // registry.getSuspectList().get(0).getConnectedList().get(0).getCodeName());
        // // g.addEdge("Edge2", "Vertex1", "Vertex3");
        // // g.addEdge("Edge3", "Vertex3", "Vertex1");

        // VisualizationImageServer<V, E> vs;

        // vs = new VisualizationImageServer<V, E>(new KKLayout(g), new Dimension(500,
        // 500));

        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);

        g.addEdge("Edge1", 1, 2);
        g.addEdge("Edge2", 1, 3);

        Layout<Integer, String> layout = new KKLayout<Integer, String>(g);
        layout.setSize(new Dimension(350, 350));
        BasicVisualizationServer<Integer, String> vv = new BasicVisualizationServer<Integer, String>(layout);
        vv.setPreferredSize(new Dimension(350, 350));

        getContentPane().add(vv);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
        this.setTitle("Suspect NewWork");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
