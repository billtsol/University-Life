import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI_Board extends JFrame implements ActionListener {
    private JFrame frame = new JFrame();
    private JButton btn = new JButton();

    /**
     * 
     */
    public GUI_Board() {
        frame.setTitle("CHESS");
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        frame.setLayout(new GridLayout(8, 8, 1, 1));



        frame.setVisible(true);
        frame.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        this.btn.setText("NEw");

    }

}
