package com.exmogamers.flappybird.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;

import java.util.ArrayList;
import java.util.List;

public class Pipe {
    private Location parentLocation;
    private int height = 15;
    private int opening = 5;
    private List<Entity> objectList = new ArrayList<>();

    public Pipe(Location spawnLocation){
        this.parentLocation = parentLocation;
        this.height = height;
        this.objectList = objectList;
        this.opening = opening;
    }
    public void update(){
        if(objectList.size() > 0) {
            for (Entity entity : objectList) {
                entity.remove();
            }
        }

        for(int y = 0; y < height; y++){
            if(y != opening && y != opening + 1) {
                BlockData blockData = Bukkit.createBlockData(Material.GREEN_CONCRETE);
                World world = parentLocation.getWorld();
                Location spawnLoc = parentLocation.clone();
                spawnLoc.add(0, y - 4, 0);
                Entity spawnEntity = world.spawnFallingBlock(spawnLoc, blockData);
                //spawnEntity.setGravity(false);
                objectList.add(spawnEntity);
            }
        }
    }

    public void remove(){
        if(objectList.size() > 0) {
            for (Entity entity : objectList) {
                entity.remove();
            }
        }
    }

    public Location getParentLocation() {
        return parentLocation;
    }

    public void setParentLocation(Location parentLocation) {
        this.parentLocation = parentLocation;
    }

    public int getHeight() { return height; }

    public void setHeight(int height) { this.height = height; }

    public int getOpening() {
        return opening;
    }

    public void setOpening(int opening) {
        this.opening = opening;
    }
}
