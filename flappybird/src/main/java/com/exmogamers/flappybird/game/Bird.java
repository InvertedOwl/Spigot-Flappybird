package com.exmogamers.flappybird.game;

import org.bukkit.Location;
import org.bukkit.entity.*;

public class Bird {
    private Entity birdObject;
    private Player player;

    public Bird(Player player) {

        Location newLoc = player.getLocation().clone();
        newLoc.setZ(player.getLocation().getZ() + 7);
        this.birdObject = player.getWorld().spawnEntity(newLoc, EntityType.CHICKEN);
        this.birdObject.setGravity(false);
        this.player = player;


    }

    public void remove(){
        birdObject.remove();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Entity getBirdObject() {
        return birdObject;
    }

    public void setBirdObject(Entity birdObject) {
        this.birdObject = birdObject;
    }
}
