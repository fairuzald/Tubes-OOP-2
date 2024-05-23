package org.bro.tubesoop2.plant;

import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.item.Item;

public class Plant extends Creature {
    private Integer durationToHarvest;
    private Integer currentAge;

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

    @Override
    public void consumeItem(Item item) {
        item.consumedBy(this);
    }
}
