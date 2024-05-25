package org.bro.tubesoop2.state;

import org.bro.tubesoop2.creature.Creature;
import org.bro.tubesoop2.item.Item;
import org.bro.tubesoop2.player.Player;
import org.bro.tubesoop2.quantifiable.Quantifiable;
import org.bro.tubesoop2.resource.Resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class StateLoader {

    String path;
    String player1FileName;
    String player2FileName;
    String gameStateFileName;
    StatePlugin plugin;

    List<String> pluginFiles = new ArrayList<String>();
    ServiceLoader<StatePlugin> serviceLoader;

    public StateLoader setPath(String parentFolder, String gameStateFileName, String player1FileName, String player2FileName) {
        this.path = parentFolder;
        this.player1FileName = player1FileName;
        this.player2FileName = player2FileName;
        this.gameStateFileName = gameStateFileName;
        return this;
    }

    public StateLoader setPlugin(StatePlugin plugin){
        this.plugin = plugin;
        return this;
    }


    public StateLoader setPluginFromJarPath(String jarPath) {
        pluginFiles.add(jarPath);
        loadPluginService();
        test();
        return this;
    }

    public StateLoader addPluginFromJarPath(String jarPath) {
        pluginFiles.add(jarPath);
        loadPluginService();
        return this;
    }




    public void test() {
        try {
            System.out.println("test");
            for(StatePlugin p : serviceLoader) {
                p.Load(gameStateFileName, player1FileName, player2FileName, null);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void loadPluginService() {
        List<URL> urls = this.pluginFiles.stream().map(each -> {
            try {
                System.out.println("each");
                System.out.println(each);
                System.out.println((new File(each)).toURI().toURL());
                return (new File(each)).toURI().toURL(); }
            catch (Exception e) { throw new RuntimeException(e); }
        }).toList();

        URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[0]), ClassLoader.getSystemClassLoader());
        this.serviceLoader = ServiceLoader.load(StatePlugin.class, classLoader);
    }




    /**
     * Default value is "state/gamestate.txt"
     */
    public void loadState(GameState state) {
        if(state == null) {
            System.out.println("State is null, please instantiate it first.");
            return;
        }
        state.clear();
        String gameStatePath = path + "/" + gameStateFileName;
        String player1Path   = path + "/" + player1FileName;
        String player2Path   = path + "/" + player2FileName;

        try {
            plugin.Load(gameStatePath, player1Path, player2Path, state);
        } catch (FileNotFoundException e) {
            System.out.println("Error when reading state/gamestate.txt.");
            System.out.println(e.getMessage());
            // e.printStackTrace();
        } catch (Exception e){
            System.out.println("Error when reading state/gamestate.txt.");
            System.out.println(e.getMessage());
            // e.printStackTrace();
        }
    }


    public void saveState(GameState state) {
        String gameStatePath = path + "/" + gameStateFileName;
        String player1Path   = path + "/" + player1FileName;
        String player2Path   = path + "/" + player2FileName;

        try {
            plugin.Save(gameStatePath, player1Path, player2Path, state);
        } catch (FileNotFoundException e) {
            System.out.println("Error when reading state/gamestate.txt.");
            System.out.println(e.getMessage());
            // e.printStackTrace();
        } catch (Exception e){
            System.out.println("Error when reading state/gamestate.txt.");
            System.out.println(e.getMessage());
            // e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StateLoader loader = new StateLoader();
        GameState state = new GameState();
        loader.setPath("state", "gamestate.txt", "player1.txt", "player2.txt")
                .setPlugin(new TextLoader())
//                .setPluginFromJarPath("src/plugin/jar/JsonLoader.jar")
                .loadState(state);

        // gamestate
        System.out.println("============= [ Result ] ==============");
        System.out.println(state.getTurn());
        System.out.println(state.getToko().getStock().size());
        for(Quantifiable<Resource> item : state.getToko().getStock()){
            System.out.print(item.getValue().getName() + " " + item.getQuantity() + "\n");
        }

        // player
        Player p1 = state.getPlayer1();
        debugPlayer(p1);
        Player p2 = state.getPlayer2();
        debugPlayer(p2);

    }
    private static void debugPlayer(Player p){
        System.out.println(p.getGulden());
        System.out.println(p.getDeckLeft());
        System.out.println(p.getActiveDeck().size());
        for(int i = 0; i < p.getActiveDeck().size(); i++){
            Resource r = p.getActiveDeck().get(i);
            if(r != null){
                System.out.println(i + " " + r.getName());
            }
        }
        System.out.println(p.getLadang().getCountFilled());
        p.getLadang().forEachActive(l -> {
            Creature c = (Creature) p.getLadang().getElement(l);
            System.out.print(c.getName() + " " + c.getUmurOrBerat() + " " + c.getItemsActive().size());
            for(Item item : c.getItemsActive()){
                System.out.print(" "+item.getClass().getName().toString());
            }

            System.out.println("");
        });
    }

}
