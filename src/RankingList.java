import javax.swing.*;
import java.awt.*;

public class RankingList<GameRecord> extends JList<GameRecord> {

    public RankingList(ListModel<GameRecord> dataModel) {
        super(dataModel);

        // 선택 기능 비활성화
        setSelectionModel(new DefaultListSelectionModel() {

            @Override
            public void setSelectionInterval(int index0, int index1) {
            }
        });

        // 리스트의 각 항목에 대한 외형 설정
        setCellRenderer(new DefaultListCellRenderer() {
            private static final int VERTICAL_PADDING = 10;
            private static final int HORIZONTAL_PADDING = 10;

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                // 기본 렌더러 구현 사용
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                // Border를 사용하여 간격 설정
                label.setBorder(BorderFactory.createEmptyBorder(VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING));

                // 각 셀 앞에 순위 붙이기
                label.setText((index + 1) + "위 : " + value.toString());

                return label;
            }
        });
    }

}