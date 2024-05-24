package org.bro.tubesoop2.player;

import org.bro.tubesoop2.grid.Grid;
import org.bro.tubesoop2.grid.Location;
import org.bro.tubesoop2.resource.Resource;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private Grid<Resource> ladang;
    private List<Resource> deck;
    private List<Resource> activeDeck;
    private Integer gulden = 0;
    private int deckLeft = 40;

    public Player() {
        ladang = new Grid<>(5, 4);
        deck = new ArrayList<>(40);
        activeDeck = new ArrayList<>();
    }

    public Grid<Resource> getLadang() {
        return ladang;
    }

    public List<Resource> getDeck() {
        return deck;
    }

    public List<Resource> getActiveDeck() {
        return activeDeck;
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
        activeDeck.add(l.getRow(), r);
    }

    public void addLadang(Resource r, Location l){
        ladang.put(l, r);
    }

    public void addToDeck(Resource resource) {
        deck.add(resource);
    }


    public void setDeckLeft(int deckLeft){
        this.deckLeft = deckLeft;
    }

    public void clear(){
        ladang.clear();
        deck.clear();
        activeDeck.clear();
        gulden = 0;
        deckLeft = 40;
    }
}
