package org.bro.tubesoop2.animal;

import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.item.Accelerate;
import org.bro.tubesoop2.item.Delay;
import org.bro.tubesoop2.item.Item;
import org.bro.tubesoop2.product.Product;

import java.util.ArrayList;

public abstract class Animal extends Creature {
    protected int weightToHarvest;
    protected int weight;
    Animal(String name, int weightToHarvest, Product drops) {
        super(name, drops);
        this.weightToHarvest = weightToHarvest;
        this.weight = 0;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void addWeight(int weight){
        this.weight += weight;
    }
    public void reduceWeight(int weight){
        this.weight -= weight;
        if(this.weight < 0) this.weight = 0;
    }

  
    abstract public void eat(Product p) throws Exception;

    @Override
    public void consumeItem(Item item) {
        item.consumedBy(this);
    }

    @Override
    public void setUmurOrBerat(int u){this.weight = u;}

    @Override
    public int getUmurOrBerat(){return this.weight;}

    @Override
    public Product harvest()  throws IllegalStateException {
        if(weight >= weightToHarvest){
            return drops;
        }
        throw new IllegalStateException(getFormattedName() + " is not ready to be harvested. Atleast weight is "+weightToHarvest);
    }
}
