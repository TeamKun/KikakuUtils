package net.kunmc.lab.kikakuutils.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import java.util.List;

public class Utils {
    public static boolean isValidTarget(CommandSender sender, String text) {
        try {
            List<Entity> tmp = Bukkit.selectEntities(sender, text);
        } catch (Exception ignore) {
            return false;
        }
        return true;
    }
}
