import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class SuspectPage extends JFrame {
    private JPanel mainPane = new JPanel();
    private JButton reuturnToSearchScreenBtn;

    // Panel 1
    private JPanel panel1 = new JPanel();
    private JTextArea suspectPhones;
    private JTextField suspectName, suspectCodeName;

    // Panel 2
    private JPanel panel2 = new JPanel();
    private JTextField suspectSearchNumber;
    private JTextArea suspectMessages;
    private JButton smsFindBtn;

    // Panel 3
    private JPanel panel3 = new JPanel();
    private JTextArea possiblePartners;
    private JLabel partnersLabel;

    // Panel 4
    private JPanel panel4 = new JPanel();
    private JTextArea suggestedPartners;
    private JLabel suggestedPartnersLabel;

    // Panel 5
    private JPanel panel5 = new JPanel();
    private JTextArea suspectsFromTheSameCountry;

    public SuspectPage() {
        // Panel 1
        suspectName = new JTextField("");
        suspectName.setPreferredSize(new Dimension(110, 24));

        suspectCodeName = new JTextField("");
        suspectCodeName.setPreferredSize(new Dimension(110, 24));

        suspectPhones = new JTextArea(3, 14);
        suspectPhones.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel1.add(suspectName);
        panel1.add(suspectCodeName);
        panel1.add(suspectPhones);
        panel1.setBorder(BorderFactory.createLineBorder(Color.black));

        // Panel 2
        suspectSearchNumber = new JTextField("");
        suspectSearchNumber.setPreferredSize(new Dimension(135, 24));

        suspectMessages = new JTextArea(12, 22);
        suspectMessages.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        smsFindBtn = new JButton("Find SMS");

        panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());
        panel2.add(suspectSearchNumber);
        panel2.add(suspectMessages);
        panel2.add(smsFindBtn);
        // panel2.setSize(400, 350);
        panel2.setBorder(BorderFactory.createLineBorder(Color.black));

        // Panel 3
        possiblePartners = new JTextArea(15, 22);
        partnersLabel = new JLabel("Partners");
        possiblePartners.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panel3 = new JPanel();
        panel3.setLayout(new FlowLayout());
        panel3.add(partnersLabel);
        panel3.add(possiblePartners);
        panel3.setBorder(BorderFactory.createLineBorder(Color.black));

        // Panel 4
        suggestedPartnersLabel = new JLabel("Suggested Partners ---->");

        suggestedPartners = new JTextArea(6, 20);
        suggestedPartners.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panel4 = new JPanel();
        panel4.setLayout(new FlowLayout());
        panel4.add(suggestedPartnersLabel);
        panel4.add(suggestedPartners);
        panel4.setBorder(BorderFactory.createLineBorder(Color.black));

        // Panel 5
        suspectsFromTheSameCountry = new JTextArea(6, 32);
        suspectsFromTheSameCountry.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panel5 = new JPanel();
        panel5.setLayout(new FlowLayout());
        panel5.add(suspectsFromTheSameCountry);
        panel5.setBorder(BorderFactory.createLineBorder(Color.black));

        // MAIN PANE
        reuturnToSearchScreenBtn = new JButton("Return to Search Screen");
        mainPane = new JPanel();
        this.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));

        mainPane.add(panel1);
        mainPane.add(panel2);
        mainPane.add(panel3);
        mainPane.add(panel4);
        mainPane.add(panel5);
        mainPane.add(reuturnToSearchScreenBtn);

        this.setContentPane(mainPane);

        this.setVisible(true);
        this.setSize(550, 830);
        this.setLocationRelativeTo(null);
        this.setTitle("Suspect Page");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
    }
}