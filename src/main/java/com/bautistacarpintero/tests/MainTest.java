package com.bautistacarpintero.tests;

import com.bautistacarpintero.utilities.Ranking;
import com.bautistacarpintero.utilities.RankingElem;

import java.util.ArrayList;
import java.util.Random;

public class MainTest {


    public static void main(String[] args) {

        Ranking ranking = new Ranking();
        ArrayList<RankingElem> outElems = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            RankingElem elem = new RankingElem("Elem"+i);
            ranking.addElem(elem);
            outElems.add(elem);
        }


        for (int it = 0; it < 100; it++) {
            System.out.println("Iteration: "+it+"\n");

            for(RankingElem elem : outElems){
                elem.setTime(random.nextInt(10));
            }
//            ranking.updateScores();

            System.out.println(ranking.getTimes());
            System.out.println(ranking.getScores());
            System.out.println("---------\n");
        }


    }
}
