package me.mattdokn.middleclicker;

import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import me.mattdokn.middleclicker.mixins.HandledScreenAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;

@Environment(EnvType.CLIENT)
public class MiddleClickerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenEvents.AFTER_INIT.register(this::afterInitScreen);
    }

    private void afterInitScreen(MinecraftClient client, Screen screen, int windowWidth, int windowHeight) {
        if (screen instanceof AbstractInventoryScreen || screen instanceof GenericContainerScreen) {
            ScreenMouseEvents.afterMouseClick(screen).register((_screen, mouseX, mouseY, button) -> {
                if (button != 2) return;
                MinecraftClient mc = MinecraftClient.getInstance();
                if (mc.player == null || mc.player.currentScreenHandler == null || mc.getNetworkHandler() == null) return;
                HandledScreenAccessor hScreen = (HandledScreenAccessor) _screen;
                ClickSlotC2SPacket packet = new ClickSlotC2SPacket(mc.player.currentScreenHandler.syncId, mc.player.currentScreenHandler.getRevision(), hScreen.invokeGetSlotAt(mouseX, mouseY).id, 2, SlotActionType.CLONE, ItemStack.EMPTY, Int2ObjectMaps.emptyMap());
                mc.getNetworkHandler().sendPacket(packet);

            });
        }
    }
}
