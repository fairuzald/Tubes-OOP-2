package org.bro.tubesoop2.grid;

public class Icosihexastring {
    private Integer value;
    private String str;

    public Icosihexastring(Integer value) {
        this.value = value;

        String reversed = "";
        reversed += (value % 26) + 'A';
        while (value/26 > 0) {
            value /= 26;
            reversed += ((--value) % 26) + 'A';
        }

        str = "";
        for (int i = reversed.length() - 1; i >= 0; i--) {
            str += reversed.charAt(i);
        }
    }

    public Icosihexastring(String str) {
        this.str = str;

        int val = 0;
        for (int i = 0; i < str.length() - 1; i++) {
            val += (str.charAt(i) - 'A' + 1);
            val *= 26;
        }

        val += str.charAt(str.length() - 1) - 'A';
        value = val;
    }

    public Integer getValue() {
        return value;
    }

    public String getStr() {
        return str;
    }

}
