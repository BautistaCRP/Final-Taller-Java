package com.bautistacarpintero.utilities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ranking {


    private List<RankingElem> elems;


    public Ranking() {
        this.elems = new ArrayList<>();
    }

    public boolean addElem(RankingElem elem) {
        return this.elems.add(elem);
    }

    public boolean removeElem(RankingElem elem) {
        return this.elems.remove(elem);
    }


    private void updateScores() {

        elems.sort(RankingElem::compareTo);


        int score = -1;
        long lastTime = Long.MAX_VALUE;

        for (int i = elems.size() - 1; i >= 0; i--) {
            RankingElem elem = elems.get(i);

            if (elem.getTime() != lastTime)
                score++;

            elem.addScore(score);

            lastTime = elem.getTime();

        }


    }

    public String getTimes() {
        StringBuilder builder = new StringBuilder();
        elems.sort(RankingElem::compareTo);

        builder.append("Times in ms: \n");
        for (RankingElem elem : elems) {
            builder.append(elem.getName())
                    .append(": ")
                    .append(elem.getTime())
                    .append(" ms \n");
        }

        return builder.toString();
    }

    public String getScores() {
        this.updateScores();
        StringBuilder builder = new StringBuilder();

        elems.sort(new Comparator<RankingElem>() {
            @Override
            public int compare(RankingElem o1, RankingElem o2) {
                return Double.compare(o2.getScore(), o1.getScore());
            }
        });

        builder.append("Scores: \n");
        for (RankingElem elem : elems) {
            builder.append(elem.getName())
                    .append(": ")
                    .append(elem.getScore())
                    .append("\n");
        }


        return builder.toString();
    }


}
