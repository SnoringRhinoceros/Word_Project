package com.example.generaltemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static com.example.generaltemplate.HelloController.allTimers;

public class JobGame {
    private final Timer timer;
    private int timeElapsed;
    private String chosenWord;
    private String hiddenChosenWord;
    private final ArrayList<Character> guessedLetters;

    public JobGame() {
        timer = new Timer();
        allTimers.add(timer);
        guessedLetters = new ArrayList<>();
    }

    public void start(HelloController.onTimerUpdateTask onTimerUpdateTask) {
        makeNewChosenWord();
        timer.schedule(new TimerTicker(onTimerUpdateTask), 0 , 1000);
    }

    public void makeNewChosenWord() {
        guessedLetters.clear();
        chosenWord = getRandWord();
        System.out.println(chosenWord);
        setHiddenChosenWord();
    }

    private void setHiddenChosenWord() {
        hiddenChosenWord = "";
        for (int i = 0; i < chosenWord.length(); i++) {
            char c = chosenWord.charAt(i);
            if (guessedLetters.contains(Character.toLowerCase(c))) {
                hiddenChosenWord += c;
            } else {
                hiddenChosenWord += "_";
            }
        }
    }

    public boolean wordFullyGuessed() {
        return !hiddenChosenWord.contains("_");
    }

    public class TimerTicker extends TimerTask {
        HelloController.onTimerUpdateTask onTimerUpdateTask;
        public TimerTicker(HelloController.onTimerUpdateTask onTimerUpdateTask) {
            this.onTimerUpdateTask = onTimerUpdateTask;
        }

        @Override
        public void run() {
            System.out.println("Start time is at :" +
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(scheduledExecutionTime()), ZoneId.systemDefault()));
            try {
                timeElapsed++;
                onTimerUpdateTask.run();
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private String getRandWord() {
        try {
            String path = "src/main/java/com/example/generaltemplate/words.txt";
            BufferedReader lineNumReader = new BufferedReader(new FileReader(path));
            int numLines = 0;
            while (lineNumReader.readLine() != null) {
                numLines++;
            }
            lineNumReader.close();

            BufferedReader reader = new BufferedReader(new FileReader(path));
            int randNum = generateRandNum(0, numLines-1);
            for (int i = 0; i < randNum; i++) {
                reader.readLine();
            }
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int generateRandNum(int minInc, int maxInc) {
        return minInc + (int) (Math.random()*((maxInc - minInc) + 1));
    }

    public void guess(char guess) {
        guessedLetters.add(Character.toLowerCase(guess));
        setHiddenChosenWord();
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }

    public Timer getTimer() {
        return timer;
    }

    public String getHiddenChosenWord() {
        return hiddenChosenWord;
    }
}
