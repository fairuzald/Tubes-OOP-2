package org.bro.tubesoop2.creature;

import org.bro.tubesoop2.item.Item;
import org.bro.tubesoop2.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Creature {
    private List<Product> drops;
    private List<Item> itemsActive;

    public Creature(List<Product> drops) {
        this.drops = drops;
        itemsActive = new ArrayList<>();
    }

    public List<Product> getDrops() {
        return drops;
    }

    public List<Item> getItemsActive() {
        return itemsActive;
    }

    public void addItem(Item item) {
        itemsActive.add(item);
    }

    public List<Item> getItems() {
        return itemsActive;
    }


}
