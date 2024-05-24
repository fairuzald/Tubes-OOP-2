package org.bro.tubesoop2.grid;

public class Location {
    private Integer row, col;

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getCol() {
        return col;
    }
    public Location(String str) {
        String rowIcosiHexaString = "";
        int i;
        for (i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            // if c is a number
            if (c >= '0' && c <= '9') {
                break;
            }

            rowIcosiHexaString += c;
        }

        Icosihexastring icosihexastring = new Icosihexastring(rowIcosiHexaString);
        row = icosihexastring.getValue();

        int col = 0;
        for (;  i < str.length(); i++) {
            char c = str.charAt(i);

            if (c >= '0' && c <= '9') {
                col *= 10;
                col += c - '0';
            }
        }

        this.col = col-1;
    }

    public String toString() {
        Icosihexastring rowString = new Icosihexastring(row);
        Integer col1Indexed = col + 1;
        String colStr = col1Indexed.toString();
        if (col1Indexed < 10) {
            colStr = "0" + col1Indexed;
        }
        return rowString.getStr() + colStr;
    }
}
