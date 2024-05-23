package org.bro.tubesoop2.animal;

import org.bro.tubesoop2.product.Product;
import org.bro.tubesoop2.product.ProductPlant;

import java.util.ArrayList;

public class Herbivore extends Animal{

    public Herbivore(String name, int weightToHarvest, ArrayList<Product> products){
        super(name, weightToHarvest, products);

    }

    @Override
    public void eat(Product p) throws Exception {
        if(p instanceof ProductPlant){
            this.weight+= p.getAddedWeight();
        }
        else{
            throw new Exception("Cannot eat");
        }
    }
}
