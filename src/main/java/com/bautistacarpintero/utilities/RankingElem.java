package com.bautistacarpintero.utilities;

public class RankingElem implements Comparable<RankingElem>{

    private String name;
    private long time;
    private double score;

    public RankingElem(String name) {
        this.name = name;
        this.time = 0;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void addScore(double score) {
        this.score += score;
    }


    @Override
    public int compareTo(RankingElem o) {
        return Long.compare(time,o.time);
    }

    @Override
    public String toString() {
        return name+" | "+time;
    }
}
