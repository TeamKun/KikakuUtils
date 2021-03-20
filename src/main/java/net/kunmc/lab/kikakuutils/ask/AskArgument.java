package net.kunmc.lab.kikakuutils.ask;

public class AskArgument {
    private String argument;

    public String get() {
        return argument == null ? "" : this.argument;
    }

    public void set(String arg) {
        this.argument = arg;
    }

    public boolean is(String target) {
        return argument.equals(target);
    }

    public boolean is(AskArgument askArgument) {
        return argument.equals(askArgument.get());
    }
}
