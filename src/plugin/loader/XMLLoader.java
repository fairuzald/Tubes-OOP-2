package org.bro.tubesoop2.plugins;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class XMLLoader implements StatePlugin {

    @Override
    public void Load(File gameStateFile, File player1File, File player2File) throws Exception {
        System.out.println("Reflection works with xml lezgooo");
    }
}
