package net.kunmc.lab.kikakuutils.command;

import net.kunmc.lab.kikakuutils.command.ku.Gamemode;

public class Ku extends AbstractCommand {
    public Ku() {
        // 必要？
        super();

        // コマンド引数の追加
        super.registerArgument(new Gamemode());
    }
}
