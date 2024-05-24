package org.bro.tubesoop2.item;

import org.bro.tubesoop2.animal.Animal;
import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.plant.Plant;

public class Delay extends Item {
    

    @Override
    public void consumedBy(Creature creature) {
        if(hasProtectCard(creature)) return;

        if (creature instanceof Animal) {
            Animal animal = (Animal) creature;
            animal.reduceWeight(8);
            System.out.println("Animal has consumed the Delay item.");
        } else if (creature instanceof Plant) {
            Plant plant = (Plant) creature;
            plant.reduceAge(2);
            System.out.println("Creature has consumed the Delay item.");
        }
    }
}