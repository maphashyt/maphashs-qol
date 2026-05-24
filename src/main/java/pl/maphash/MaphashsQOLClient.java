package pl.maphash;

import net.fabricmc.api.ClientModInitializer;
import pl.maphash.config.ModConfig;

public class MaphashsQOLClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModConfig.load();
    }
}