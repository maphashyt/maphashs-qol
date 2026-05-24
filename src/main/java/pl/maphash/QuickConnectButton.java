package pl.maphash;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import pl.maphash.config.ModConfig;
import net.minecraft.client.gui.Click;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.gui.screen.world.WorldIcon;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.gl.RenderPipelines;

public class QuickConnectButton extends ButtonWidget {
    private final ModConfig.ServerEntry entry;
    private final Runnable onRightClick;
    private final WorldIcon icon;

    public QuickConnectButton(int x, int y, net.minecraft.text.Text message, ModConfig.ServerEntry entry, PressAction onPress, Runnable onRightClick) {
        super(x, y, 20, 20, message, onPress, DEFAULT_NARRATION_SUPPLIER);
        this.entry = entry;
        this.onRightClick = onRightClick;
        this.icon = WorldIcon.forServer(MinecraftClient.getInstance().getTextureManager(), entry.address);
        
        ServerInfo info = new ServerInfo(entry.name, entry.address, ServerInfo.ServerType.OTHER);
        byte[] favicon = info.getFavicon();
        if (favicon != null) {
            try {
                this.icon.load(NativeImage.read(favicon));
            } catch (Exception e) {}
        }
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (this.isHovered() && click.button() == 1) {
            ModConfig.INSTANCE.quickConnectServers.remove(this.entry);
            ModConfig.save();
            if (this.onRightClick != null) {
                this.onRightClick.run();
            }
            return true;
        }
        return super.mouseClicked(click, doubled);
    }

    @Override
    protected void drawIcon(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.drawButton(context);
        
        net.minecraft.util.Identifier textureId = this.icon.getTextureId();
        if (textureId.getPath().contains("unknown_server")) {
            this.drawLabel(context.getHoverListener(this, net.minecraft.client.gui.DrawContext.HoverType.NONE));
        } else {
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, textureId, this.getX() + 2, this.getY() + 2, 16, 16, -1);
        }
    }
}