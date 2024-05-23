package org.bro.tubesoop2.seranganberuang;

import org.bro.tubesoop2.countdowntimer.ITimerSubscriber;
import org.bro.tubesoop2.grid.Grid;
import org.bro.tubesoop2.resource.Resource;
import java.util.Random;


public class SeranganBeruang implements ITimerSubscriber {
    Grid<Resource> areaSerangan;
    int startRow;
    int startCol;
    int deltaRow;
    int deltaCol;

    public SeranganBeruang() {
        this.areaSerangan = new Grid<>();
        int totalRows = 4;
        int totalCols = 5;

        Random random = new Random();
        deltaRow = random.nextInt(totalRows)+1;
        do {
            deltaCol = random.nextInt(6 / deltaRow) + 1;
        } while (deltaCol > totalCols);


        this.startRow = random.nextInt(totalRows-deltaRow+1);
        this.startCol = random.nextInt(totalCols-deltaCol+1);

        System.out.println("Start: "+startRow+" "+startCol);
        System.out.println("Delta: "+rowBox+" "+colBox);

    }

    // TODO: Belom handle item Trap
    @Override
    public void update(int count) {
        if(count > 0) {return;}
        for(int i = this.startRow; i <= this.startRow + deltaRow; i++) {
            for(int j = this.startCol; j <= this.startCol + deltaCol; j++) {
                this.areaSerangan.pop(i,j);
            }
        }
    }
}
