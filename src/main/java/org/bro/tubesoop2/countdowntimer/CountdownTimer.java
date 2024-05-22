package org.bro.tubesoop2.countdowntimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CountdownTimer {
    private int counter;
    private Timer timer;
    private List<ITimerSubscriber> subscriber;

    public CountdownTimer(int seconds) {
        this.counter = seconds;
        this.timer = new Timer();
        this.subscriber = new ArrayList<>();
    }

    public void subscribe(ITimerSubscriber subskrep) {
        this.subscriber.add(subskrep);
    }

    public void start() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                infokan(counter);
                if (counter > 0) {
                    System.out.println("Time remaining: " + counter + " seconds");
                    counter--;
                } else {
                    System.out.println("Time's up!");
                    timer.cancel();  // Stop the timer
                }
            }
        };

        // Schedule the task to run every second (1000 milliseconds)
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public void infokan(int counter) {
        for (ITimerSubscriber subscriber : subscriber) {
            subscriber.update(counter);
        }
    }

//    public static void main(String[] args) {
//        // Initialize the countdown with 10 seconds
//        CountdownTimer countdownTimer = new CountdownTimer(10);
//        countdownTimer.start();
//    }
}
