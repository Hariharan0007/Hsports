package com.example.hsports;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class Result {
    String chestNo;
    String maxScore;
    String position;

    public Result(String chestNo, String maxScore, String position) {
        this.chestNo = chestNo;
        this.maxScore = maxScore;
        this.position = position;
    }

    @NonNull
    @Override
    public String toString() {
        return chestNo + " " + maxScore + " " + position;
    }

    public String getPosition() {
        return position;
    }

    public HashMap<String, String> toMap() {
        HashMap<String, String> result = new HashMap<>();
        result.put("CHESTNO", chestNo);
        result.put("MAXSCORE", maxScore);
        result.put("POSITION", position);
        return result;
    }
}
