package org.bro.tubesoop2.grid;

import java.util.*;

    public class Grid<T> {

        private static final int defaultWidth = 5;
        private static final int defaultHeight = 4;


        private List<List<T>> elements;
        private List<List<Boolean>> isFilled;
        int countFilled;
        int countNotFilled;

        public Grid() {
            this(defaultWidth, defaultHeight);
        }

        public Grid(int width, int height) {
            elements = new ArrayList<>();
            isFilled = new ArrayList<>();

            for (int i = 0; i < height; i++) {
                elements.add(new ArrayList<>(width));
                isFilled.add(new ArrayList<>(width));

                for (int j = 0; j < width; j++) {
                    elements.get(i).add(null);
                    isFilled.get(i).add(false);
                }
            }

            countFilled = 0;
            countNotFilled = width * height;
        }

        public void setElement(int row, int col, T element) {
            if (!isFilled.get(row).get(col)) {
                isFilled.get(row).set(col, true);
                countFilled++;
                countNotFilled--;
            }
            elements.get(row).set(col, element);
        }

        public T getElement(int row, int col) {
            return elements.get(row).get(col);
        }

        public T getElement(Location loc) {
            int row = loc.getRow();
            int col = loc.getCol();

            return getElement(row, col);
        }

        public T pop(int row, int col) {
            if (row < 0 || row >= elements.size() || col < 0 || col >= elements.get(0).size()) {
                throw new IndexOutOfBoundsException("Row or column is out of range.");
            }

            if (!isFilled.get(row).get(col)) {
                throw new IllegalStateException("Element at specified position is not available.");
            }

            T val = elements.get(row).get(col);
            isFilled.get(row).set(col, false);

            countFilled--;
            countNotFilled++;

            return val;
        }

        public T pop(Location l) {
            int row = l.getRow();
            int col = l.getCol();

            return pop(row, col);
        }

        public boolean isFilled(int row, int col) {
            return isFilled.get(row).get(col);
        }

        public int getCountFilled() {
            return countFilled;
        }

        public int getCountNotFilled() {
            return countNotFilled;
        }

    }