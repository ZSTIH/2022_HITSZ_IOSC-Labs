package edu.hitsz.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wb-yu
 */
public class GameRecordDaoImpl implements GameRecordDao {

    private final List<GameRecord> gameRecords;
    private final File f = new File("data/gameRecords.txt");


    public GameRecordDaoImpl() throws IOException {
        gameRecords = new ArrayList<>();
        if (!f.exists()) {
            f.createNewFile();
        }
        FileInputStream fip = new FileInputStream(f);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fip));
        String[] lineFromFile;
        while (reader.ready()) {
            lineFromFile = reader.readLine().split(",");
            gameRecords.add(new GameRecord(lineFromFile[0], lineFromFile[1], Integer.parseInt(lineFromFile[2]), lineFromFile[3]));
        }
        fip.close();
        reader.close();
    }

    public void writeToFile() throws IOException {
        FileOutputStream fop = new FileOutputStream(f);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fop));
        if (gameRecords != null) {
            for (GameRecord gameRecord : gameRecords) {
                String lineToFile;
                lineToFile = gameRecord.getUserName() + ',' + gameRecord.getTime() + ',' + gameRecord.getScore() + ',' + gameRecord.getDifficulty() + '\n';
                writer.write(lineToFile);
            }
        }
        writer.close();
        fop.close();
    }

    @Override
    public List<GameRecord> getAllRecords() {
        return this.gameRecords;
    }

    @Override
    public void addRecord(GameRecord gameRecord) {
        this.gameRecords.add(gameRecord);
    }

    @Override
    public void deleteRecord(GameRecord gameRecord) {
        this.gameRecords.remove(gameRecord);
    }

}
