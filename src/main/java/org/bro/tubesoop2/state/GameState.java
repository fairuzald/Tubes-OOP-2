package org.bro.tubesoop2.state;

import org.bro.tubesoop2.item.Item;
import org.bro.tubesoop2.player.Player;
import org.bro.tubesoop2.quantifiable.Quantifiable;
import org.bro.tubesoop2.resource.Resource;
import org.bro.tubesoop2.resource.ResourceFactory;
import org.bro.tubesoop2.toko.Toko;

import java.util.*;

public class GameState {
    Player player1 = new Player();
    Player player2 = new Player();

    Toko toko = new Toko(new ArrayList<Quantifiable<Resource>>());

    int turn = 1; // Asumsi turn 1 dan 2 instead of 0 dan 1
    public void NextTurn(){
        turn++;
    }
    public Player getCurrentPlayer(){
        if(turn % 2 == 1) return player1;
        else return player2;
    }
    public Player getNextPlayer(){
        if(turn % 2 == 1) return player2;
        else return player1;
    }

    public void setTurn(int turn){this.turn = turn;}
    public int getTurn(){return turn;}

    public void setToko(Toko toko){this.toko = toko;}


    ResourceFactory factory = new ResourceFactory();
    public Resource createResource(String key){
        return factory.get(key);
    }

    public Player getPlayer1(){return player1;}
    public Player getPlayer2(){return player2;}
    public Toko getToko(){return toko;}

    public void populateDeckWithRandomResources(Player player, int count) {
        Set<String> resourceKeys = factory.getAllResourceKeys();
        List<String> resourceList = new ArrayList<>(resourceKeys);
        Collections.shuffle(resourceList);
        for (int i = 0; i < count; i++) {
            Random rand = new Random();
            int j = rand.nextInt(resourceList.size());
            String key = resourceList.get(j);
            Resource resource = factory.get(key);
            player.addToDeck(resource);
        }
    }

    public void populateBothPlayersDecks(int count) {
        populateDeckWithRandomResources(player1, count);
        populateDeckWithRandomResources(player2, count);
    }

    public void clear(){
        player1.clear();
        player2.clear();
        toko.clear();
    }

    public static void main(String[] args) {
        Player p1 =  new Player();
        GameState newGameState = new GameState();
        newGameState.populateDeckWithRandomResources(p1, 40);
        System.out.println(p1.getActiveDeck().size());
    }
}

