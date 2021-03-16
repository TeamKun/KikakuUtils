package net.kunmc.lab.kikakuutils.command;

import net.kunmc.lab.kikakuutils.command.please.PleaseGameModeCmd;

public class PleaseCmd extends AbstractCommand {
    public PleaseCmd() {
        super();
        registerArgument(new PleaseGameModeCmd());
    }
}
