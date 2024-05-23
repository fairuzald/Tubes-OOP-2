package org.bro.tubesoop2.animal;

import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.product.Product;

import java.util.ArrayList;

public abstract class Animal extends Creature {
    protected int weightToHarvest;
    protected int weight;
    Animal(String name, int weightToHarvest, ArrayList <Product> products) {
        super(name, products);
        this.weightToHarvest = weightToHarvest;
        this.weight = 0;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isHarvestable() {
        return weight >= weightToHarvest;
    }
    abstract public void eat(Product p) throws Exception;
}
