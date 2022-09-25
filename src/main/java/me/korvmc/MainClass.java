package me.korvmc;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

import net.minecraft.client.gui.ClientChatListener;

import java.util.UUID;

public class MainClass implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("KorvMC");

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Hello Fabric world!");

        ClientTickEvents.END_CLIENT_TICK.register((evt) -> {
            LOGGER.info(String.valueOf(evt.player.getPos().x));

            //            long x = ((long)player.poss)
        });

        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) ->
        {
            BlockState state = world.getBlockState(pos);
            /* Manual spectator check is necessary because AttackBlockCallbacks
               fire before the spectator check */
            if (state.isToolRequired() && !player.isSpectator() &&
                    player.getMainHandStack().isEmpty())
            {
                player.damage(DamageSource.GENERIC, 1.0F);
            }
            player.setVelocityClient(1,0,0);
            LOGGER.info("Hit smt event!");


            return ActionResult.PASS;
        });
    }

    public void tick(MinecraftClient client) {
        if (client.player != null) {
            client.player.setVelocityClient(10,0,0);
        }
    }

    public void onChatMessage(MessageType type, Text message, UUID sender) {
        LOGGER.info("Chat: "+ message);
    }

}
