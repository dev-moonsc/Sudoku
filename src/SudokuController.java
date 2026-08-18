import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class SudokuController {
    private Game game;
    private final Ranking ranking;
    private final MainFrame mainFrame;
    private final RankingFrame rankingFrame;
    private final GetLevelFrame getLevelFrame;
    private final GameFrame gameFrame;
    private final DefaultListModel<GameRecord> easyModel;
    private final DefaultListModel<GameRecord> normalModel;
    private final DefaultListModel<GameRecord> hardModel;

    // Ctor
    public SudokuController(String PATH) {
        ranking = new Ranking(PATH);
        mainFrame = new MainFrame();
        rankingFrame = new RankingFrame();
        getLevelFrame = new GetLevelFrame();
        gameFrame = new GameFrame();

        // Ranking 정보를 저장할 ListModel 생성
        easyModel = new DefaultListModel<>();
        normalModel = new DefaultListModel<>();
        hardModel = new DefaultListModel<>();

        // 각 프레임 ActionListener
        mainFrame.setActionListener(new MainBtnListener());
        getLevelFrame.setActionListener(new LevelBtnListener(this));
        gameFrame.setNumActionListener(new NumBtnListener());
        gameFrame.setPuzzleActionListener(new PuzzleBtnListener());

        // 각 프레임 WindowListener
        WindowListener windowListener = new WindowListener();
        mainFrame.addWindowListener(windowListener);
        getLevelFrame.addWindowListener(windowListener);
        rankingFrame.addWindowListener(windowListener);
        gameFrame.addWindowListener(windowListener);
    }

    // 게임 실행 함수
    public void runGame() {
        mainFrame.setVisible(true);
        updateAllModels();
        rankingFrame.initialize(easyModel, normalModel, hardModel);
    }

    private void updateAllModels() {
        for (int i = 1; i <= 3; i++) {
            updateListModel(i, ranking.getRanking(i));
        }
    }

    private void updateListModel(int level, List<GameRecord> recordList) {
        if (level == 1) {
            easyModel.clear();

            for (GameRecord record : recordList) {
                easyModel.addElement(record);
            }
        } else if (level == 2) {
            normalModel.clear();

            for (GameRecord record : recordList) {
                normalModel.addElement(record);
            }
        } else {
            hardModel.clear();

            for (GameRecord record : recordList) {
                hardModel.addElement(record);
            }
        }
    }

    // 빈칸이 다 채워졌으나 틀린 칸이 존재
    public void continueGame() {
        JOptionPane.showMessageDialog(null, "잘못 입력된 칸이 있습니다. 다시 풀어보세요.", "오답", JOptionPane.PLAIN_MESSAGE);
    }

    // 빈칸이 다 채워지고 정답 보드와 일치
    public void finishGame(int level, int elapsedTime) {
        String name = (String) JOptionPane.showInputDialog(null, "소요 시간 : " + elapsedTime + "초, 이름을 입력하세요", "랭킹", JOptionPane.PLAIN_MESSAGE, null, null, "익명");

        if (name != null) {
            ranking.updateRecords(level, name, elapsedTime);
            updateListModel(level, ranking.getRanking(level));
        }

        mainFrame.setVisible(true);
        gameFrame.dispose();
    }

    // 각 프레임 ActionListener 및 WindowListener 정의
    // MainFrame 버튼 리스너
    class MainBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("New Game")) {
                getLevelFrame.setVisible(true);
            } else {
                rankingFrame.setVisible(true);
            }

            mainFrame.setVisible(false);
        }
    }

    // GetLevelFrame 버튼 리스너
    class LevelBtnListener implements ActionListener {
        private final SudokuController controller;

        public LevelBtnListener(SudokuController controller) {
            this.controller = controller;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Easy")) {
                game = new Game(controller, 1);
            } else if (command.equals("Normal")) {
                game = new Game(controller, 2);
            } else {
                game = new Game(controller, 3);
            }

            gameFrame.initialize(game.getQuizBoard());
            getLevelFrame.setVisible(false);
        }
    }

    // GameFrame Num버튼 리스너
    class NumBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gameFrame.setEnabled(false); // 비활성화

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    // 비동기 작업 수행
                    if (game.isSelected()) {
                        int value = Integer.parseInt(e.getActionCommand());
                        int selectedRow = game.getSelectedRow();
                        int selectedCol = game.getSelectedCol();
                        gameFrame.updatePuzzleButton(selectedRow, selectedCol, value);
                        game.updatePiece(value);
                    }

                    return null;
                }

                @Override
                protected void done() {
                    gameFrame.setEnabled(true); // 작업 완료 후 다시 활성화
                }
            };

            worker.execute(); // SwingWorker 실행
        }
    }

    // GameFrame Puzzle버튼 리스너
    class PuzzleBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gameFrame.setEnabled(false); // 비활성화

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    // 비동기 작업 수행
                    if (e.getSource() instanceof PuzzleButton button) {
                        boolean TO_GUESS = button.isTO_GUESS();
                        int row = button.getRow();
                        int col = button.getCol();
                        int area = button.getArea();

                        game.selectPiece(TO_GUESS, row, col, area);

                        boolean SELECT = game.isSelected();
                        int selectedRow = game.getSelectedRow();
                        int selectedCol = game.getSelectedCol();
                        int selectedArea = game.getSelectedArea();

                        gameFrame.paintPuzzleButton(SELECT, selectedRow, selectedCol, selectedArea);
                    }

                    return null;
                }

                @Override
                protected void done() {
                    gameFrame.setEnabled(true); // 작업 완료 후 다시 활성화
                }
            };

            worker.execute(); // SwingWorker 실행

        }
    }

    // Frame 윈도우 리스너
    class WindowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            if (e.getSource() instanceof JFrame eventFrame) {
                if (eventFrame == mainFrame) {
                    int answer = JOptionPane.showConfirmDialog(null, "게임을 종료하시겠습니까?", "게임 종료", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null);
                    if (answer == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                } else if (eventFrame == gameFrame) {
                    int answer = JOptionPane.showConfirmDialog(null, "게임을 중단하고 메인 화면으로 돌아가시겠습니까?", "게임 중단", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null);
                    if (answer == JOptionPane.YES_OPTION) {
                        eventFrame.dispose();
                        mainFrame.setVisible(true);
                    }
                } else { // eventFrame == GetLevelFrame || eventFrame == RankingFrame
                    eventFrame.setVisible(false);
                    mainFrame.setVisible(true);
                }

            }
        }
    }
}
