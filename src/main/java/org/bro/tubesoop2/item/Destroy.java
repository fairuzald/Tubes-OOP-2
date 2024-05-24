package org.bro.tubesoop2.item;

import org.bro.tubesoop2.animal.Animal;
import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.plant.Plant;

public class Destroy extends Item {
    @Override
    public void consumedBy(Creature creature) {
        if(hasProtectCard(creature)) return;
        if (creature instanceof Animal) {
        } else if (creature instanceof Plant) {
        }
    }
}