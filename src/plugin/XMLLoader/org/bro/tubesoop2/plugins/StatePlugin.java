import java.io.File;

public interface StatePlugin {
    void Load(File gameStateFile, File player1File, File player2File) throws Exception;
}
