package net.kunmc.lab.kikakuutils.please;

public class PleaseManager {
    public PleaseGameMode gameMode;
    public AbstractPlease[] please;

    public PleaseManager(){
        gameMode = new PleaseGameMode();

        please = new AbstractPlease[]{
                gameMode,
        };
    }
}
