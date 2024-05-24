
package org.bro.tubesoop2.countdowntimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CountdownTimer {
    private int counter;
    private final Timer timer;
    private final List<ITimerSubscriber> subscribers;

    public CountdownTimer(int seconds) {
        this.counter = seconds * 1000;
        this.timer = new Timer();
        this.subscribers = new ArrayList<>();
    }

    public void subscribe(ITimerSubscriber subskrep) {
        this.subscribers.add(subskrep);
    }
    public boolean isTimeUp() {
        return counter == 0;
    }
    public int getTime() {
        return counter;
    }

    public void start() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                infokan(counter);
                if (counter > 0) {
                    System.out.println("Time remaining: " + counter + " seconds");
                    counter-=100;
                } else {
                    System.out.println("Time's up!");
                    timer.cancel();  // Stop the timer
                }
            }
        };

        timer.scheduleAtFixedRate(task, 0, 100);
    }

    public void infokan(int counter) {
        for (ITimerSubscriber subscriber : subscribers) {
            subscriber.update(counter);
        }
    }

//    public static void main(String[] args) {
//        // Initialize the countdown with 10 seconds
//        CountdownTimer countdownTimer = new CountdownTimer(10);
//        countdownTimer.start();
//    }
}