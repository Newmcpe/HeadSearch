package ru.newmcpe.headsearch;

import com.google.common.collect.Lists;
import lombok.Getter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.newmcpe.headsearch.listener.EventListener;
import ru.newmcpe.headsearch.player.GamePlayer;
import ru.newmcpe.headsearch.util.LocationUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HeadSearch extends JavaPlugin {
    @Getter
    public static HeadSearch instance;

    @Getter
    private List<Location> headLocations = Lists.newArrayList();

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new EventListener(), this);

        getConfig().addDefault("head-locations", Collections.singletonList("5.0:7.0:-266.0"));
        getConfig().addDefault("head-status-message", "%found_heads% / %total_heads%");
        getConfig().addDefault("head-found-message", "Вы нашли голову!");
        getConfig().addDefault("all-heads-found-message", "%player% нашел все головы!");

        getConfig().getDefaults().options().copyDefaults(true);

        headLocations = getConfig().getStringList("head-locations")
                .stream()
                .map(LocationUtil::getLocationFromString)
                .collect(Collectors.toList());

        this.startStatusTask();
    }

    private void startStatusTask() {
        Bukkit.getScheduler().runTaskTimer(this, () -> Bukkit.getOnlinePlayers()
                .stream()
                .map(GamePlayer::of)
                .forEach(gamePlayer -> {
                    gamePlayer.getPlayer().sendActionBar(
                            TextComponent.fromLegacyText(getConfig().getString("head-status-message")
                                    .replace("%found_heads%", String.valueOf(gamePlayer.getCollectedHeads()))
                                    .replace("%total_heads%", String.valueOf(headLocations.size())
                                    )));
                }), 0L, 20L);
    }
}
