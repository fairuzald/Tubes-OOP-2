package org.bro.tubesoop2.animal;

import org.bro.tubesoop2.product.Product;
import org.bro.tubesoop2.product.ProductAnimal;

import java.util.ArrayList;

public class Carnivore extends Animal{
    public Carnivore(String name, int weightToHarvest, Product products){
        super(name, weightToHarvest, products);

    }

    @Override
    public void eat(Product p) throws Exception {
        if(p instanceof ProductAnimal){
            this.weight+= p.getAddedWeight();
        } else{
            throw new Exception("Cannot eat");
        }
    }
}
