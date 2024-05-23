package org.bro.tubesoop2.resource;
import org.bro.tubesoop2.animal.Carnivore;
import org.bro.tubesoop2.animal.Herbivore;
import org.bro.tubesoop2.animal.Omnivore;
import org.bro.tubesoop2.item.*;
import org.bro.tubesoop2.plant.Plant;
import org.bro.tubesoop2.product.ProductAnimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public class ResourceFactory {
    Map<String, Supplier<Resource>> resourceMap = new HashMap<>();
    Map<String, Supplier<Item>> itemMap = new HashMap<>();

    public ResourceFactory(){
        // Product
        resourceMap.put("SIRIP_HIU", () -> new ProductAnimal("SIRIP_HIU", 12, 500));
        resourceMap.put("SUSU"     , () -> new ProductAnimal("SUSU", 4, 100));
        resourceMap.put("DAGING_DOMBA", () -> new ProductAnimal("DAGING_DOMBA", 6, 120));
        resourceMap.put("DAGING_KUDA", () -> new ProductAnimal("DAGING_KUDA", 8, 150));
        resourceMap.put("TELUR", () -> new ProductAnimal("TELUR", 2, 50));
        resourceMap.put("DAGING_BERUANG", () -> new ProductAnimal("DAGING_BERUANG", 12, 500));
        resourceMap.put("JAGUNG", () -> new ProductAnimal("JAGUNG", 3, 150));
        resourceMap.put("LABU", () -> new ProductAnimal("LABU", 10, 500));
        resourceMap.put("STROBERI", () -> new ProductAnimal("STROBERI", 5, 350));

        // Animal
        resourceMap.put("HIU_DARAT", () -> new Carnivore("HIU_DARAT", 25, null));
        resourceMap.put("SAPI", () -> new Herbivore("SAPI", 10, null));
        resourceMap.put("DOMBA", () -> new Herbivore("DOMBA", 12, null));
        resourceMap.put("KUDA", () -> new Herbivore("KUDA", 14, null));
        resourceMap.put("AYAM", () -> new Omnivore("AYAM", 5, null));
        resourceMap.put("BERUANG", () -> new Omnivore("BERUANG", 25, null));

        // Tanaman
        resourceMap.put("BIJI_LABU", () -> new Plant(3, "BIJI_LABU", null));
        resourceMap.put("BIJI_JAGUNG", () -> new Plant(5, "BIJI_JAGUNG", null));
        resourceMap.put("BIJI_STROBERI", () -> new Plant(4, "BIJI_STROBERI", null));

        // Item
        itemMap.put("ACCELERATE", Accelerate::new);
        itemMap.put("DELAY", Delay::new);
        itemMap.put("INSTANT_HARVEST", InstantHarvest::new);
        itemMap.put("DESTROY", Destroy::new);
        itemMap.put("PROTECT", Protect::new);
        itemMap.put("TRAP", Trap::new);

    }

    public Resource get(String key){
        Supplier<Resource> s = resourceMap.get(key);
        return s.get();
    }

    public Item getItem(String key){
        Supplier<Item> s = itemMap.get(key);
        return s.get();
    }

}
