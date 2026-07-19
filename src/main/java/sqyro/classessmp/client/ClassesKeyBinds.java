package sqyro.classessmp.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.network.Keybind1Packet;
import sqyro.classessmp.network.Keybind2Packet;

import org.lwjgl.glfw.GLFW;
import sqyro.classessmp.network.Keybind3Packet;

public class ClassesKeyBinds {
    public static KeyMapping ABILITY1;
    public static KeyMapping ABILITY2;
    public static KeyMapping ABILITY3;

    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "abilities"));

    public static void register() {
        ABILITY1 = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.classessmp.ability1", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_1, CATEGORY));
        ABILITY2 = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.classessmp.ability2", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_2, CATEGORY));
        ABILITY3 = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.classessmp.ability3", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_3, CATEGORY));
    }

    public static void registerEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (ABILITY1.consumeClick()) {
                ClientPlayNetworking.send(new Keybind1Packet());
            }

            while (ABILITY2.consumeClick()) {
                ClientPlayNetworking.send(new Keybind2Packet());
            }

            while (ABILITY3.consumeClick()) {
                ClientPlayNetworking.send(new Keybind3Packet());
            }
        });
    }
}