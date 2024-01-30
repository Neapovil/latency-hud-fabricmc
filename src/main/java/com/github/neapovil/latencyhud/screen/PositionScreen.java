package com.github.neapovil.latencyhud.screen;

import com.github.neapovil.latencyhud.LatencyHud;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public final class PositionScreen extends Screen
{
    private final LatencyHud mod;
    private final Screen parent;
    private final String string = "0ms";
    private int maxWidth;
    private int maxHeight;

    public PositionScreen(Screen parent)
    {
        super(Text.literal(""));
        this.mod = LatencyHud.INSTANCE;
        this.parent = parent;
    }

    @Override
    protected void init()
    {
        this.maxWidth = this.width - this.textRenderer.getWidth(this.string);
        this.maxHeight = this.height - this.textRenderer.fontHeight;
    }

    @Override
    public void close()
    {
        this.mod.saveConfig();
        this.client.setScreen(this.parent);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        super.render(matrices, mouseX, mouseY, delta);

        this.textRenderer.drawWithShadow(matrices, this.string, this.mod.getConfig().positionX, this.mod.getConfig().positionY, 0xFFFFFF);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY)
    {
        this.setPosition(mouseX, mouseY);
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        this.setPosition(mouseX, mouseY);
        return true;
    }

    private void setPosition(double mouseX, double mouseY)
    {
        if (mouseX > 0 && mouseX <= this.maxWidth)
        {
            this.mod.getConfig().positionX = (float) mouseX;
        }

        if (mouseY > 0 && mouseY <= this.maxHeight)
        {
            this.mod.getConfig().positionY = (float) mouseY;
        }
    }
}
