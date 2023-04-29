package com.strikedevx.norwayvsswitzerland;

public class Score {
    private int score;
    private String timestamp;

    public Score(int score, String timestamp) {
        this.score = score;
        this.timestamp = timestamp;
    }

    public int getScore() {
        return score;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
