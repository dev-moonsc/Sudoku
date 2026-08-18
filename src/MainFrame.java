import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * 게임의 시작 화면을 나타내는 프레임
 */
public class MainFrame extends DefaultFrame {
    JButton btnStart;
    JButton btnRanking;

    public MainFrame() {
        super("Sudoku");

        setLayout(new FlowLayout());
        setSize(200, 200);

        btnStart = new JButton("New Game");
        btnStart.setPreferredSize(new Dimension(150, 70));
        btnStart.setActionCommand("New Game");
        add(btnStart);

        btnRanking = new JButton("Ranking");
        btnRanking.setPreferredSize(new Dimension(150, 70));
        btnRanking.setActionCommand("Ranking");
        add(btnRanking);
    }

    public void setActionListener(ActionListener listener) {
        btnStart.addActionListener(listener);
        btnRanking.addActionListener(listener);
    }
}
