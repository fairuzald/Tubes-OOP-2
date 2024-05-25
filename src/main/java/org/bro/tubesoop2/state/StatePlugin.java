package org.bro.tubesoop2.state;

public interface StatePlugin {
    void Load(String gameStateFile, String player1File, String player2File, GameState state) throws Exception;
    void Save(String gameStateFile, String player1File, String player2File, GameState state) throws Exception;

    String getName();
}
