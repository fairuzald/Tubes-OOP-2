package org.bro.tubesoop2.item;

import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.resource.Resource;

public abstract class Item extends Resource {
    public Item() {
        super("");
        // String before = (getClass().getSimpleName().toUpperCase());
        String res = getClass().getSimpleName().replaceAll("(.)(\\p{Upper})", "$1_$2").toUpperCase();
        setName(res);

    }

    public abstract void consumedBy(Creature creature);

    boolean hasProtectCard(Creature creature) {
        boolean hasProtectCard = false;
        for (Item item : creature.getItemsActive()) {
            if (item instanceof Protect) {
                hasProtectCard = true;
                break;
            }
        }
        return hasProtectCard; 
    }
}
