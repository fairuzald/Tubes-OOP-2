package org.bro.tubesoop2.state;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TextLoader implements StatePlugin {

    @Override
    public void Load(File gameStateFile, File player1File, File player2File) throws Exception {
        try{
            // GameState
            loadGame(gameStateFile);
            loadPlayer(player1File);
            loadPlayer(player2File);

        } catch (NoSuchElementException e) {
            System.out.println("No more lines.");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    void loadPlayer(File playerFile) throws Exception {
        Scanner scanner = new Scanner(playerFile);
        int guldenAmount = Integer.parseInt(scanner.nextLine());
        System.out.println(guldenAmount);

        int deckAmount = Integer.parseInt(scanner.nextLine());
        System.out.println(deckAmount);

        int deckActiveAmount = Integer.parseInt(scanner.nextLine());
        System.out.println(deckActiveAmount);
        for(int i = 0; i < deckActiveAmount; i++) {
            String[] split = scanner.nextLine().split(" ");
            String location = split[0];
            String typeName = split[1];
            // Debug
            System.out.println(location + " " + typeName);
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

            for(int j = 0; j < activeItemAmount; j++){
                String itemTypeName = split[4+j];
                System.out.print(" "+itemTypeName);
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

        int itemAmount = 0;
        try{ itemAmount = Integer.parseInt(scanner.nextLine()); }
        catch(NumberFormatException e){
            throw new NumberFormatException("Item Amount in gamestate.txt isn't an integer.");
        }
        System.out.println(itemAmount);

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
        }
        scanner.close();
    }
}
