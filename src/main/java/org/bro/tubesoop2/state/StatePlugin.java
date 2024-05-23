package org.bro.tubesoop2.state;

import java.io.File;
import java.io.FileWriter;

public interface StatePlugin {
    void Load(File gameStateFile, File player1File, File player2File, GameState state) throws Exception;
    void Save(FileWriter gameStateFile, FileWriter player1File, FileWriter player2File, GameState state) throws Exception;
}
