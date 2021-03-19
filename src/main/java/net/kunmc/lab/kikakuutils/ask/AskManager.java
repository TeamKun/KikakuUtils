package net.kunmc.lab.kikakuutils.ask;

public class AskManager {
    public AskGameMode gameMode;
    public AbstractAsk[] please;

    public AskManager(){
        gameMode = new AskGameMode();

        please = new AbstractAsk[]{
                gameMode,
        };
    }
}
