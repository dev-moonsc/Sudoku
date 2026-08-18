import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ranking {
    private final String PATH;
    private final List<GameRecord> easyRanking;
    private final List<GameRecord> normalRanking;
    private final List<GameRecord> hardRanking;

    // Ctor
    public Ranking(String PATH) {
        this.PATH = PATH;
        this.easyRanking = new ArrayList<>();
        this.normalRanking = new ArrayList<>();
        this.hardRanking = new ArrayList<>();

        try {
            loadFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 게임 기록 업데이트
    public void updateRecords(int level, String name, int elapsedTime) {
        GameRecord record = new GameRecord(level, name, elapsedTime);

        if (level == 1) {
            easyRanking.add(record);
            Collections.sort(easyRanking);
        } else if (level == 2) {
            normalRanking.add(record);
            Collections.sort(normalRanking);
        } else {
            hardRanking.add(record);
            Collections.sort(hardRanking);
        }

        try {
            writeToFile(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<GameRecord> getRanking(int level) {
        if (level == 1)
            return easyRanking;
        else if (level == 2)
            return normalRanking;
        else
            return hardRanking;
    }

    // 파일에서 게임 기록을 로드
    private void loadFromFile() throws IOException {
        List<GameRecord> totalRecords = new ArrayList<>();

        boolean done = false;
        while (!done) {
            try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
                reader.readLine(); // 첫 줄은 헤더이므로 무시

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(",");

                    int level = Integer.parseInt(fields[0]);
                    String userName = fields[1];
                    int time = Integer.parseInt(fields[2]);

                    GameRecord gameRecord = new GameRecord(level, userName, time);

                    totalRecords.add(gameRecord);
                }

                done = true;

            } catch (FileNotFoundException e) {
                createFile();
            }
        }

        classifyRecords(totalRecords);
    }

    // 로드한 게임 기록을 분류
    private void classifyRecords(List<GameRecord> records) {
        for (GameRecord record : records) {
            int level = record.getLevel();

            if (level == 1)
                easyRanking.add(record);
            else if (level == 2)
                normalRanking.add(record);
            else
                hardRanking.add(record);
        }

        Collections.sort(easyRanking);
        Collections.sort(normalRanking);
        Collections.sort(hardRanking);
    }

    // 파일에 게임 기록 쓰기
    private void writeToFile(GameRecord record) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH, true))) {
            String recordString = record.getLevel() + "," + record.getUserName() + "," + record.getTime() + "\n";
            writer.write(recordString);
        }
    }

    // 파일이 없는 경우 생성
    private void createFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH))) {
            writer.write("level,userName,time\n");
        }
    }
}
