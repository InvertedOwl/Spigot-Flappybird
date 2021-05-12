package com.exmogamers.flappybird;

import com.exmogamers.flappybird.game.Game;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Commands implements CommandExecutor {
    public static HashMap<Player, Game> gameMap = new HashMap<>();

    public HashMap<Player, Game> getGameMap() {
        return gameMap;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Configuration config = Main.getInstance().getConfig();
        if (s.equalsIgnoreCase("start")) {
            if (commandSender.hasPermission("flappybird.start") && commandSender instanceof Player) {
                Player player = (Player) commandSender;
                if(player.getScoreboardTags().contains("inFlappyBird")){
                    player.sendMessage("Already playing (TEMP MESSAGE)");
                } else {
                    player.addScoreboardTag("inFlappyBird");
                    player.teleport(player.getLocation().add(0, 30, 0));
                    HashMap<String, Game> gameTest = new HashMap<>();
                    gameMap.put(player, new Game(player));

                }

            }
        }
        if (s.equalsIgnoreCase("exit")) {
            if (commandSender.hasPermission("flappybird.exit") && commandSender instanceof Player) {
                Player player = (Player) commandSender;
                if(!player.getScoreboardTags().contains("inFlappyBird")){
                    player.sendMessage("You are not in game (TEMP MESSAGE)");
                } else {

                    gameMap.get(player).endGame(player);
                }

            }
        }

        return false;
    }
}
