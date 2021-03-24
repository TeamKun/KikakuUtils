package net.kunmc.lab.kikakuutils.command;

import java.util.Optional;

public class ValidationResult {
    private boolean validationResult;
    private String message;

    public ValidationResult(boolean isValid) {
        this.validationResult = isValid;
        this.message = "";
    }

    public ValidationResult(boolean isValid, String message) {
        this.validationResult = isValid;
        this.message = message;
    }

    public boolean isValid() {
        return validationResult;
    }

    public Optional<String> getMessage() {
        if (message == null) return Optional.empty();
        if (message == "") return Optional.empty();
        return Optional.of(message);
    }
}
