package org.bro.tubesoop2.toko;

public class TokoException extends Exception {
}

class BuyingFromShopNotSuccesfullException extends TokoException {
    @Override
    public String getMessage() {
        return "Buying from shop was not successful.";
    }
}

class ItemShopNotFoundException extends TokoException {
    @Override
    public String getMessage() {
        return "Item shop not found.";
    }
}

class ItemShopNotEqualException extends TokoException {
    @Override
    public String getMessage() {
        return "Item shop not equal.";
    }
}

class ItemShopEmptyException extends TokoException {
    @Override
    public String getMessage() {
        return "Item shop is empty.";
    }
}

class UangTidakCukupShopException extends TokoException {
    @Override
    public String getMessage() {
        return "Insufficient funds in the shop.";
    }
}

class StockTidakCukupShopException extends TokoException {
    @Override
    public String getMessage() {
        return "Insufficient stock in the shop.";
    }
}

class StockTidakCukupPlayer extends TokoException {
    @Override
    public String getMessage() {
        return "Insufficient stock in the deck.";
    }
}

class BeliOutOfRange extends TokoException {
    @Override
    public String getMessage() {
        return "Beli out of range.";
    }
}

class PenyimpananTidakCukup extends TokoException {
    @Override
    public String getMessage() {
        return "Insufficient storage.";
    }
}
