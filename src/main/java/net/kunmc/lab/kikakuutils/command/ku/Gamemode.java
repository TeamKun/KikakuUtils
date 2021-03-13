package net.kunmc.lab.kikakuutils.command.ku;

import net.kunmc.lab.kikakuutils.command.AbstractArgument;
import net.kunmc.lab.kikakuutils.command.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gamemode extends AbstractArgument {
    // /ku gamemode <adventure|creative|survival|spectator>
    // /ku gamemode <adventure|creative|survival|spectator> TARGET_SELECTOR

    private final String ADVENTURE = "adventure";
    private final String CREATIVE = "creative";
    private final String SURVIVAL = "survival";
    private final String SPECTATOR = "spectator";

    public boolean executeCommand(CommandSender sender, String[] args){
        if(args.length < 2) return false;
        if(args.length > 3) return false;
        if(!args[0].equals("gamemode")) return false;

        // ターゲットセレクタの処理？

        // ゲームモードの処理？

        return false;
    }

    public List<String> tabComplete(CommandSender sender, String[] args){
        if(args.length == 0) return null;
        if(args.length > 3) return null;

        if(args.length == 1 && "gamemode".startsWith(args[0])){
            return Collections.singletonList("gamemode");
        }

        if(args.length == 2 && args[0].equals("gamemode")){

        }


        return null;
    }
}
