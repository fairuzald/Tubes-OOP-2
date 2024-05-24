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
    private int gulden = 0;
    private int deckLeft = 40;

    public Player() {
        ladang = new Grid<>(4, 5);
        deck = new ArrayList<>(40);
        activeDeck = new ArrayList<>(6);

        // prefill
        for (int i = 0; i < 40; i++) {
            deck.add(null);
        }
        for (int i = 0; i < 6; i++) {
            activeDeck.add(null);
        }
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

    public int getDeckLeft(){return deckLeft;}

    public void setGulden(int gulden){this.gulden = gulden;}
    public int getGulden(){return gulden;}

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

        // prefill
        for (int i = 0; i < 40; i++) {
            deck.add(null);
        }
        for (int i = 0; i < 6; i++) {
            activeDeck.add(null);
        }
    }
}
