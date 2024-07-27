package com.github.neapovil.latencyhud.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class ModConfig
{
    public float positionX = 0;
    public float positionY = 0;

    public void save()
    {
        CompletableFuture.runAsync(() -> {
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            final String string = gson.toJson(this);

            try
            {
                Files.write(this.path(), string.getBytes());
            }
            catch (IOException ignored)
            {
            }
        });
    }

    public void load()
    {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try
        {
            final String string = Files.readString(this.path());
            final ModConfig modconfig = gson.fromJson(string, ModConfig.class);

            this.positionX = modconfig.positionX;
            this.positionY = modconfig.positionY;
        }
        catch (IOException ignored)
        {
        }
    }

    private Path path()
    {
        return FabricLoader.getInstance().getConfigDir().resolve("latencyhud.json");
    }
}
