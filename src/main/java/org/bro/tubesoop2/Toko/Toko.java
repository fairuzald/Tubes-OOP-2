package org.bro.tubesoop2.Toko;

import org.bro.tubesoop2.Quantifiable.Quantifiable;
import org.bro.tubesoop2.Resource.Resource;

public class Toko {
    private Quantifiable<Resource> stock;
    public Toko(Quantifiable<Resource> stock) {}

    public Quantifiable<Resource> getStock() {
        return stock;
    }

}
