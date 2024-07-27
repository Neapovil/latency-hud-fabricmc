package com.github.neapovil.latencyhud.screen;

import com.github.neapovil.latencyhud.LatencyHud;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
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
        this.mod.config().save();
        this.client.setScreen(this.parent);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        super.render(context, mouseX, mouseY, delta);
        context.drawTextWithShadow(this.textRenderer, this.string, (int) this.mod.config().positionX, (int) this.mod.config().positionY, 0xFFFFFF);
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
            this.mod.config().positionX = (float) mouseX;
        }

        if (mouseY > 0 && mouseY <= this.maxHeight)
        {
            this.mod.config().positionY = (float) mouseY;
        }
    }
}
