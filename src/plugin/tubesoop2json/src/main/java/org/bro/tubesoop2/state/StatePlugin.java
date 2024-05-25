package org.bro.tubesoop2.state;

import java.io.File;
import java.io.FileWriter;

public interface StatePlugin {
    void Load(String gameStateFile, String player1File, String player2File, GameState state) throws Exception;
    void Save(String gameStateFile, String player1File, String player2File, GameState state) throws Exception;
}
