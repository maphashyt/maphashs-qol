package pl.maphash.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.maphash.config.ModConfig;
import pl.maphash.QuickConnectButton;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        int startX = 2;
        int startY = 2;
        int i = 0;
        for (ModConfig.ServerEntry entry : ModConfig.INSTANCE.quickConnectServers) {
            final String address = entry.address;
            String btnTextStr = entry.name;
            if (btnTextStr.length() > 1) {
                btnTextStr = btnTextStr.substring(0, 1);
            }
            net.minecraft.text.Text btnText = net.minecraft.text.Text.literal(btnTextStr);
            QuickConnectButton btn = new QuickConnectButton(startX + i * 22, startY, btnText, entry, button -> {
                ServerInfo info = new ServerInfo(entry.name, address, ServerInfo.ServerType.OTHER);
                ConnectScreen.connect(this, this.client, ServerAddress.parse(address), info, false, null);
            }, () -> {
                this.client.setScreen(new TitleScreen());
            });
            this.addDrawableChild(btn);
            i++;
        }
    }
}