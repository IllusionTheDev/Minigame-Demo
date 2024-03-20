package me.illusion.minigamedemo.game.core.template;

import me.illusion.minigamedemo.game.map.data.GameMapData;
import me.illusion.minigamedemo.game.map.instance.LoadedMap;

public abstract class AbstractGameTemplate<L extends LoadedMap> implements GameTemplate<L> {

    private GameMapData<L> mapData;

    @Override
    public GameMapData<L> getMapData() {
        if (mapData == null) {
            mapData = createMapData();
        }

        return mapData;
    }

    protected abstract GameMapData<L> createMapData();
}
