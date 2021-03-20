package net.kunmc.lab.kikakuutils.command;

import net.kunmc.lab.kikakuutils.command.AbstractArgument;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ArgumentSpawnpoint extends AbstractArgument {
    public boolean onCommand(CommandSender sender, String[] args){
        return false;
    }

    public List<String> tabComplete(CommandSender sender, String[] args){
        return null;
    }
}
