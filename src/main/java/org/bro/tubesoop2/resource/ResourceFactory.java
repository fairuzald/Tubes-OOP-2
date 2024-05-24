package org.bro.tubesoop2.resource;
import org.bro.tubesoop2.animal.Carnivore;
import org.bro.tubesoop2.animal.Herbivore;
import org.bro.tubesoop2.animal.Omnivore;
import org.bro.tubesoop2.item.*;
import org.bro.tubesoop2.plant.Plant;
import org.bro.tubesoop2.product.Product;
import org.bro.tubesoop2.product.ProductAnimal;
import org.bro.tubesoop2.product.ProductPlant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;


public class ResourceFactory {
    Map<String, Supplier<Resource>> resourceMap = new HashMap<>();

    public ResourceFactory(){
        // Product
        resourceMap.put("SIRIP_HIU", () -> new ProductAnimal("SIRIP_HIU", 12, 500));
        resourceMap.put("SUSU"     , () -> new ProductAnimal("SUSU", 4, 100));
        resourceMap.put("DAGING_DOMBA", () -> new ProductAnimal("DAGING_DOMBA", 6, 120));
        resourceMap.put("DAGING_KUDA", () -> new ProductAnimal("DAGING_KUDA", 8, 150));
        resourceMap.put("TELUR", () -> new ProductAnimal("TELUR", 2, 50));
        resourceMap.put("DAGING_BERUANG", () -> new ProductAnimal("DAGING_BERUANG", 12, 500));
        resourceMap.put("JAGUNG", () -> new ProductPlant("JAGUNG", 3, 150));
        resourceMap.put("LABU", () -> new ProductPlant("LABU", 10, 500));
        resourceMap.put("STROBERI", () -> new ProductPlant("STROBERI", 5, 350));

        // Animal
        resourceMap.put("HIU_DARAT", () -> new Carnivore("HIU_DARAT", 25, (Product)get("SIRIP_HIRU") ));
        resourceMap.put("SAPI", () -> new Herbivore("SAPI", 10, (Product)get("SUSU") ));
        resourceMap.put("DOMBA", () -> new Herbivore("DOMBA", 12, (Product)get("DAGING_DOMBA") ));
        resourceMap.put("KUDA", () -> new Herbivore("KUDA", 14, (Product)get("DAGING_KUDA") ));
        resourceMap.put("AYAM", () -> new Omnivore("AYAM", 5, (Product)get("TELUR") ));
        resourceMap.put("BERUANG", () -> new Omnivore("BERUANG", 25, (Product)get("DAGING_BERUANG") ));

        // Tanaman
        resourceMap.put("BIJI_LABU", () -> new Plant(3, "BIJI_LABU", (Product)get("LABU") ));
        resourceMap.put("BIJI_JAGUNG", () -> new Plant(5, "BIJI_JAGUNG", (Product)get("JAGUNG") ));
        resourceMap.put("BIJI_STROBERI", () -> new Plant(4, "BIJI_STROBERI", (Product)get("STROBERI") ));

        resourceMap.put("ACCELERATE", Accelerate::new);
        resourceMap.put("DELAY", Delay::new);
        resourceMap.put("INSTANT_HARVEST", InstantHarvest::new);
        resourceMap.put("DESTROY", Destroy::new);
        resourceMap.put("PROTECT", Protect::new);
        resourceMap.put("TRAP", Trap::new);

    }

    public Resource get(String key){
        Supplier<Resource> s = resourceMap.get(key);
        return s.get();
    }

    public Set<String> getAllResourceKeys() {
        return resourceMap.keySet();
    }
}
