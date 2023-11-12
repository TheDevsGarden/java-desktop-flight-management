import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class SetFrame extends JFrame implements ActionListener {

    JComboBox<String> comboBox;
    JButton b;
    String compagnieChoisie;

    SetFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setTitle("Sélectionner une Compagnie");
        ArrayList<String> cie = new ArrayList<String>();
        cie.add("airCanada");
        cie.add("airRelax");

        comboBox = new JComboBox<String>();
        for (String company : cie) {
            comboBox.addItem(company);
        }

        b = new JButton("Sélectionner");
        b.addActionListener(this);

        this.add(comboBox);
        this.add(b);
        this.setSize(350, 75);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b) {
            compagnieChoisie = (String) comboBox.getSelectedItem();
            this.dispose();
        }
    }

    public String getCieChoisie() {
        return compagnieChoisie;
    }

}
