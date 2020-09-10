package my.app.Commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import my.app.Services.PlayerService;

public class TestCommandExecutor extends AbstractCommandExecutor {

    public TestCommandExecutor() {
        super();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("test")) {
            if (!(sender instanceof Player)) {
                main.getLogger().warning("You can't do this!!!");
                return true;
            }
            try {
                final Player player = (Player) sender;
                final PlayerService playerService = (PlayerService) main.getAppContext().getBean("playerService");

                playerService.addPlayer(player.getName());

                final List<my.app.Entities.Player> players = playerService.findAll();
                player.sendMessage("Players: " + players.toString());

            } catch (Exception e) {
                main.getLogger().warning("Error while executing /test command!");
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

}