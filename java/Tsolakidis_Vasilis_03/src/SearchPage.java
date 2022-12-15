import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchPage extends JFrame {

    private JTextField inputSearchField = new JTextField();
    private JPanel panel = new JPanel();
    private JButton find_btn;
    private String searchInput = "";
    private Registry registry;
    private SearchPage thisSearchPage;

    public SearchPage(Registry registry) {
        thisSearchPage = this;
        this.registry = registry;
        inputSearchField = new JTextField("Please enter suspect's name");
        find_btn = new JButton("Find");

        panel.add(inputSearchField);
        panel.add(find_btn);

        this.setContentPane(panel);

        ButtonListener listener = new ButtonListener();
        find_btn.addActionListener(listener);

        this.setVisible(true);
        this.setSize(300, 150);
        this.setLocationRelativeTo(null);
        this.setTitle("Find Suspect");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void changeInputField() {
        this.inputSearchField.setText("Please enter suspect's name");
    }

    class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == find_btn) {

                boolean find = false;
                searchInput = inputSearchField.getText();
                for (Suspect sp : registry.getSuspectList()) {
                    if (sp.getName().equals(searchInput)) {
                        find = true;
                    }
                }
                if (!find) {
                    JOptionPane.showOptionDialog(null, "Suspect " + searchInput + " Not Found", "Message",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "OK" },
                            null);
                } else {
                    new SuspectPage(registry, thisSearchPage, inputSearchField.getText());
                    setVisible(false);
                }
            }
        }
    }
}