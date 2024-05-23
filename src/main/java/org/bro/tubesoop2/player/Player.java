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
    }

    public Player(Grid<Resource> ladang, List<Resource> activeDeck, List<Resource> deck) {
        this.ladang = ladang;
        this.deck = activeDeck;
        this.activeDeck = deck;
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
        activeDeck.add(l.getCol(), r);
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
}
