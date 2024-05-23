package org.bro.tubesoop2.state;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

public class StateLoader {

    String path;
    String player1FileName;
    String player2FileName;
    String gameStateFileName;
    StatePlugin plugin;

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


    ServiceLoader<StatePlugin> loader;
    public StateLoader setPluginFromJarPath(String jarPath) {
        ArrayList<String> pluginFiles = new ArrayList<>();
        pluginFiles.add(jarPath);

        List<URL> urls = pluginFiles.stream().map(each -> {
            try {
                return (new File(each)).toURI().toURL();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();

        URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[0]), ClassLoader.getSystemClassLoader());
        loader = ServiceLoader.load(StatePlugin.class, classLoader);
        System.out.println(jarPath);

        try{
            for (StatePlugin plugin : loader) {
                System.out.println(plugin);
                plugin.Load(null, null, null);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("=======");
            e.printStackTrace();
        }

        return this;
    }

    /**
     * Default value is "state/gamestate.txt"
     */
    public void initialize() {
        String gameStatePath = path + "/" + gameStateFileName;
        String player1Path   = path + "/" + player1FileName;
        String player2Path   = path + "/" + player2FileName;

        try {
            File gameStateFile = new File(gameStatePath);
            File player1File = new File(player1Path);
            File player2File = new File(player2Path);
            plugin.Load(gameStateFile, player1File, player2File);
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

}
