package ru.newmcpe.headsearch.player;

import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import ru.newmcpe.headsearch.HeadSearch;

@Data
public class GamePlayer {
    final private Player player;
    private int collectedHeads = 0;

    private GamePlayer(Player player) {
        this.player = player;
    }

    public static GamePlayer of(Player player) {
        GamePlayer gamePlayer = (GamePlayer) player.getMetadata("game_player").stream().findFirst().orElse(null);

        if (gamePlayer == null){
            gamePlayer = new GamePlayer(player);
            
            player.setMetadata("game_player", new FixedMetadataValue(HeadSearch.getInstance(), gamePlayer));
        }

        return gamePlayer;
    }
}
