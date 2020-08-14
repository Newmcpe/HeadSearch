package ru.newmcpe.headsearch.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import ru.newmcpe.headsearch.HeadSearch;
import ru.newmcpe.headsearch.player.GamePlayer;

public class EventListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.hasBlock()) {
            Block block = event.getClickedBlock();
            Player player = event.getPlayer();

            if (block.getType() == Material.PLAYER_HEAD || block.getType() == Material.PLAYER_WALL_HEAD) {
                GamePlayer gamePlayer = GamePlayer.of(player);

                if (HeadSearch.getInstance().getHeadLocations().contains(block.getLocation())) {
                    player.sendMessage(HeadSearch.getInstance().getConfig().getString("head-found-message"));

                    int collectedHeads = gamePlayer.getCollectedHeads() + 1;
                    gamePlayer.setCollectedHeads(collectedHeads);

                    if (collectedHeads == HeadSearch.getInstance().getHeadLocations().size()) {
                        Bukkit.getOnlinePlayers().forEach(onlinePlayer ->
                                onlinePlayer.sendMessage(HeadSearch.getInstance().getConfig().getString("all-heads-found-message")
                                        .replace("%player%", player.getDisplayName())
                                )
                        );

                        Bukkit.dispatchCommand(player, "gamemode spectator");
                    }
                }
            }
        }
    }
}
