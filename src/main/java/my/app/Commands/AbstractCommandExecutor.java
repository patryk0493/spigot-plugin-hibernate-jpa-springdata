package my.app.Commands;

import org.bukkit.command.CommandExecutor;

import my.app.Main;

public abstract class AbstractCommandExecutor implements CommandExecutor {

    protected Main main;

    public AbstractCommandExecutor() {
        main = Main.getInstance();
    }

}