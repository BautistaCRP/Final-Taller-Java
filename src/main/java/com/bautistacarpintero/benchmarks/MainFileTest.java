package com.bautistacarpintero.benchmarks;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MainFileTest {


    public static void main(String[] args) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/"+"testFile.csv"));

            String header = "a,b,c,d\n";
            bufferedWriter.write(header);

            for (int i = 0; i < 100; i++) {
                StringBuilder builder = new StringBuilder();
                builder
                        .append(i)      // a
                        .append(",")
                        .append(i+200)  // b
                        .append(",")
                        .append(i+300)  // c
                        .append(",")
                        .append(i+400)  // d
                        .append("\n");

                bufferedWriter.write(builder.toString());
            }

            bufferedWriter.flush();
            bufferedWriter.close();




        } catch (IOException e) {
            e.printStackTrace();
            /*
                java.io.IOException – if the named file exists but is a directory rather
                than a regular file, does not exist but cannot be created, or cannot be
                opened for any other reason
             */
        }
    }
}
