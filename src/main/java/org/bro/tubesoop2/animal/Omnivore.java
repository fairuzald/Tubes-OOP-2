package org.bro.tubesoop2.animal;

import org.bro.tubesoop2.product.Product;
import org.bro.tubesoop2.product.ProductAnimal;

import java.util.ArrayList;

public class Omnivore extends Animal{
    Omnivore(String name, int weightToHarvest, ArrayList<Product> products){
        super(name, weightToHarvest, products);

    }

    @Override
    public void eat(Product p) throws Exception {
        this.weight+=p.getAddedWeight();
    }
}
