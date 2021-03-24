package net.kunmc.lab.kikakuutils.rules;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * 設定の変更をする際、強制的に行うのか、プレイヤーに確認するのか、それとも権限で分けるのかを定める
 */
public class ExecutionAttitude {
    public static ExecutionAttitude ASK = new ExecutionAttitude("ask");
    public static ExecutionAttitude FORCE = new ExecutionAttitude("force");
    public static ExecutionAttitude PERMISSION = new ExecutionAttitude("permission");

    private static final ExecutionAttitude[] attitudes = new ExecutionAttitude[]{
            ASK,
            FORCE,
            PERMISSION
    };

    private String state;

    private ExecutionAttitude(String state) {
        this.state = state;
    }

    public String value() {
        return this.state;
    }

    public static String[] values() {
        return Arrays.stream(attitudes).map(v -> v.state).toArray(String[]::new);
    }

    public static Optional<ExecutionAttitude> valueOf(String text) {
        for (ExecutionAttitude attitude : attitudes) {
            if (text.equals(attitude.state)) return Optional.of(attitude);
        }
        return Optional.empty();
    }

    public static boolean contains(String text) {
        for (ExecutionAttitude state : attitudes) {
            if (text.equals(state.state)) return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExecutionAttitude that = (ExecutionAttitude) o;
        return Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }
}
