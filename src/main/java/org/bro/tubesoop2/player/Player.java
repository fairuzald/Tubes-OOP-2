package org.bro.tubesoop2.player;

import org.bro.tubesoop2.grid.Grid;
import org.bro.tubesoop2.grid.Location;
import org.bro.tubesoop2.resource.Resource;

import java.util.ArrayList;
import java.util.List;

public class Player {

    // Only used when printing who wins
    private String name;
    public String getName(){return name;}

    private Grid<Resource> ladang;
    private List<Resource> activeDeck;
    private Integer gulden = 0;
    private int deckLeft = 40;

    static final int MAX_ACTIVE_DECK = 6;
    static final int MAX_DECK_LEFT = 40;

    public Player(String name) {
        this.name = name;
        ladang = new Grid<>();
        activeDeck = new ArrayList<>(MAX_ACTIVE_DECK);

        // prefill
        for (int i = 0; i < 6; i++) {
            activeDeck.add(null);
        }
    }

    public Grid<Resource> getLadang() {
        return ladang;
    }
    public List<Resource> getActiveDeck() {
        return activeDeck;
    }
    public int getActiveDeckCount() {
        int count = 0;
        for (Resource r : activeDeck) {
            if (r != null) {
                count++;
            }
        }
        return count;
    }

    public void compactActiveDeck() {
        List<Resource> activeDeckCopy = new ArrayList<>();

        for (int i = 0; i < activeDeck.size(); i++) {
            if (activeDeck.get(i) != null) {
                activeDeckCopy.add(activeDeck.get(i));
            }
        }

        activeDeck = activeDeckCopy;
        System.out.println(activeDeck);
    }

    public int getDeckLeft(){return deckLeft;}

    public void setGulden(Integer gulden){this.gulden = gulden;}
    public Integer getGulden(){return gulden;}

    public void insertDeck(Resource r, Location l){
        activeDeck.add(l.getCol(), r);
    }

    public void putLadang(Resource r, Location l){
        ladang.put(l, r);
    }

    public void removeLadang(Location l){
        ladang.pop(l.getRow(), l.getCol());
    }

    public void addToDeck(Resource resource) {
        // find first empty slot
        for (int i = 0; i < activeDeck.size(); i++) {
            if (activeDeck.get(i) == null) {
                activeDeck.set(i, resource);
                return;
            }
        }
        throw new IllegalStateException("Deck is full");
    }

    public int countFreeSlotFromActiveDeck() {
        int count = 0;
        for (int i = 0; i < 6; i++) {
            if (activeDeck.get(i) != null) {
                count++;
            }
        }

        return count;
    }


    public void setDeckLeft(int deckLeft){
        this.deckLeft = deckLeft;
    }

    public void clear(){
        ladang.clear();
        activeDeck.clear();
        gulden = 0;
        deckLeft = 40;
        // prefill
        for (int i = 0; i < 6; i++) {
            activeDeck.add(null);
        }
    }

    public boolean isActiveDeckFull(){
        for(int i = 0; i < activeDeck.size(); i++){
            if(activeDeck.get(i) == null){
                return false;
            }
        }
        return true;
    }
}
