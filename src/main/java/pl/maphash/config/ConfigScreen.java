package pl.maphash.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.Positioner;
import net.minecraft.client.gui.widget.TextWidget;

public class ConfigScreen extends Screen {
    private final Screen parent;
    
    private ButtonWidget noFovChangeBtn;
    private ButtonWidget lowFireBtn;
    private ButtonWidget lowShieldBtn;

    private TextFieldWidget serverNameField;
    private TextFieldWidget serverAddressField;

    public ConfigScreen(Screen parent) {
        super(Text.literal("Maphash's QOL Config"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        GridWidget grid = new GridWidget();
        grid.getMainPositioner().margin(4, 4, 4, 4).alignHorizontalCenter();
        GridWidget.Adder adder = grid.createAdder(2);

        this.noFovChangeBtn = ButtonWidget.builder(Text.literal("NoFOV Change: " + (ModConfig.INSTANCE.noFovChange ? "ON" : "OFF")), button -> {
            ModConfig.INSTANCE.noFovChange = !ModConfig.INSTANCE.noFovChange;
            button.setMessage(Text.literal("NoFOV Change: " + (ModConfig.INSTANCE.noFovChange ? "ON" : "OFF")));
            ModConfig.save();
        }).width(150).build();
        adder.add(this.noFovChangeBtn, 2);

        this.lowFireBtn = ButtonWidget.builder(Text.literal("LowFire: " + (ModConfig.INSTANCE.lowFire ? "ON" : "OFF")), button -> {
            ModConfig.INSTANCE.lowFire = !ModConfig.INSTANCE.lowFire;
            button.setMessage(Text.literal("LowFire: " + (ModConfig.INSTANCE.lowFire ? "ON" : "OFF")));
            ModConfig.save();
        }).width(150).build();
        adder.add(this.lowFireBtn, 2);

        this.lowShieldBtn = ButtonWidget.builder(Text.literal("LowShield: " + (ModConfig.INSTANCE.lowShield ? "ON" : "OFF")), button -> {
            ModConfig.INSTANCE.lowShield = !ModConfig.INSTANCE.lowShield;
            button.setMessage(Text.literal("LowShield: " + (ModConfig.INSTANCE.lowShield ? "ON" : "OFF")));
            ModConfig.save();
        }).width(150).build();
        adder.add(this.lowShieldBtn, 2);

        adder.add(new TextWidget(Text.literal("Add Server:"), this.textRenderer), 2);
        
        this.serverNameField = new TextFieldWidget(this.textRenderer, 0, 0, 95, 20, Text.literal("Server Name"));
        adder.add(this.serverNameField, 1);

        this.serverAddressField = new TextFieldWidget(this.textRenderer, 0, 0, 95, 20, Text.literal("Server Address"));
        adder.add(this.serverAddressField, 1);

        adder.add(ButtonWidget.builder(Text.literal("+ Add"), button -> {
            String name = this.serverNameField.getText();
            String address = this.serverAddressField.getText();
            if (!name.isEmpty() && !address.isEmpty()) {
                ModConfig.INSTANCE.quickConnectServers.add(new ModConfig.ServerEntry(name, address));
                ModConfig.save();
                this.client.setScreen(new ConfigScreen(this.parent));
            }
        }).width(200).build(), 2);

        adder.add(new TextWidget(Text.literal("Remove Servers:"), this.textRenderer), 2);

        int index = 0;
        for (ModConfig.ServerEntry entry : ModConfig.INSTANCE.quickConnectServers) {
            final int currIndex = index;
            adder.add(ButtonWidget.builder(Text.literal("Remove: " + entry.name), button -> {
                ModConfig.INSTANCE.quickConnectServers.remove(currIndex);
                ModConfig.save();
                this.client.setScreen(new ConfigScreen(this.parent));
            }).width(200).build(), 2);
            index++;
        }

        adder.add(ButtonWidget.builder(Text.literal("Done"), button -> {
            this.client.setScreen(this.parent);
        }).width(200).build(), 2);

        grid.refreshPositions();
        grid.forEachChild(this::addDrawableChild);
        
        DirectionalLayoutWidget root = DirectionalLayoutWidget.vertical().spacing(8);
        root.add(grid);
        root.refreshPositions();
        
        int yOffset = Math.max(30, (this.height - root.getHeight()) / 2);
        grid.setPosition(this.width / 2 - grid.getWidth() / 2, yOffset);
        grid.refreshPositions();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
    }
}