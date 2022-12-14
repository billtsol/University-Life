import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class SuspectPage extends JFrame {
    private JPanel panel = new JPanel();
    private JPanel fieldPanel = new JPanel();

    private JTextField suspectName, suspectCodeName;
    private JButton findSMS;

    public SuspectPage() {
        panel.setLayout(new BorderLayout());

        fieldPanel.setLayout(new GridLayout(3, 2));

        suspectName = new JTextField("Code");
        suspectCodeName = new JTextField("Destination");

        findSMS = new JButton("Create Bulk");

        fieldPanel.add(suspectName);
        fieldPanel.add(suspectCodeName);
        fieldPanel.add(findSMS);

        panel.add(fieldPanel, BorderLayout.CENTER);

        this.setContentPane(panel);

        ButtonListener listener = new ButtonListener();
        findSMS.addActionListener(listener);

        setTitle("JPANEL CREATION");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        // setting the bounds for the JFrame
        setBounds(100, 100, 645, 470);
        Border br = BorderFactory.createLineBorder(Color.BLACK);
        Container c = getContentPane();

        // Creating a JPanel for the JFrame
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        // setting the panel layout as null
        panel.setLayout(null);
        panel2.setLayout(null);
        panel3.setLayout(null);
        panel4.setLayout(null);
        // adding a label element to the panel
        JLabel label = new JLabel("Panel 1");
        JLabel label2 = new JLabel("Panel 2");
        JLabel label3 = new JLabel("Panel 3");
        JLabel label4 = new JLabel("Panel 4");

        label.setBounds(120, 50, 200, 50);
        label2.setBounds(120, 50, 200, 50);
        label3.setBounds(120, 50, 200, 50);
        label4.setBounds(120, 50, 200, 50);
        panel.add(label);
        panel2.add(label2);
        panel3.add(label3);
        panel4.add(label4);
        // changing the background color of the panel to yellow
        // Panel 1
        panel.setBackground(Color.YELLOW);
        panel.setBounds(10, 10, 300, 200);
        // Panel 2
        panel2.setBackground(Color.RED);
        panel2.setBounds(320, 10, 300, 200);
        // Panel 3
        panel3.setBackground(Color.GREEN);
        panel3.setBounds(10, 220, 300, 200);
        // Panel 4
        panel4.setBackground(Color.CYAN);
        panel4.setBounds(320, 220, 300, 200);

        // Panel border
        panel.setBorder(br);
        panel2.setBorder(br);
        panel3.setBorder(br);
        panel4.setBorder(br);

        // adding the panel to the Container of the JFrame
        c.add(panel);
        c.add(panel2);
        c.add(panel3);
        c.add(panel4);

        this.setVisible(true);
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        this.setTitle("Suspect Page");
        this.setLocationByPlatform(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}