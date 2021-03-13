package net.kunmc.lab.kikakuutils.command.ku;

import net.kunmc.lab.kikakuutils.command.AbstractArgument;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Spawnpoint extends AbstractArgument {
    public boolean executeCommand(CommandSender sender, String[] args){
        return false;
    }

    public List<String> tabComplete(CommandSender sender, String[] args){
        return null;
    }
}
