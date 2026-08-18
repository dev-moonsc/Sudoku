import javax.swing.*;
import java.awt.*;

public class RankingFrame extends DefaultFrame {
    public RankingFrame() {
        super("Ranking");

        setLayout(new GridLayout(1, 3));
        setSize(600, 600);
    }

    public void initialize(DefaultListModel<GameRecord> easyModel, DefaultListModel<GameRecord> normalModel, DefaultListModel<GameRecord> hardModel) {
        RankingPanel easyRankingPanel = new RankingPanel(1, easyModel);
        RankingPanel noramlRankingPanel = new RankingPanel(2, normalModel);
        RankingPanel hardRankingPanel = new RankingPanel(3, hardModel);

        add(easyRankingPanel);
        add(noramlRankingPanel);
        add(hardRankingPanel);
    }
}
