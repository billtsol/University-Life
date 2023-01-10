import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.shortestpath.DistanceStatistics;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.Color;

public class VisualizeNetworkGUI<V, E> extends JFrame {

    public VisualizeNetworkGUI(Registry registry) {

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

        Layout<String, String> layout = new CircleLayout<String, String>(g);
        layout.setSize(new Dimension(300, 300));

        BasicVisualizationServer<String, String> vv = new BasicVisualizationServer<String, String>(layout);
        vv.setPreferredSize(new Dimension(450, 450));

        vv.setBackground(Color.CYAN);

        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>());

        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(vv);

        panel.add(new JTextField("Diameter = " + DistanceStatistics.diameter(g)));

        this.setContentPane(panel);
        this.setSize(450, 450);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
        this.setTitle("Suspect NewWork");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
