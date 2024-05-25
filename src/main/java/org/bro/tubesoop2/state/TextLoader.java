package org.bro.tubesoop2.state;

import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.grid.Location;
import org.bro.tubesoop2.item.Item;
import org.bro.tubesoop2.player.Player;
import org.bro.tubesoop2.quantifiable.Quantifiable;
import org.bro.tubesoop2.resource.Resource;
import org.bro.tubesoop2.toko.Toko;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TextLoader implements StatePlugin {
    GameState state;

    @Override
    public String getName() {
        return "txt";
    }

    @Override
    public void Load(String gameStatePath, String player1Path, String player2Path, GameState state) throws Exception {
        File gameStateFile = new File(gameStatePath);
        File player1File = new File(player1Path);
        File player2File = new File(player2Path);
        
        this.state = state;
        try{
            // GameState
            loadGame(gameStateFile);
            loadPlayer(player1File, state.getPlayer1());
            loadPlayer(player2File, state.getPlayer2());

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void Save(String gameStatePath, String player1Path, String player2Path, GameState state) throws Exception {
        FileWriter gameStateFile = new FileWriter(gameStatePath);
        FileWriter player1File = new FileWriter(player1Path);
        FileWriter player2File = new FileWriter(player2Path);
        this.state = state;
        try{
            // GameState
            saveGame(gameStateFile);
            savePlayer(player1File, state.getPlayer1());
            savePlayer(player2File, state.getPlayer2());

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
        player.setDeckLeft(deckAmount);
        // set active deck amount

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
            player.putLadang(c, new Location(location));

            for(int j = 0; j < activeItemAmount; j++){
                String itemTypeName = split[4+j];
                System.out.print(" "+itemTypeName);
                c.addItem((Item)state.createResource(itemTypeName));
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
        state.getToko().clearAndRepopulateItems(items);
        scanner.close();
    }





    void savePlayer(FileWriter f, Player p) throws Exception {
        int activeDeckCount = p.getActiveDeckCount();

        System.out.println(p.getGulden());
        System.out.println(p.getDeckLeft());
        System.out.println(activeDeckCount);
        f.write(p.getGulden()+"\n");
        f.write(p.getDeckLeft()+"\n");
        f.write(activeDeckCount+"\n");


        for(int i = 0; i < p.getActiveDeck().size(); i++){
            Resource r = p.getActiveDeck().get(i);
            if(r != null){
                Location loc = new Location(i, 0);
                System.out.println(loc.toString() + " " + r.getName());
                f.write(loc.toString() + " " + r.getName() + "\n");
            }
        }
        System.out.println(p.getLadang().getCountFilled());
        f.write(p.getLadang().getCountFilled() + "\n");
        p.getLadang().forEachActive(l -> {
            try{
                Creature c = (Creature) p.getLadang().getElement(l);
                System.out.print(l.toString() + " " + c.getName() + " " + c.getUmurOrBerat() + " " + c.getItemsActive().size());
                f.write(l.toString() + " " + c.getName() + " " + c.getUmurOrBerat() + " " + c.getItemsActive().size());

                for(Item item : c.getItemsActive()){
                    System.out.print(" "+item.getClass().getSimpleName().toUpperCase());
                    String className = item.getClass().getSimpleName().toUpperCase();
                    f.write(" "+className);
                }
                System.out.println("\n");
                f.write("\n");

            } catch (Exception e){}

        });

        f.close();
    }

    void saveGame(FileWriter f) throws Exception{
        System.out.println(state.getTurn());
        f.write(state.getTurn() + "\n");

        System.out.println(state.getToko().getStock().size());
        f.write(state.getToko().getStock().size() + "\n");
        for(Quantifiable<Resource> item : state.getToko().getStock()){
            System.out.print(item.getValue().getName() + " " + item.getQuantity() + "\n");
            f.write(item.getValue().getName() + " " + item.getQuantity() + "\n");
        }
        f.close();
    }

}
