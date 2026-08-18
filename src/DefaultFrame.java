import javax.swing.*;

/**
 * 프로그램 내에서 쓸 Frame의 기본 설정값을 포함한 추상 클래스
 */
public abstract class DefaultFrame extends JFrame {
    public DefaultFrame(String title) {
        super(title);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // 닫았을 때 동작 : 아무것도 안함
        setLocationRelativeTo(null); // 열릴 때 위치 : 화면 가운데
        setResizable(false); // 창 크기 조절 불가
        setVisible(false);
    }
}
