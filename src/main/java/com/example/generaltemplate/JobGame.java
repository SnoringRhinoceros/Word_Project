package com.example.generaltemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static com.example.generaltemplate.HelloController.BASE_END_TIME;
import static com.example.generaltemplate.HelloController.allTimers;

public class JobGame {
    private final Timer timer;
    private int timeElapsed;
    private final Guesses guesses;
    private ChosenWord chosenWord;
    private int wordsCorrect;
    private final Player player;
    private int endTime;

    public JobGame(Player player) {
        this.player = player;
        timer = new Timer();
        allTimers.add(timer);
        guesses = new Guesses();
        endTime = BASE_END_TIME;
    }

    public void start(HelloController.onTimerUpdateTask onTimerUpdateTask) {
        makeNewChosenWord();
        timer.schedule(new TimerTicker(onTimerUpdateTask), 0 , 1000);
    }

    public void makeNewChosenWord() {
        guesses.clear();
        chosenWord = new ChosenWord();
        chosenWord.setHiddenChosenWord(guesses);
    }

    public class TimerTicker extends TimerTask {
        HelloController.onTimerUpdateTask onTimerUpdateTask;
        public TimerTicker(HelloController.onTimerUpdateTask onTimerUpdateTask) {
            this.onTimerUpdateTask = onTimerUpdateTask;
        }

        @Override
        public void run() {
//            System.out.println("Start time is at :" +
//                    LocalDateTime.ofInstant(Instant.ofEpochMilli(scheduledExecutionTime()), ZoneId.systemDefault()));
            try {
                timeElapsed++;
                onTimerUpdateTask.run();
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void guess(char guess) {
        guesses.guess(chosenWord, guess);
        chosenWord.setHiddenChosenWord(guesses);
        if (chosenWord.wordFullyGuessed()) {
            wordsCorrect++;
            player.addMoney();
        }
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }

    public Timer getTimer() {
        return timer;
    }

    public ChosenWord getChosenWord() {
        return chosenWord;
    }

    public Guesses getGuesses() {
        return guesses;
    }

    public int getWordsCorrect() {
        return wordsCorrect;
    }

    public int getEndTime() {
        return endTime;
    }
}
