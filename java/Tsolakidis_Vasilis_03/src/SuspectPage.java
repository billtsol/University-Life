import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

// Java program to illustrate
// boolean addAll(Collection c)
import java.io.*;

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

    private Registry registry;
    private Suspect suspect;
    private SearchPage searchPage;

    public SuspectPage(Registry registry, SearchPage searchPage, String suspectInputName) {
        this.registry = registry;
        this.searchPage = searchPage;

        for (Suspect sp : registry.getSuspectList()) {
            if (sp.getName().equals(suspectInputName)) {
                this.suspect = sp;
            }
        }

        ButtonListener listener = new ButtonListener();

        // Panel 1
        // Soixeia toy ypotpoy

        suspectName = new JTextField(this.suspect.getName());
        suspectName.setPreferredSize(new Dimension(110, 24));

        suspectCodeName = new JTextField(this.suspect.getCodeName());
        suspectCodeName.setPreferredSize(new Dimension(110, 24));

        suspectPhones = new JTextArea(1, 14);
        for (String phone : suspect.getPhoneList()) {
            suspectPhones.append(phone + "\n");
        }
        suspectPhones.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel1.add(suspectName);
        panel1.add(suspectCodeName);
        panel1.add(suspectPhones);
        panel1.setBorder(BorderFactory.createLineBorder(Color.black));

        // END of Panel 1

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
        panel2.setBorder(BorderFactory.createLineBorder(Color.black));

        // Panel 3
        possiblePartners = new JTextArea(15, 22);
        for (Suspect sp : suspect.getConnectedList()) {
            possiblePartners.append(sp.getName() + ", " + sp.getCodeName() + "\n");
        }
        partnersLabel = new JLabel("Partners");
        possiblePartners.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panel3 = new JPanel();
        panel3.setLayout(new FlowLayout());
        panel3.add(partnersLabel);
        panel3.add(possiblePartners);
        panel3.setBorder(BorderFactory.createLineBorder(Color.black));

        // END of Panel 3

        // Panel 4
        suggestedPartnersLabel = new JLabel("Suggested Partners ---->");

        suggestedPartners = new JTextArea(6, 20);
        for (Suspect sp : suspect.SuggestedPartners()) {
            suggestedPartners.append(sp.getName() + "\n");
        }
        suggestedPartners.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panel4 = new JPanel();
        panel4.setLayout(new FlowLayout());
        panel4.add(suggestedPartnersLabel);
        panel4.add(suggestedPartners);
        panel4.setBorder(BorderFactory.createLineBorder(Color.black));

        // Panel 5
        suspectsFromTheSameCountry = new JTextArea(6, 32);
        suspectsFromTheSameCountry.append("Suspects coming from " + this.suspect.getCountry() + ": \n");
        for (Suspect sp : registry.getSuspectList()) {
            if (sp.getCountry().equals(this.suspect.getCountry())) {
                suspectsFromTheSameCountry.append(sp.getName() + "\n");
            }
        }
        suspectsFromTheSameCountry.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panel5 = new JPanel();
        panel5.setLayout(new FlowLayout());
        panel5.add(suspectsFromTheSameCountry);
        panel5.setBorder(BorderFactory.createLineBorder(Color.black));

        // MAIN PANE
        reuturnToSearchScreenBtn = new JButton("Return to Search Screen");

        reuturnToSearchScreenBtn.addActionListener(listener);
        smsFindBtn.addActionListener(listener);

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
            if (e.getSource() == reuturnToSearchScreenBtn) {
                searchPage.changeInputField();
                searchPage.setVisible(true);
                setVisible(false);
                dispose();
            }
            if (e.getSource() == smsFindBtn) {
                String phoneNumber1 = suspectSearchNumber.getText();
                boolean checkTheNumber = false;
                for (Suspect sp : registry.getSuspectList()) {
                    if (sp.getPhoneList().contains(phoneNumber1)) {
                        checkTheNumber = true;
                    }
                }
                if (checkTheNumber) {
                    ArrayList<SMS> allMessages = new ArrayList<SMS>();
                    for (String phone : suspect.getPhoneList()) {
                        allMessages.addAll(registry.getMessagesBetween(phoneNumber1, phone));
                    }
                    for (SMS sms : allMessages) {
                        suspectMessages.append(sms.getMessage() + "\n");
                    }

                } else {
                    suspectMessages.setText("Error! Phone number doesn't exist.");
                }

            }
        }
    }
}