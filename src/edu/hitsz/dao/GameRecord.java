package edu.hitsz.dao;

/**
 * @author wb-yu
 */
public class GameRecord {

    private final String userName;
    private final String time;
    private final int score;
    private final String difficulty;

    public GameRecord(String userName, String time, int score, String difficulty) {
        this.userName = userName;
        this.time = time;
        this.score = score;
        this.difficulty = difficulty;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getTime() {
        return this.time;
    }

    public int getScore() {
        return this.score;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

}
