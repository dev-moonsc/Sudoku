public class Game {
    private final SudokuController controller;
    private final int level;
    private final int[][] solutionBoard;
    private final int[][] quizBoard;
    private int numOfHoles;
    private boolean select; // PuzzleButton이 선택된 상태인가
    private int selectedRow;
    private int selectedCol;
    private int selectedArea;
    private final long startTime;

    public Game(SudokuController controller, int level) {
        this.controller = controller;
        this.level = level;
        BoardGenerator generator = new BoardGenerator();
        this.numOfHoles = generator.NumOfHoles(level);
        this.solutionBoard = generator.makeBoard();
        this.quizBoard = generator.makeQuizBoard(solutionBoard, numOfHoles);

        // 정답 보드 콘솔에 출력
        System.out.println("----- 정답 보드 -----");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(solutionBoard[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("-----------------");

        startTime = System.currentTimeMillis(); // 시작 시간 기록
    }

    public void selectPiece(boolean TO_GUESS, int row, int col, int area) {
        if (TO_GUESS) {
            if (select && selectedRow == row && selectedCol == col) {
                select = false;
            } else {
                selectedRow = row;
                selectedCol = col;
                selectedArea = area;
                select = true;
            }
        } else {
            select = false;
        }
    }

    public void updatePiece(int value) {
        int piece = quizBoard[selectedRow][selectedCol];

        if (piece != 0 && value == 0)
            numOfHoles++;
        else if (piece == 0 && value != 0)
            numOfHoles--;

        quizBoard[selectedRow][selectedCol] = value;

        checkFinish();
    }

    private void checkFinish() {
        if (numOfHoles == 0) {
            if (checkSolution()) {
                long endTime = System.currentTimeMillis();
                int elapsedTime = (int) ((endTime - startTime) / 1000);
                controller.finishGame(level, elapsedTime);
            } else {
                controller.continueGame();
            }
        }
    }

    private boolean checkSolution() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (quizBoard[i][j] != solutionBoard[i][j])
                    return false;
            }
        }

        return true;
    }

    public int[][] getQuizBoard() {
        return quizBoard;
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public int getSelectedCol() {
        return selectedCol;
    }

    public int getSelectedArea() {
        return selectedArea;
    }

    public boolean isSelected() {
        return select;
    }
}
