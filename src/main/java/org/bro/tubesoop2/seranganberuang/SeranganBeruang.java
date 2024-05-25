package org.bro.tubesoop2.seranganberuang;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SeranganBeruang {

    public List<Integer> generateAffectedIndex() {
        Random random = new Random();
        int size;
        int width;
        int height;

        // Generate a random size between 1 and 6
        do {
            width = random.nextInt(5) + 1; // Generate a random width between 1 and 5
            height = random.nextInt(4) + 1; // Generate a random height between 1 and 4
            size = width * height;
        } while (size > 6);

        int startRow = random.nextInt(4 - height + 1); // Generate a random start row
        int startCol = random.nextInt(5 - width + 1); // Generate a random start column

        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int index = (startRow + i) * 5 + (startCol + j); // Convert the row and column to an index
                numbers.add(index);
            }
        }

        return numbers;
    }


}
