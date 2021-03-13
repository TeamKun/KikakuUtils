package net.kunmc.lab.kikakuutils.utils;

import org.bukkit.GameMode;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GameModeUtils {
    public static final String S_ADVENTURE = "adventure";
    public static final String S_CREATIVE = "creative";
    public static final String S_SURVIVAL = "survival";
    public static final String S_SPECTATOR = "spectator";

    public static boolean isGameMode(String text) {
        if (text.equals(S_ADVENTURE)) return true;
        if (text.equals(S_CREATIVE)) return true;
        if (text.equals(S_SURVIVAL)) return true;
        if (text.equals(S_SPECTATOR)) return true;
        return false;
    }

    public static List<String> stringValues() {
        return Arrays.asList(S_ADVENTURE, S_CREATIVE, S_SURVIVAL, S_SPECTATOR);
    }

    public static Optional<GameMode> toGameMode(String text) {
        if (isGameMode(text)) return Optional.of(GameMode.valueOf(text));
        return Optional.empty();
    }
}
