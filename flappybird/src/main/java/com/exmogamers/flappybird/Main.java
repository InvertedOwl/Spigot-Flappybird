package com.exmogamers.flappybird;

import com.exmogamers.flappybird.game.Bird;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    Bird bird;

    private static Main mainInstance;
    public static Main getInstance() {
        return mainInstance;
    }
    @Override
    public void onLoad() {
        mainInstance = this;
    }



    @Override
    public void onEnable() {
        this.getCommand("start").setExecutor(new Commands());
        this.getCommand("exit").setExecutor(new Commands());


        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {

                    if (player.getScoreboardTags().contains("inFlappyBird")) {

                        Commands.gameMap.get(player).update();


                    }
                }
            }
        }, 0, 0);
    }
    @Override
    public void onDisable() {
        for(Player player : Bukkit.getOnlinePlayers()){
            if(Commands.gameMap.containsKey(player) && player.getScoreboardTags().contains("inFlappyBird")) {
                Commands.gameMap.get(player).endGame(player);
            }
        }
        super.saveDefaultConfig();
    }
}
