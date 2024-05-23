package org.bro.tubesoop2.product;

import org.bro.tubesoop2.resource.Resource;

public class Product extends Resource {
    private Integer addedWeight;
    private Integer price;

    public Product(String name, int addedWeight, int price){
        super(name);
        this.addedWeight = addedWeight;
        this.price = price;
    }

    public Integer getAddedWeight() {
        return addedWeight;
    }

    public void setAddedWeight(Integer addedWeight) {
        this.addedWeight = addedWeight;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}

