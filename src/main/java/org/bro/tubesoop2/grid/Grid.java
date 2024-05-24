package org.bro.tubesoop2.grid;

import org.bro.tubesoop2.resource.Resource;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

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

        public void put(Location l, T t){
            setElement(l.getCol(), l.getRow(), t);
        }

        public boolean isFilled(int row, int col) {
            return isFilled.get(row).get(col);
        }
        public boolean isFilled(Location l) {
        return isFilled.get(l.getRow()).get(l.getCol());
    }

        public int getCountFilled() {
            return countFilled;
        }

        public int getCountNotFilled() {
            return countNotFilled;
        }

        public void forEachActive(Consumer<Location> callback){
            try{
                for (int i = 0; i < elements.size(); i++) {
                    for (int j = 0; j < elements.get(i).size(); j++) {
                        if(isFilled.get(i).get(j)){
                            callback.accept(new Location(i, j));
                        }
                    }
                }
            } catch (Exception e){

            }
        }

        public List<T> getFlattened() {
            List<T> result = new ArrayList<>();
            Collections.addAll(elements);

            return result;
        }

        public void clear(){
            for (int i = 0; i < elements.size(); i++) {
                for (int j = 0; j < elements.get(i).size(); j++) {
                    elements.get(i).set(j, null);
                    isFilled.get(i).set(j, false);
                }
            }
            countFilled = 0;
            countNotFilled = elements.size() * elements.get(0).size();
        }
    }
