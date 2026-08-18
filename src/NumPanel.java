import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NumPanel extends JPanel {
    private final JButton[] numButtons = new JButton[10];

    public NumPanel() {
        super(new GridLayout(5, 2));

        for (int i = 9; i >= 0; i--) {
            JButton button = new JButton("" + i);
            button.setActionCommand("" + i);
            add(button);
            numButtons[i] = button;
        }
        numButtons[0].setText("CLEAR");

        setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    public JButton[] getNumButtons() {
        return numButtons;
    }
}
