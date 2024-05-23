package org.bro.tubesoop2.seranganberuang;

import javafx.util.Pair;
import org.bro.tubesoop2.countdowntimer.ITimerSubscriber;
import org.bro.tubesoop2.grid.Grid;
import org.bro.tubesoop2.resource.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SeranganBeruang implements ITimerSubscriber {
    Grid<Resource> areaSerangan;
    int startRow;
    int startCol;
    int subCols;
    int subRows;

    public SeranganBeruang() {
        this.areaSerangan = new Grid<>();
        int totalRows = 4;
        int totalCols = 5;

        Random random = new Random();

        this.subRows = random.nextInt(totalRows) + 1;
        this.subCols = random.nextInt(totalCols) + 1;

        this.startRow = random.nextInt(totalRows - subRows + 1);
        this.startCol = random.nextInt(totalCols - subCols + 1);
    }

    // TODO: Belom handle item Trap
    @Override
    public void update(int count) {
        if(count > 0) {return;}
        for(int i = this.startRow; i <= this.startRow + subRows; i++) {
            for(int j = this.startCol; j <= this.startCol + subCols; j++) {
                this.areaSerangan.pop(i,j);
            }
        }
    }

}