package org.bro.tubesoop2.plant;

import java.util.ArrayList;

import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.item.Item;
import org.bro.tubesoop2.product.Product;

public class Plant extends Creature {

    private Integer durationToHarvest = 0;
    private Integer currentAge = 0;

    public Plant(Integer duration, String name, Product drops) {
        super(name, drops);
        plantList.add(this);
        this.durationToHarvest = duration;
    }

    public Integer getDurationToHarvest() {
        return durationToHarvest;
    }

    public void setDurationToHarvest(Integer durationToHarvest) {
        this.durationToHarvest = durationToHarvest;
    }

    public Integer getCurrentAge() {
        return currentAge;
    }

    public void setCurrentAge(Integer currentAge) {
        this.currentAge = currentAge;
    }

    public void addAge(int age){
        this.currentAge += age;
    }
    public void reduceAge(int age){
        this.currentAge -= age;
        if(this.currentAge < 0) this.currentAge = 0;
    }

    @Override
    public void consumeItem(Item item) {
        item.consumedBy(this);
    }

    @Override
    public void setUmurOrBerat(int u){this.currentAge = u;}


    @Override
    public int getUmurOrBerat(){return this.currentAge;}
    
    
    // All plants that is constructed
    private static ArrayList<Plant> plantList = new ArrayList<Plant>();
    public static void IncreaseAllPlantAge(){
        for (Plant p : plantList){
            p.currentAge++;
        }
    } 

    @Override
    public Product harvest() throws IllegalStateException {
        if(currentAge >= durationToHarvest){
            return drops;
        }
        throw new IllegalStateException(getFormattedName() + " is not ready to be harvested. Atleast age is "+durationToHarvest);
    }

}
