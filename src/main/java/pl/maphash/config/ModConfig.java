package pl.maphash.config;

import net.minecraft.client.MinecraftClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(MinecraftClient.getInstance().runDirectory, "config/maphashs-qol.json");

    public static ConfigData INSTANCE = new ConfigData();

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                INSTANCE = GSON.fromJson(reader, ConfigData.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            save();
        }
    }

    public static void save() {
        try {
            CONFIG_FILE.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(INSTANCE, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ConfigData {
        public boolean noFovChange = false;
        public boolean lowFire = false;
        public boolean lowShield = false;
        public List<ServerEntry> quickConnectServers = new ArrayList<>();
    }

    public static class ServerEntry {
        public String name = "";
        public String address = "";

        public ServerEntry() {}
        
        public ServerEntry(String name, String address) {
            this.name = name;
            this.address = address;
        }
    }
}