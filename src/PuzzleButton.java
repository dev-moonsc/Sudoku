import javax.swing.*;
import javax.swing.border.LineBorder;

public class PuzzleButton extends JButton {
    private int row;
    private int col;
    private int area;
    private boolean TO_GUESS;

    // Ctor
    public PuzzleButton() {
        setOpaque(true); // 불투명 설정 (색 지정 위해서 필요)
        setFocusPainted(false); // 포커스 표시 제거
        setBorder(new LineBorder(SudokuColor.BLACK, 1)); // 보더 설정
    }

    public void initialize(int row, int col, int value) {
        this.row = row;
        this.col = col;

        // area 설정: 좌측 상단부터 우측 하단까지 1 ~ 9
        area = (row / 3) * 3 + (col / 3) + 1;

        // 할당된 value가 0이면 빈 칸, 즉 뚫려있는 칸이다. 이에 따라 버튼의 각종 속성을 설정.
        if (value == 0) {
            TO_GUESS = true;
            setText("");
            setBackground(SudokuColor.WHITE);
        } else {
            TO_GUESS = false;
            setText("" + value);
            setBackground(SudokuColor.GRAY);
        }
    }

    public void update(int value) {
        if (value == 0)
            setText("");
        else
            setText("" + value);
    }

    public void unpaint() {
        if (TO_GUESS)
            setBackground(SudokuColor.WHITE);
        else
            setBackground(SudokuColor.GRAY);
    }

    public int getArea() {
        return area;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isTO_GUESS() {
        return TO_GUESS;
    }
}
