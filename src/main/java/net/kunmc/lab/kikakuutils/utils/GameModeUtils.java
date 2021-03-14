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

    public static final String SL_ADVENTURE = "ADVENTURE";
    public static final String SL_CREATIVE = "CREATIVE";
    public static final String SL_SURVIVAL = "SURVIVAL";
    public static final String SL_SPECTATOR = "SPECTATOR";

    public static boolean isGameMode(String text) {
        return isGameModeStrict(text.toUpperCase());
    }

    public static boolean isGameModeStrict(String text) {
        if (text.equals(SL_ADVENTURE)) return true;
        if (text.equals(SL_CREATIVE)) return true;
        if (text.equals(SL_SURVIVAL)) return true;
        if (text.equals(SL_SPECTATOR)) return true;
        return false;
    }

    public static List<String> stringLowerCaseValues() {
        return Arrays.asList(S_ADVENTURE, S_CREATIVE, S_SURVIVAL, S_SPECTATOR);
    }

    public static List<String> stringUpperCaseValues() {
        return Arrays.asList(SL_ADVENTURE, SL_CREATIVE, SL_SURVIVAL, SL_SPECTATOR);
    }

    public static Optional<GameMode> toGameMode(String text) {
        if (isGameMode(text)) {
            text = text.toUpperCase();
            return Optional.of(GameMode.valueOf(text));
        }

        return Optional.empty();
    }
}
