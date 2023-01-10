import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.event.GraphEvent.Edge;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.samples.SimpleGraphDraw;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.EdgeArrowRenderingSupport;
import edu.uci.ics.jung.visualization.renderers.VertexLabelRenderer;

public class VisualizeNetworkGUI<V, E> extends JFrame {

    private Registry registry;

    public VisualizeNetworkGUI(Registry registry) {

        this.registry = registry;

        Graph<String, String> g = new SparseGraph<>();

        for (Suspect suspect : registry.getSuspectList()) {
            g.addVertex(suspect.getCodeName());
        }

        int counter = 0;
        for (Suspect suspect : registry.getSuspectList()) {
            for (Suspect partner : suspect.getConnectedList()) {
                g.addEdge("Edge" + Integer.toString(counter), suspect.getCodeName(), partner.getCodeName());
                counter++;
            }
        }

        // Layout<String, String> layout = new KKLayout<String, String>(g);
        Layout<String, String> layout = new FRLayout<String, String>(g);
        layout.setSize(new Dimension(350, 350));

        BasicVisualizationServer<String, String> vv = new BasicVisualizationServer<String, String>(layout);
        vv.setPreferredSize(new Dimension(550, 550));

        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>());

        getContentPane().add(vv);
        this.setSize(550, 550);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
        this.setTitle("Suspect NewWork");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
