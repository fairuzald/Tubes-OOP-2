package org.bro.tubesoop2.state;

import java.io.File;

public interface StatePlugin {
    void Load(File gameStateFile, File player1File, File player2File, GameState state) throws Exception;
}
