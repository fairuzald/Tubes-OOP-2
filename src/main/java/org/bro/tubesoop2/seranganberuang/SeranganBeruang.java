package org.bro.tubesoop2.seranganberuang;

import javafx.util.Pair;
import org.bro.tubesoop2.countdowntimer.ITimerSubscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SeranganBeruang implements ITimerSubscriber {
    List<Pair<Integer,Integer>> area_serangan;
    int startRow;
    int startCol;
    int subCols;
    int subRows;

    public SeranganBeruang() {
        this.area_serangan = new ArrayList<>();
        int totalRows = 4;
        int totalCols = 5;

        Random random = new Random();

        this.subRows = random.nextInt(totalRows) + 1;
        this.subCols = random.nextInt(totalCols) + 1;


        this.startRow = random.nextInt(totalRows - subRows + 1);
        this.startCol = random.nextInt(totalCols - subCols + 1);
    }

    // TODO: Tungguin implementasi grid
    @Override
    public void update(int count) {
        // Nanti iterasinya diganti
        for(Pair<Integer,Integer> item: this.area_serangan) {
            return;
        }
    }

}
