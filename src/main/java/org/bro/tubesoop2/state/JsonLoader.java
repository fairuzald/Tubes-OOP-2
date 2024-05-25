package org.bro.tubesoop2.state;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.grid.Location;
import org.bro.tubesoop2.item.Item;
import org.bro.tubesoop2.player.Player;
import org.bro.tubesoop2.quantifiable.Quantifiable;
import org.bro.tubesoop2.resource.Resource;
import org.bro.tubesoop2.toko.Toko;

public class JsonLoader implements StatePlugin {
    public GameState state = new GameState();
    @Override
    public void Load(File gameStateFile, File player1File, File player2File, GameState state) throws Exception {
        Player newPlayer = new Player("Nigs");
        loadGame(gameStateFile);
        loadPlayer(player1File,newPlayer);

    }

    public void Save(FileWriter gameStateFile, FileWriter player1File, FileWriter player2File, GameState state) throws Exception{
    }

    public void loadPlayer(File playerFile, Player player) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(playerFile);
    
        int guldenAmount = rootNode.get("jumlahGulden").asInt();
        System.out.println("Gulden Amount: " + guldenAmount);
        player.setGulden(guldenAmount);
    
        int deckAmount = rootNode.get("jumlahDeck").asInt();
        System.out.println("Deck Amount: " + deckAmount);
        player.setDeckLeft(deckAmount);
    
        int deckActiveAmount = rootNode.get("jumlahDeckAktif").asInt();
        System.out.println("Active Deck Amount: " + deckActiveAmount);
        JsonNode deckAktifNode = rootNode.get("deckAktif");
        for (JsonNode node : deckAktifNode) {
            String location = node.get("lokasiKartu").asText();
            String typeName = node.get("kartuDeckAktif").asText();
            System.out.println("Location: " + location + ", Type Name: " + typeName);
            player.insertDeck(state.createResource(typeName), new Location(location));
        }
    
        int ladangCardAmount = rootNode.get("jumlahKartuLadang").asInt();
        System.out.println("Ladang Card Amount: " + ladangCardAmount);
        JsonNode ladangNode = rootNode.get("ladang");
        for (JsonNode node : ladangNode) {
            String location = node.get("lokasiKartu").asText();
            String typeName = node.get("kartuLadang").asText();
            int umurORBerat = node.get("umurBerat").asInt();
            int activeItemAmount = node.get("jumlahItemAktif").asInt();
            System.out.print("Location: " + location + ", Type Name: " + typeName + ", Age/Weight: " + umurORBerat + ", Active Item Amount: " + activeItemAmount);
            Creature c = (Creature) state.createResource(typeName);
            c.setUmurOrBerat(umurORBerat);
            player.putLadang(c, new Location(location));
    
            JsonNode itemsNode = node.get("items");
            for (JsonNode itemNode : itemsNode) {
                String itemTypeName = itemNode.asText();
                System.out.print(" " + itemTypeName);
                c.addItem((Item)state.createResource(itemTypeName));
            }
    
            System.out.println("");
        }
    }
    public void loadGame(File gameStateFile) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(gameStateFile);

        int turn = rootNode.get("currentTurn").asInt();

        System.out.println(turn);
         state.setTurn(turn);

        JsonNode shopNode = rootNode.get("shop");
        int itemAmount = shopNode.get("jumlahItemDiShop").asInt();
        System.out.println(itemAmount);

        ArrayList<Quantifiable<Resource>> items = new ArrayList<>();
        JsonNode itemsNode = shopNode.get("items");
        for (JsonNode itemNode : itemsNode) {
            String typeName = itemNode.get("name").asText();
            int count = itemNode.get("quantity").asInt();

            System.out.println(typeName + " " + count);
            Resource r = state.createResource(typeName);
            Quantifiable<Resource> qr = new Quantifiable<>(r, count);

            items.add(qr);
        }
         state.getToko().clearAndRepopulateItems(items);
    }
    public static void main(String[] args) throws Exception {
        File gamestatefile = new File("C:\\ITB\\Semester_4\\OOP\\src\\main\\java\\org\\bro\\tubesoop2\\state\\gamestatejson.json");
        File player1Filer = new File("C:\\ITB\\Semester_4\\OOP\\src\\main\\java\\org\\bro\\tubesoop2\\state\\player1.json");

        JsonLoader jsonLoader = new JsonLoader();
        jsonLoader.Load(gamestatefile, player1Filer, null, new GameState());
    }

}
