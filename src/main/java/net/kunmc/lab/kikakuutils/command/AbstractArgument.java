package net.kunmc.lab.kikakuutils.command;

import org.bukkit.command.CommandSender;

import java.util.List;

abstract public class AbstractArgument {
    abstract public boolean executeCommand(CommandSender sender, String[] args);
    abstract public List<String> tabComplete(CommandSender sender, String[] args);
}
