package org.bro.tubesoop2.toko;

import org.bro.tubesoop2.product.Product;
import org.bro.tubesoop2.quantifiable.Quantifiable;
import org.bro.tubesoop2.resource.Resource;
import org.bro.tubesoop2.player.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Toko {
    private ArrayList<Quantifiable<Resource>> stock;

    public Toko(ArrayList<Quantifiable<Resource>> stock) {
        this.stock = stock;
    }
    public Toko() {
        this.stock = new ArrayList<Quantifiable<Resource>>();
    }

    public Toko(Toko other) {
        this.stock = new ArrayList<>(other.stock);
    }

    public Quantifiable<Resource> get(int idx){
        return stock.get(idx);
    }

    public ArrayList<Quantifiable<Resource>> getStock() {
        return stock;
    }

    
    public void clearAndRepopulateItems(ArrayList<Quantifiable<Resource>> newStock){
        stock.clear();
        for(Quantifiable<Resource> item : newStock){
            this.addItem(item);
        }
    }

    public int getItemIndex(Quantifiable<Resource> otherQuant) throws ItemShopNotFoundException {
        for (int i = 0; i < stock.size(); i++) {
            if (stock.get(i).getValue().getName().equals(otherQuant.getValue().getName())) {
                return i;
            }
        }
        throw new ItemShopNotFoundException();
    }

    public int getItemIndex(Resource otherRsc) throws ItemShopNotFoundException {
        for (int i = 0; i < stock.size(); i++) {
            if (stock.get(i).getValue().getName().equals(otherRsc.getName())) {
                return i;
            }
        }
        throw new ItemShopNotFoundException();
    }

    public void removeItem(Quantifiable<Resource> otherQuant) {
        try {
            int idx = getItemIndex(otherQuant);
            stock.remove(idx);
        } catch (ItemShopNotFoundException err) {
            System.out.println(err.getMessage());
        }
    }

    public void addItem(Quantifiable<Resource> otherQuant) {
        try {
            int idx = getItemIndex(otherQuant);
            stock.get(idx).incrementQuantity(otherQuant.getQuantity());
        } catch (ItemShopNotFoundException e) {
            stock.add(otherQuant);
        }
    }

    public void addItem(Resource otherrsc) {
        try {
            int idx = getItemIndex(otherrsc);
            stock.get(idx).incrementQuantity(1);
        } catch (ItemShopNotFoundException e) {
            stock.add(new Quantifiable<>(otherrsc,1));
        }
    }

    public int getStockCount(Player pl, Quantifiable<Resource> r){
        int count = 0;
        for (Resource rsc:pl.getActiveDeck()){
            if(rsc.getName().equals(r.getValue().getName())){
                count++;
            }
        }
        return count;
    }

    public int getStockCount(Player pl, Resource r){
        int count = 0;
        for (Resource rsc:pl.getActiveDeck()){
            if(rsc!=null && Objects.equals(rsc.getName(), r.getName())){
                count++;
            }
        }
        return count;
    }

    public void buy(Player pl, int idxItem, int quantity) throws TokoException, Exception {
        if (idxItem < 0 || idxItem >= stock.size()) {
            throw new BeliOutOfRange();
        }
        if(quantity <= 0) {
            throw new InvalidKuantitas();
        }

        Quantifiable<Resource> itemShop = stock.get(idxItem);
        Integer price = ((Product) itemShop.getValue()).getPrice()*quantity;

        System.out.println("Buying: "+itemShop.getValue().getName()+" Amount: "+quantity);
        System.out.println("Current Stock: "+itemShop.getQuantity());

        if(itemShop.getValue() instanceof Product){
            if (itemShop.getQuantity() < quantity) {
                throw new StockTidakCukupShopException();
            }

            if(pl.getGulden()<price){
                throw new Exception("Uang tidak cukup");
            }

            if (pl.getActiveDeckCount() + quantity > 6) {
                throw new PenyimpananTidakCukup();
            }
        }

        for(int i=0;i<quantity;i++){
            pl.addToDeck(itemShop.getValue());
        }
        itemShop.decrementQuantity(quantity);
        pl.setGulden(pl.getGulden()-price);
    }


    public void sell(Player pl, Resource rsc, int quantity) throws TokoException {
        System.out.println("Sedang menjual: "+rsc.getName()+" sebanyak "+quantity);
        int stockCount = getStockCount(pl, rsc);
        if (stockCount - quantity < 0) {
            throw new StockTidakCukupPlayer();
        }
        if(quantity <= 0){
            throw new InvalidKuantitas();
        }
        int price = ((Product) rsc).getPrice()*quantity;

        int sold = 0;
        List<Resource> deck = new ArrayList<>(pl.getActiveDeck());
        for (Resource r: deck) {
            if(r ==null){
                continue;
            }
            if(r.getName().equals(rsc.getName())){
                pl.getActiveDeck().remove(r);
                sold++;
                if(sold >= quantity) {
                    break;
                }
            }
        }

        this.addItem(new Quantifiable<>(rsc,quantity));

        pl.setGulden(pl.getGulden()+price);
    }


    public void clear(){
        stock.clear();
    }
}
