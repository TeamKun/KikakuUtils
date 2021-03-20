package net.kunmc.lab.kikakuutils.ask;

public class AskManager {
    public AskGameMode gameMode;
    public AbstractAsk[] askArray;

    public AskManager(){
        gameMode = new AskGameMode();

        askArray = new AbstractAsk[]{
                gameMode,
        };
    }
}
