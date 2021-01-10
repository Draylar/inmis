package draylar.inmis.client;

import draylar.inmis.network.ServerNetworking;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;

public class InmisKeybinds {
    private static KeyBinding openBackpackKeybinding;

    public static void initialize() {
        openBackpackKeybinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.inmis.open_backpack",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_GRAVE_ACCENT,
                "category.inmis.keybindings"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openBackpackKeybinding.wasPressed()) {
                ClientPlayNetworking.send(ServerNetworking.OPEN_BACKPACK, new PacketByteBuf(Unpooled.buffer()));
            }
        });
    }
}
