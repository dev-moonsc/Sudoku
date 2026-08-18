import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameFrame extends DefaultFrame {
    private final PuzzlePanel puzzlePanel;
    private final PuzzleButton[][] puzzleButtons;
    private final JButton[] numButtons;

    public GameFrame() {
        super("Sudoku");

        setLayout(new BorderLayout());
        setSize(700, 550);

        puzzlePanel = new PuzzlePanel();
        NumPanel numPanel = new NumPanel();

        add(puzzlePanel, BorderLayout.CENTER);
        add(numPanel, BorderLayout.EAST);

        puzzleButtons = puzzlePanel.getPuzzleButtons();
        numButtons = numPanel.getNumButtons();
    }

    public void initialize(int[][] quizBoard) {
        puzzlePanel.initialize(quizBoard);
        setVisible(true);
    }

    public void updatePuzzleButton(int row, int col, int value) {
        puzzleButtons[row][col].update(value);
    }

    public void paintPuzzleButton(boolean SELECT, int row, int col, int area) {
        if (SELECT) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    PuzzleButton button = puzzleButtons[i][j];
                    if (i == row || j == col || button.getArea() == area)
                        button.setBackground(SudokuColor.CYAN);
                    else
                        button.unpaint();
                }
            }
            puzzleButtons[row][col].setBackground(SudokuColor.BLUE);

        } else {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++)
                    puzzleButtons[i][j].unpaint();
            }
        }
    }

    public void setNumActionListener(ActionListener listener) {
        for (int i = 0; i < 10; i++) {
            numButtons[i].addActionListener(listener);
        }
    }

    public void setPuzzleActionListener(ActionListener listener) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                puzzleButtons[i][j].addActionListener(listener);
            }
        }
    }
}
