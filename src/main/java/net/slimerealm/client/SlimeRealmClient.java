package net.slimerealm.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.slimerealm.client.render.GelatinousCreeperRenderer;
import net.slimerealm.client.render.SlimeGolemRenderer;
import net.slimerealm.item.SlimeBootsItem;
import net.slimerealm.registry.ModBlocks;
import net.slimerealm.registry.ModEntities;

public class SlimeRealmClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLIME_LEAVES, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLIME_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLIME_PORTAL, RenderLayer.getTranslucent());

        EntityRendererRegistry.register(ModEntities.SLIME_GOLEM, SlimeGolemRenderer::new);
        EntityRendererRegistry.register(ModEntities.GELATINOUS_CREEPER, GelatinousCreeperRenderer::new);

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                SlimeBootsItem.handlePlayerTick(client.player);
            }
        });
    }
}
