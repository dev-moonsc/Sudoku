import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BoardGenerator {

    // 보드 생성해서 리턴
    public int[][] makeBoard() { // 2차원 정수형 리스트 makeboard 메소드 생성
        List<List<Integer>> board = initializeBoard(); // initializeBoard 호출해서 초기 보드 생성한걸 board에 넣음
        board = transpose(shuffleRibbons(board)); // 보드 행을 섞고 전치함

        return ListToArray(board);
    }

    // 빈칸 개수 결정
    public int NumOfHoles(int level) {
        return switch (level) {
            case 1 -> 40;
            case 2 -> 50;
            default -> 60;
        };
    }

    public int[][] makeQuizBoard(int[][] board, int holes) {
        int[][] newBoard = copyBoard(board);

        while (holes > 0) {
            Random random = new Random();
            int row = random.nextInt(9);
            int col = random.nextInt(9);

            if (newBoard[row][col] == 0)
                continue;
            newBoard[row][col] = 0;
            holes -= 1;
        }

        return newBoard;
    }

    // 초기 보드 생성
    private List<List<Integer>> initializeBoard() { // 보드 초기화해서 만들기
        List<Integer> row0 = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)); //row0은 1~9로 구성되어 있음.
        Collections.shuffle(row0); // row0 섞기

        List<Integer> row1 = new ArrayList<>(row0.subList(6, 9)); //row1은 row0의 6~8 index로 구성되어 있는 하위리스트
        row1.addAll(row0.subList(0, 3));  //row1에 row0의 0~2 index로 구성된 하위리스트를 뒤에 추가함.
        row1.addAll(row0.subList(3, 6));  //row1에 row0의 3~5 index로 구성된 하위리스트를 뒤에 추가함.

        List<Integer> row2 = new ArrayList<>(row0.subList(3, 6));
        row2.addAll(row0.subList(6, 9));
        row2.addAll(row0.subList(0, 3));

        List<Integer> row3 = new ArrayList<>(List.of(row0.get(2), row0.get(0), row0.get(1), row0.get(5),
                row0.get(3), row0.get(4), row0.get(8), row0.get(6), row0.get(7)));   // 4행은 새로운 3x3 정사각형 구역이므로 row0의 인덱스를 재구성하여 생성

        List<Integer> row4 = new ArrayList<>(row3.subList(6, 9));
        row4.addAll(row3.subList(0, 3));
        row4.addAll(row3.subList(3, 6));

        List<Integer> row5 = new ArrayList<>(row3.subList(3, 6));
        row5.addAll(row3.subList(6, 9));
        row5.addAll(row3.subList(0, 3));

        List<Integer> row6 = new ArrayList<>(List.of(row0.get(1), row0.get(2), row0.get(0), row0.get(4),
                row0.get(5), row0.get(3), row0.get(7), row0.get(8), row0.get(6)));

        List<Integer> row7 = new ArrayList<>(row6.subList(6, 9));
        row7.addAll(row6.subList(0, 3));
        row7.addAll(row6.subList(3, 6));

        List<Integer> row8 = new ArrayList<>(row6.subList(3, 6));
        row8.addAll(row6.subList(6, 9));
        row8.addAll(row6.subList(0, 3));

        return new ArrayList<>(List.of(row0, row1, row2, row3, row4, row5, row6, row7, row8));
    }

    // 보드 섞기
    private List<List<Integer>> shuffleRibbons(List<List<Integer>> board) { // 행을 세 구역으로 나누어 섞기 . 행만 섞어도 다양한 초기상태를 얻을 수 있어 열은 굳이 섞지 않아도됨.
        List<List<Integer>> top = board.subList(0, 3);
        List<List<Integer>> mid = board.subList(3, 6);
        List<List<Integer>> bottom = board.subList(6, 9);

        Collections.shuffle(top);
        Collections.shuffle(mid);
        Collections.shuffle(bottom);

        List<List<Integer>> shuffledBoard = new ArrayList<>();
        shuffledBoard.addAll(top);
        shuffledBoard.addAll(mid);
        shuffledBoard.addAll(bottom);

        return shuffledBoard;
    }

    // 보드 전치
    private List<List<Integer>> transpose(List<List<Integer>> board) {
        List<List<Integer>> boardTranspose = new ArrayList<>();

        int size = board.size();
        for (int i = 0; i < size; i++) {
            boardTranspose.add(new ArrayList<>());
        }

        for (List<Integer> row : board) { //board에 있는 각 행을 순회하여 그 행을 row에 할당
            for (int i = 0; i < size; i++) {
                boardTranspose.get(i).add(row.get(i));
            }
        }

        return boardTranspose;
    }

    // List형태로 된 보드를 배열 형태로 리턴
    private int[][] ListToArray(List<List<Integer>> board) {
        int numRows = board.size();
        int[][] array = new int[numRows][];

        for (int i = 0; i < numRows; i++) {
            List<Integer> row = board.get(i);
            int numCols = row.size();
            array[i] = new int[numCols];

            for (int j = 0; j < numCols; j++) {
                array[i][j] = row.get(j);
            }
        }

        return array;
    }

    private int[][] copyBoard(int[][] board) {
        int[][] newBoard = new int[9][9];
        for (int i = 0; i < 9; i++)
            System.arraycopy(board[i], 0, newBoard[i], 0, 9);

        return newBoard;
    }
}
