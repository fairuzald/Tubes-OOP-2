package org.bro.tubesoop2.player;

import org.bro.tubesoop2.grid.Grid;
import org.bro.tubesoop2.resource.Resource;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private Grid<Resource> ladang;
    private List<Resource> activeDeck;
    private List<Resource> deck;

    public Player(String name) {
        this.name = name;
        ladang = new Grid<>();
        activeDeck = new ArrayList<>();
        deck = new ArrayList<>();
    }

    public Player(String name, Grid<Resource> ladang, List<Resource> activeDeck, List<Resource> deck) {
        this.name = name;
        this.ladang = ladang;
        this.activeDeck = activeDeck;
        this.deck = deck;
    }

    public String getName() {
        return name;
    }

    public Grid<Resource> getLadang() {
        return ladang;
    }

    public List<Resource> getActiveDeck() {
        return activeDeck;
    }

    public List<Resource> getDeck() {
        return deck;
    }
}
