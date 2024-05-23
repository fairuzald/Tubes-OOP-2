package org.bro.tubesoop2.state;

import org.bro.tubesoop2.item.Item;
import org.bro.tubesoop2.player.Player;
import org.bro.tubesoop2.resource.Resource;
import org.bro.tubesoop2.resource.ResourceFactory;
import org.bro.tubesoop2.toko.Toko;

public class GameState {
    Player player1 = new Player();
    Player player2 = new Player();

    Toko toko;

    int turn = 1; // Asumsi turn 1 dan 2 instead of 0 dan 1
    public void NextTurn(){
        turn++; if(turn == 3) turn = 1;
    }
    public Player getCurrentPlayer(){
        if(turn == 1) return player1;
        else return player2;
    }

    public void setTurn(int turn){this.turn = turn;}
    public int getTurn(){return turn;}

    public void setToko(Toko toko){this.toko = toko;}

    ResourceFactory factory = new ResourceFactory();
    public Resource createResource(String key){
        return factory.get(key);
    }
    public Item createItem(String key){
        return factory.getItem(key);
    }

    public Player getPlayer1(){return player1;}
    public Player getPlayer2(){return player2;}
    public Toko getToko(){return toko;}
}
