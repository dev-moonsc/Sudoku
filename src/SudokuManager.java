public class SudokuManager {
    public static void main(String[] args) {
        SudokuController controller = new SudokuController("./record.csv");

        controller.runGame();
    }
}
