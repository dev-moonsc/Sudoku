import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GetLevelFrame extends DefaultFrame {

    JButton btnEasy;
    JButton btnNormal;
    JButton btnHard;

    public GetLevelFrame() {
        super("Select Level");

        setLayout(new FlowLayout());
        setSize(200, 200);

        btnEasy = new JButton("Easy");
        btnEasy.setActionCommand("Easy");
        btnEasy.setPreferredSize(new Dimension(150, 50));
        add(btnEasy);

        btnNormal = new JButton("Normal");
        btnNormal.setActionCommand("Normal");
        btnNormal.setPreferredSize(new Dimension(150, 50));
        add(btnNormal);

        btnHard = new JButton("Hard");
        btnHard.setActionCommand("Hard");
        btnHard.setPreferredSize(new Dimension(150, 50));
        add(btnHard);
    }

    public void setActionListener(ActionListener listener) {
        btnEasy.addActionListener(listener);
        btnNormal.addActionListener(listener);
        btnHard.addActionListener(listener);
    }
}
