package org.bro.tubesoop2.creature;

import org.bro.tubesoop2.item.Item;
import org.bro.tubesoop2.product.Product;
import org.bro.tubesoop2.resource.Resource;

import java.util.ArrayList;
import java.util.List;

public abstract class Creature extends Resource {
    private List<Product> drops;
    private List<Item> itemsActive;


    public Creature(String name, List<Product> drops) {
        super(name);
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

    public abstract void consumeItem(Item item);

    public abstract void setUmurOrBerat(int umurOrBerat);
    public abstract int getUmurOrBerat();
}
