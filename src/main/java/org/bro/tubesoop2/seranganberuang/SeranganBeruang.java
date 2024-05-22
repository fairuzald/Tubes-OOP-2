package org.bro.tubesoop2.seranganberuang;

import javafx.util.Pair;
import org.bro.tubesoop2.countdowntimer.ITimerSubscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SeranganBeruang implements ITimerSubscriber {
    List<Pair<Integer,Integer>> area_serangan;


    public SeranganBeruang() {
        this.area_serangan = new ArrayList<>();
        int totalRows = 4;
        int totalCols = 5;

        Random random = new Random();

        int subRows = random.nextInt(totalRows) + 1;
        int subCols = random.nextInt(totalCols) + 1;


        int startRow = random.nextInt(totalRows - subRows + 1);
        int startCol = random.nextInt(totalCols - subCols + 1);

        for (int i = 0; i < subRows; i++) {
            for (int j = 0; j < subCols; j++) {
                this.area_serangan.add(new Pair<>(startRow + i, startCol + j));
            }
        }
    }

    // TODO: Tungguin implementasi grid
    @Override
    public void update(int count) {
        for(Pair<Integer,Integer> item: this.area_serangan) {
            return;
        }
    }

}
