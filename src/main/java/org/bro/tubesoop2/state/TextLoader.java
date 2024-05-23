package org.bro.tubesoop2.state;

import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.grid.Location;
import org.bro.tubesoop2.item.Item;
import org.bro.tubesoop2.player.Player;
import org.bro.tubesoop2.quantifiable.Quantifiable;
import org.bro.tubesoop2.resource.Resource;
import org.bro.tubesoop2.toko.Toko;

import java.io.File;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TextLoader implements StatePlugin {
    GameState state;
    @Override
    public void Load(File gameStateFile, File player1File, File player2File, GameState state) throws Exception {
        this.state = state;
        try{
            // GameState
            loadGame(gameStateFile);
            loadPlayer(player1File, state.getPlayer1());
            loadPlayer(player2File, state.getPlayer2());

        } catch (NoSuchElementException e) {
            System.out.println("No more lines.");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    void loadPlayer(File playerFile, Player player) throws Exception {
        Scanner scanner = new Scanner(playerFile);
        int guldenAmount = Integer.parseInt(scanner.nextLine());
        System.out.println(guldenAmount);
        player.setGulden(guldenAmount);

        int deckAmount = Integer.parseInt(scanner.nextLine());
        System.out.println(deckAmount);
        player.setGulden((deckAmount));

        int deckActiveAmount = Integer.parseInt(scanner.nextLine());
        System.out.println(deckActiveAmount);
        for(int i = 0; i < deckActiveAmount; i++) {
            String[] split = scanner.nextLine().split(" ");
            String location = split[0];
            String typeName = split[1];
            // Debug
            System.out.println(location + " " + typeName);
            player.insertDeck(state.createResource(typeName), new Location(location));
        }

        int ladangCardAmount = Integer.parseInt(scanner.nextLine());
        System.out.println(ladangCardAmount);
        for(int i = 0; i < ladangCardAmount; i++) {
            String[] split = scanner.nextLine().split(" ");
            String location = split[0];
            String typeName = split[1];
            int umurORBerat = Integer.parseInt(split[2]);
            int activeItemAmount = Integer.parseInt(split[3]);

            // Debug
            System.out.print(location + " " + typeName + " " + umurORBerat + " " + activeItemAmount);
            Creature c = (Creature) state.createResource(typeName);
            c.setUmurOrBerat(umurORBerat);
            player.addLadang(state.createResource(typeName), new Location(location));

            for(int j = 0; j < activeItemAmount; j++){
                String itemTypeName = split[4+j];
                System.out.print(" "+itemTypeName);
                c.addItem((Item) state.createResource(typeName));
            }

            // Debug
            System.out.println("");
        }
        scanner.close();
    }

    void loadGame(File gameStateFile) throws Exception{
        Scanner scanner = new Scanner(gameStateFile);

        int turn = 0;
        try{ turn = Integer.parseInt(scanner.nextLine()); }
        catch(NumberFormatException e){
            throw new NumberFormatException("Turn in gamestate.txt isn't an integer.");
        }
        System.out.println(turn);
        state.setTurn(turn);

        int itemAmount = 0;
        try{ itemAmount = Integer.parseInt(scanner.nextLine()); }
        catch(NumberFormatException e){
            throw new NumberFormatException("Item Amount in gamestate.txt isn't an integer.");
        }
        System.out.println(itemAmount);
        ArrayList<Quantifiable<Resource>> items = new ArrayList<Quantifiable<Resource>>();

        for(int i = 0; i < itemAmount; i++){
            String data = scanner.nextLine();
            String[] split = data.split(" ");
            if(split.length != 2){
                throw new Exception("Line "+i+" doesn't contain 2 values");
            }


            int count = 0;
            try{ count = Integer.parseInt(split[1]); }
            catch(NumberFormatException e){
                throw new NumberFormatException("Item Amount for " + split[1] + " isn't an integer.");
            }

            String typeName = split[0];
            System.out.println(typeName + " " + count);
            Resource r = state.createResource(typeName);
            Quantifiable<Resource> qr = new Quantifiable<>(r, count);

            items.add(qr);
        }
        state.setToko(new Toko(items));
        scanner.close();
    }


}
