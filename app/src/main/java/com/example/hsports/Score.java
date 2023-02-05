package com.example.hsports;

import java.util.ArrayList;
import java.util.Collections;

public class Score {
    private String chestNo;
    private ArrayList<String> scores;
    
    public Score(String chestNo, ArrayList<String> scores) {
        this.chestNo = chestNo;
        this.scores = scores;
    }

    public Double get(int i) {
        return Double.parseDouble(scores.get(i));
    }

    public void cleanseScores() {
        // valid: double, {'x' , 'X', 's', 'S' -> 0.0}
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i).equals("x") || scores.get(i).equals("X") || scores.get(i).equals("s") || scores.get(i).equals("S")) {
                scores.set(i, "0.0");
            }
        }
    }

    public String getChestNo() {
        return chestNo;
    }

    public ArrayList<String> getScores() {
        return scores;
    }

    public void sortScores() {
        for (int i = 0; i < scores.size(); i++) {
            for (int j = 0; j < scores.size() - 1; j++) {
                if (Double.parseDouble(scores.get(j)) < Double.parseDouble(scores.get(j + 1))) {
                    Collections.swap(scores, j, j + 1);
                }
            }
        }
    }


}
