package com.exmogamers.flappybird.game;

import com.exmogamers.flappybird.Commands;
import com.exmogamers.flappybird.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class Game {
    Bird bird;
    Player player;
    Pipe pipe1;
    Pipe pipe2;
    int score = 0;
    Location gameLocation;
    double pipe1offset = 0;
    double pipe2offset = 0;

    public Game(Player player){
        this.player = player;
        this.bird = new Bird(player);
        this.gameLocation = player.getLocation();
        this.gameLocation.setPitch(0);
        this.gameLocation.setYaw(0);
        this.pipe1 = new Pipe(gameLocation);
        this.pipe2 = new Pipe(gameLocation);
        this.score = score;

    }

    public void update(){
        player.setFlying(true);

        if(!bird.getBirdObject().hasGravity()){
            player.sendTitle("Shift to Start", String.valueOf(score), 0, 5, 0);
        } else {
            player.sendTitle(" ", String.valueOf(score), 0, 5, 0);
        }

        player.teleport(gameLocation);
        pipe1.setParentLocation(gameLocation.clone());
        pipe2.setParentLocation(gameLocation.clone());

        Location newLoc1 = pipe1.getParentLocation().clone();
        newLoc1.add(pipe1offset, 0, 7);
        pipe1offset += 0.1;

        Location newLoc2 = pipe2.getParentLocation().clone();
        newLoc2.add(pipe2offset, 0, 7);
        pipe2offset += 0.1;

        if(pipe1offset > 10){
            pipe1.setHeight(12);
            int randomInt = ThreadLocalRandom.current().nextInt(2, 8 + 1);
            pipe1.setOpening(randomInt);
        }
        if(pipe2offset > 10){
            pipe2.setHeight(14);
            int randomInt = ThreadLocalRandom.current().nextInt(2, 8 + 1);
            pipe2.setOpening(randomInt);
        }
        if(pipe1offset > 10){
            pipe1offset = -10;

        }
        if(pipe2offset > 10){
            pipe2offset = -10;

        }

        if(pipe2offset == -10 || pipe1offset == -10){
            score++;
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.8f);
        }

        if(!bird.getBirdObject().hasGravity()){
            pipe1offset = -10;
            pipe2offset = -20;
        }


        pipe1.setParentLocation(newLoc1);
        pipe2.setParentLocation(newLoc2);

        pipe1.update();
        pipe2.update();


        Commands.gameMap.get(player).bird.getBirdObject().teleport(new Location(player.getWorld(), player.getLocation().getX(),Commands.gameMap.get(player).bird.getBirdObject().getLocation().getY() , gameLocation.getZ() + 7, 90, 0));


        if(player.isSneaking()){
            bird.getBirdObject().setGravity(true);
            bird.getBirdObject().setVelocity(new Vector(0.0, 0.1, 0));
        }

        for (int i = -10; i < 11; i++) {
            for (int y = -10; y < 11; y++) {



                    Location loc = player.getLocation();
                    BlockData blockData = Bukkit.createBlockData(Material.LIGHT_BLUE_TERRACOTTA);
                    loc.setZ(loc.getZ() + 8);
                    loc.setY(loc.getY() + y);
                    loc.setX(loc.getX() + i);

                    player.sendBlockChange(loc, blockData);
            }
        }
        if(bird.getBirdObject().getLocation().distance(gameLocation) > 11){
            endGame(player);
        }
        for(Entity entity : bird.getBirdObject().getWorld().getNearbyEntities(bird.getBirdObject().getLocation(), 1, 1, 1)){
            if(entity != bird.getBirdObject() && bird.getBirdObject().hasGravity() && bird.getBirdObject().getLocation().distance(entity.getLocation()) < 0.5){
                endGame(player);
            }
        }
    }

    public void endGame(Player player) {
        Configuration config = Main.getInstance().getConfig();
        if(player.getScoreboardTags().contains("inFlappyBird")) {
            if(config.getBoolean("announce-score")) {
                Bukkit.broadcastMessage(player.getDisplayName() + " Reached a score of " + score + " In Flappy Bird!");
            }
            player.removeScoreboardTag("inFlappyBird");
            Commands.gameMap.get(player).bird.remove();
            pipe1.remove();
            pipe2.remove();
            player.setFlying(false);
            player.teleport(player.getLocation().add(0, -30, 0));
            for (int i = -10; i < 11; i++) {
                for (int y = -10; y < 11; y++) {


                    Location loc = gameLocation.clone();
                    loc.setZ(loc.getZ() + 8);
                    loc.setY(loc.getY() + y);
                    loc.setX(loc.getX() + i);
                    BlockData blockData = loc.getBlock().getBlockData();
                    player.sendBlockChange(loc, blockData);
                }
            }
            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_DEATH, 1, 1);
        }
    }



    public Player getPlayer() {
        return player;
    }

    public Bird getPipe() {
        return bird;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Location getGameLocation() {
        return gameLocation;
    }
}
