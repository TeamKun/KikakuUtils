package net.kunmc.lab.kikakuutils.command;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class TargetPlayerState {
    public static TargetPlayerState ALL = new TargetPlayerState("all");
    public static TargetPlayerState ONLINE = new TargetPlayerState("online");
    public static TargetPlayerState OFFLINE = new TargetPlayerState("offline");

    private static final TargetPlayerState[] targetPlayerStates = new TargetPlayerState[]{
            ALL,
            ONLINE,
            OFFLINE
    };

    private String state;

    private TargetPlayerState(String state) {
        this.state = state;
    }

    public String value() {
        return this.state;
    }

    public static String[] values() {
        return Arrays.stream(targetPlayerStates).map(v -> v.state).toArray(String[]::new);
    }

    public static Optional<TargetPlayerState> valueOf(String text) {
        for (TargetPlayerState state : targetPlayerStates) {
            if (text.equals(state.state)) return Optional.of(state);
        }
        return Optional.empty();
    }

    public static boolean contains(String text) {
        for (TargetPlayerState state : targetPlayerStates) {
            if (text.equals(state.state)) return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TargetPlayerState that = (TargetPlayerState) o;
        return Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }
}
