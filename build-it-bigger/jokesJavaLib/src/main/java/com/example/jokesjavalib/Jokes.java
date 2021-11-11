package com.example.jokesjavalib;

import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

public class Jokes {
    public Jokes() {
    }

    public static String getJoke(){
        List<String> staticStringList = asList(
                "Joke 01",
                "Joke 02",
                "Joke 03",
                "Joke 04",
                "Joke 05"
        );
        Random random = new Random();

        return staticStringList.get(random.nextInt(staticStringList.size()));
    }
}
