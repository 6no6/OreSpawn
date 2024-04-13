package com.o6no6.orespawn;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class OreSpawn extends JavaPlugin {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getLogger().info("[OreSpawn] is starting......");
        getLogger().info("[OreSpawn] Made by 6no6");

        spawnItemOnNearestBlock(this.getConfig().getString("WorldName"), Material.NETHERITE_INGOT, Material.NETHERITE_BLOCK,
                this.getConfig().getInt("MaxPlayerDistance"), this.getConfig().getInt("Reloadtime.Netherite"));

        spawnItemOnNearestBlock(this.getConfig().getString("WorldName"), Material.EMERALD, Material.EMERALD_BLOCK,
                this.getConfig().getInt("MaxPlayerDistance"), this.getConfig().getInt("Reloadtime.Emerald"));

        spawnItemOnNearestBlock(this.getConfig().getString("WorldName"), Material.DIAMOND, Material.DIAMOND_BLOCK,
                this.getConfig().getInt("MaxPlayerDistance"), this.getConfig().getInt("Reloadtime.Diamond"));

        spawnItemOnNearestBlock(this.getConfig().getString("WorldName"), Material.QUARTZ, Material.QUARTZ_BLOCK,
                this.getConfig().getInt("MaxPlayerDistance"), this.getConfig().getInt("Reloadtime.Quartz"));

        spawnItemOnNearestBlock(this.getConfig().getString("WorldName"), Material.GOLD_INGOT, Material.GOLD_BLOCK,
                this.getConfig().getInt("MaxPlayerDistance"), this.getConfig().getInt("Reloadtime.Gold"));

        spawnItemOnNearestBlock(this.getConfig().getString("WorldName"), Material.IRON_INGOT, Material.IRON_BLOCK,
                this.getConfig().getInt("MaxPlayerDistance"), this.getConfig().getInt("Reloadtime.Iron"));

        spawnItemOnNearestBlock(this.getConfig().getString("WorldName"), Material.LAPIS_LAZULI, Material.LAPIS_BLOCK,
                this.getConfig().getInt("MaxPlayerDistance"), this.getConfig().getInt("Reloadtime.Lapis"));

        spawnItemOnNearestBlock(this.getConfig().getString("WorldName"), Material.COAL, Material.COAL_BLOCK,
                this.getConfig().getInt("MaxPlayerDistance"), this.getConfig().getInt("Reloadtime.Coal"));

        spawnItemOnNearestBlock(this.getConfig().getString("WorldName"), Material.REDSTONE, Material.REDSTONE_BLOCK,
                this.getConfig().getInt("MaxPlayerDistance"), this.getConfig().getInt("Reloadtime.Redstone"));

        spawnItemOnNearestBlock(this.getConfig().getString("WorldName"), Material.COPPER_INGOT, Material.COPPER_BLOCK,
                this.getConfig().getInt("MaxPlayerDistance"), this.getConfig().getInt("Reloadtime.Copper"));

        getLogger().info("[OreSpawn] End, all dependecies start automatically");
    }

    private Location findNearestBlock(Location location, Material material, int radius) {
        World world = location.getWorld();
        int searchRadius = Math.min(radius, 128);

        int blockX = location.getBlockX();
        int blockY = location.getBlockY();
        int blockZ = location.getBlockZ();

        for (int x = blockX - searchRadius; x <= blockX + searchRadius; x++) {

            for (int y = blockY - searchRadius; y <= blockY + searchRadius; y++) {

                for (int z = blockZ - searchRadius; z <= blockZ + searchRadius; z++) {

                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType() == material) {
                        return block.getLocation();
                    }
                }
            }
        }
        return null;
    }

    private void spawnItemOnNearestBlock(String worldName, Material spawnMaterial, Material targetBlockMaterial, int maxDistance, int seconds) {
        BukkitTask task = getServer().getScheduler().runTaskTimer(this, new Runnable() {

            @Override
            public void run() {
                World world = Bukkit.getWorld(worldName);
                if (world == null) {
                    getLogger().warning("World '" + worldName + "' not found.");
                    return;
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Location playerLocation = player.getLocation();

                    Location nearestBlockLocation = findNearestBlock(playerLocation, targetBlockMaterial, maxDistance);
                    if (nearestBlockLocation != null) {
                        world.dropItemNaturally(nearestBlockLocation.add(0, 1, 0), new ItemStack(spawnMaterial, 1));
                    }
                }
            }
        }, 0, (seconds * 20));

    }
}
