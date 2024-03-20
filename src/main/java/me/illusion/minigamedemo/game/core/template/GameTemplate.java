package me.illusion.minigamedemo.game.core.template;

import me.illusion.minigamedemo.game.core.Game;
import me.illusion.minigamedemo.game.map.data.GameMapData;
import me.illusion.minigamedemo.game.map.instance.LoadedMap;

public interface GameTemplate<L extends LoadedMap> {

    String getName();

    Game createGame(L map);

    GameMapData<L> getMapData();
}
