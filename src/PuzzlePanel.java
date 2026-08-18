import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PuzzlePanel extends JPanel {
    private final PuzzleButton[][] puzzleButtons;

    public PuzzlePanel() {
        super(new GridLayout(9, 9));

        puzzleButtons = new PuzzleButton[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                puzzleButtons[i][j] = new PuzzleButton();
                add(puzzleButtons[i][j]);
            }
        }

        setBorder(new EmptyBorder(10, 10, 10, 5));
    }

    public void initialize(int[][] quizBoard) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                puzzleButtons[i][j].initialize(i, j, quizBoard[i][j]);
            }
        }
    }

    public PuzzleButton[][] getPuzzleButtons() {
        return puzzleButtons;
    }
}
