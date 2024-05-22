package org.bro.tubesoop2.toko;

import org.bro.tubesoop2.quantifiable.Quantifiable;
import org.bro.tubesoop2.resource.Resource;

public class Toko {
    private Quantifiable<Resource> stock;
    public Toko(Quantifiable<Resource> stock) {}

    public Quantifiable<Resource> getStock() {
        return stock;
    }

}
