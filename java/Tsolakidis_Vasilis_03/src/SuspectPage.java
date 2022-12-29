
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
// import javax.swing.border.Border;
// import java.awt.Color;
// import javax.swing.border.EtchedBorder;

/* Το τελευταίο αιώνα το διαδίκτυο έχει αλλάξει τον τρόπο που οι ανθρώπου αλληλεπιδρούν μεταξύ τους.
 * Βομβαρδιζόμαστε καθημερινά με έναν δυσθεώρητο αριθμό καινούργιων πληροφοριών, τις οποίες καλούμαστε να επεξεργαστούμε.
 * Βαθιά μέσα μας όμως συνοδευόμαστε από το σχετικό ένστικτο να απλοποιείς τα πράγματα μηδενίζοντας στην ουσία τους και εξαλείφοντας τα περιττά συστατικά.
 * Όπως είχε αναφέρει ο Steve Jobs << Η απλότητα είναι η ΥΠΕΡΤΑΤΗ σοφιστεία.
 * Και για να επιτευχθεί χρειάζεται πολλή σκληρή δουλειά, ώστε να κατανοήσουμε πραγματικά τις υποκείμενες προκλήσεις και να βρούμε κομψές λύσεις >>.
 * Αναλογιζόμενος τα λεγόμενα αυτής της ευμεγέθους προσωπικότητας πήρα το θάρρος και αντικατέστησα, τα τετελεσμένα,
 * δημιουργώντας μια άνευ προηγουμένου οπτική αναπαράσταση.
*/

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

        // Border loweredetched =
        // BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

        for (Suspect sp : registry.getSuspectList()) {
            if (sp.getName().equals(suspectInputName)) {
                this.suspect = sp;
            }
        }

        ButtonListener listener = new ButtonListener();

        // Panel 1
        suspectName = new JTextField(this.suspect.getName());
        suspectName.setPreferredSize(new Dimension(110, 24));

        suspectCodeName = new JTextField(this.suspect.getCodeName());
        suspectCodeName.setPreferredSize(new Dimension(110, 24));

        suspectPhones = new JTextArea(3, 9);
        for (String phone : suspect.getPhoneList()) {
            suspectPhones.append(phone + "\n");
        }

        // suspectPhones.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());

        JScrollPane suspectPhonesScroll = new JScrollPane(suspectPhones, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel1.add(suspectName);
        panel1.add(suspectCodeName);
        panel1.add(suspectPhonesScroll);
        // panel1.setBorder(loweredetched);

        // END of Panel 1

        // Panel 2
        suspectSearchNumber = new JTextField("");
        suspectSearchNumber.setPreferredSize(new Dimension(107, 24));

        suspectMessages = new JTextArea(10, 20);
        // suspectMessages.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        smsFindBtn = new JButton("Find SMS");

        panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());

        JScrollPane suspectMessagesScroll = new JScrollPane(suspectMessages, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel2.add(suspectSearchNumber);
        panel2.add(suspectMessagesScroll);
        panel2.add(smsFindBtn);
        // panel2.setBorder(loweredetched);

        // Panel 3
        possiblePartners = new JTextArea(10, 20);
        for (Suspect sp : suspect.getConnectedList()) {
            possiblePartners.append(sp.getName() + ", " + sp.getCodeName() + "\n");
        }
        partnersLabel = new JLabel("Partners");
        // possiblePartners.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JScrollPane possiblePartnersScroll = new JScrollPane(possiblePartners, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel3 = new JPanel();
        panel3.setLayout(new FlowLayout());
        panel3.add(partnersLabel);
        panel3.add(possiblePartnersScroll);
        // panel3.setBorder(loweredetched);

        // END of Panel 3

        // Panel 4
        suggestedPartnersLabel = new JLabel("Suggested Partners ---->");

        suggestedPartners = new JTextArea(6, 18);
        for (Suspect sp : suspect.SuggestedPartners()) {
            suggestedPartners.append(sp.getName() + "\n");
        }
        // suggestedPartners.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JScrollPane suggestedPartnersScroll = new JScrollPane(suggestedPartners,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel4 = new JPanel();
        panel4.setLayout(new FlowLayout());
        panel4.add(suggestedPartnersLabel);
        panel4.add(suggestedPartnersScroll);
        // panel4.setBorder(loweredetched);

        // Panel 5
        suspectsFromTheSameCountry = new JTextArea(6, 32);
        suspectsFromTheSameCountry.append("Suspects coming from " + this.suspect.getCountry() + ": \n");
        for (Suspect sp : registry.getSuspectList()) {
            if (sp.getCountry().equals(this.suspect.getCountry())) {
                suspectsFromTheSameCountry.append(sp.getName() + "\n");
            }
        }
        // suspectsFromTheSameCountry.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JScrollPane suspectsFromTheSameCountryScroll = new JScrollPane(suspectsFromTheSameCountry,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel5 = new JPanel();
        panel5.setLayout(new FlowLayout());
        panel5.add(suspectsFromTheSameCountryScroll);
        // panel5.setBorder(loweredetched);

        // MAIN PANE
        reuturnToSearchScreenBtn = new JButton("Return to Search Screen");

        reuturnToSearchScreenBtn.addActionListener(listener);
        smsFindBtn.addActionListener(listener);

        mainPane = new JPanel();

        mainPane.add(panel1);
        mainPane.add(panel2);
        mainPane.add(panel3);
        mainPane.add(panel4);
        mainPane.add(panel5);
        mainPane.add(reuturnToSearchScreenBtn);

        this.setContentPane(mainPane);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setSize(525, 775);
        this.setResizable(false);
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