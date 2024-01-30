package com.github.neapovil.latencyhud;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.github.neapovil.latencyhud.event.ClientConnectionEvents;
import com.github.neapovil.latencyhud.listener.ClientConnectionListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.loader.api.FabricLoader;

public final class LatencyHud implements ClientModInitializer
{
    public static LatencyHud INSTANCE;
    private final Path configPath = FabricLoader.getInstance().getConfigDir().resolve("latencyhud.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private ModConfig modConfig = new ModConfig();

    @Override
    public void onInitializeClient()
    {
        INSTANCE = this;

        this.reloadConfig();

        final ClientConnectionListener clientconnectionlistener = new ClientConnectionListener();
        ClientConnectionEvents.PACKET_RECEIVE.register(clientconnectionlistener);
        HudRenderCallback.EVENT.register(clientconnectionlistener);
    }

    public ModConfig getConfig()
    {
        return this.modConfig;
    }

    public void reloadConfig()
    {
        if (Files.exists(this.configPath))
        {
            try
            {
                final String string = Files.readString(this.configPath);

                this.modConfig = this.gson.fromJson(string, ModConfig.class);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            this.saveConfig();
        }
    }

    public void saveConfig()
    {
        final String string = this.gson.toJson(this.modConfig);

        try
        {
            Files.write(this.configPath, string.getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
