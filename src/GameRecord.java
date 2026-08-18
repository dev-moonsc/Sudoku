public class GameRecord implements Comparable<GameRecord> {
    private final int level;
    private final String userName;
    private final int time;

    public GameRecord(int level, String userName, int time) {
        this.level = level;
        this.userName = userName;
        this.time = time;
    }

    @Override
    public int compareTo(GameRecord other) {
        return Integer.compare(this.time, other.time);
    }

    @Override
    public String toString() {
        return userName + ", " + time + "초\n";
    }

    public int getLevel() {
        return level;
    }

    public String getUserName() {
        return userName;
    }

    public int getTime() {
        return time;
    }
}
