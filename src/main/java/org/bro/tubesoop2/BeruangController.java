//  package org.bro.tubesoop2;



//  import javafx.application.Platform;
//  import javafx.event.ActionEvent;
//  import javafx.fxml.FXML;
//  import org.bro.tubesoop2.countdowntimer.CountdownTimer;
//  import org.bro.tubesoop2.seranganberuang.SeranganBeruang;
//  import org.bro.tubesoop2.state.GameState;

//  import java.awt.*;
//  import java.util.List;
//  import java.util.Random;

//  public class BeruangController {
//     GameState state;

//     @FXML
//     private  Label timerLabel;

//     public BeruangController(GameState state, Label timerLabel) {
//         this.state = state;
//         this.timerLabel = timerLabel;
//     }
//     public void seranganBeruangAction(){
//         Random random = new Random();
//         if(true){
//             Thread timerThread = new Thread(() -> {
//             SeranganBeruang sb = new SeranganBeruang();
//             List <Integer> affected = sb.generateAffectedIndex();
//             for(int i = 0; i < affected.size(); i++){
//                 int idx = affected.get(i);
//                 System.out.println(idx);
//                 applyRedBorder(destinationViews[idx]);
//             }
//             System.out.println("Affected: " + affected);
//             CountdownTimer countdownTimer = new CountdownTimer(10);
//             Platform.runLater(() -> timerLabel.setVisible(true));
//             countdownTimer.start();
//             while (!countdownTimer.isTimeUp()) {
//                 try {
//                     applyRedBorder(destinationViews[0]);
//                     Thread.sleep(100);
//                     String currtime = Integer.toString(countdownTimer.getTime()/1000) + "," + Integer.toString((countdownTimer.getTime()%1000)/100); ;
//                     Platform.runLater(() -> {timerLabel.setText(currtime);}
//                     );
//                 } catch (InterruptedException e) {
//                     e.printStackTrace();
//                 }
//             }
//             Platform.runLater(() -> timerLabel.setVisible(false));
//             System.out.println("test");
//             System.out.println("Affected: " + affected);
//                 for(int i = 0; i < affected.size(); i++){
//                     int idx = affected.get(i);
//                     disableRedBorder(destinationViews[i]);
//                 }
            
//            });
//            timerThread.start();
//        }
//     }

//  }
