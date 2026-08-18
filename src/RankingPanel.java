import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RankingPanel extends JPanel {

    public RankingPanel(int level, DefaultListModel<GameRecord> listModel) {
        super(new BorderLayout());

        // 각 Panel 맨 위에 Label 생성
        JLabel levelLabel = setLevelLabel(level);
        add(levelLabel, BorderLayout.NORTH);

        // JScrollPane 생성
        JScrollPane levelSP = new JScrollPane(new RankingList<>(listModel));
        levelSP.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(levelSP, BorderLayout.CENTER);

        // 각 Panel마다 경계 설정
        setBorder(new EmptyBorder(10, 10, 10, 5));
    }

    private static JLabel setLevelLabel(int level) {
        String levelString;
        if (level == 1)
            levelString = "EASY";
        else if (level == 2)
            levelString = "NORMAL";
        else
            levelString = "HARD";

        JLabel levelLabel = new JLabel(levelString); // JLabel 텍스트 설정 : levelString 값으로
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER); // 텍스트 가운데 정렬
        levelLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // 간격 설정

        // 폰트 설정
        Font currentFont = levelLabel.getFont();
        Font newFont = new Font(currentFont.getFontName(), Font.PLAIN, currentFont.getSize() + 5);
        levelLabel.setFont(newFont);

        return levelLabel;
    }
}
