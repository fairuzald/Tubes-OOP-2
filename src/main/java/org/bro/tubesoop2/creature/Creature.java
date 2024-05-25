package org.bro.tubesoop2.creature;

import org.bro.tubesoop2.item.Item;
import org.bro.tubesoop2.product.Product;
import org.bro.tubesoop2.resource.Resource;

import java.util.ArrayList;
import java.util.List;

public abstract class Creature extends Resource {
    protected Product drops;
    private List<Item> itemsActive;


    public Creature(String name, Product drops) {
        super(name);
        this.drops = drops;
        itemsActive = new ArrayList<>();
    }

    public Product getDrop() {
        return drops;
    }

    public void updateImage() {

    }

    public boolean  isHarvestable() {
        return getWhenCanHarvest() <= getUmurOrBerat();
    }

    public abstract Product harvest() throws IllegalStateException;

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

    public abstract Integer getWhenCanHarvest();

    public abstract void setUmurOrBerat(int umurOrBerat);
    public abstract int getUmurOrBerat();
}
