package net.slimerealm.client.render;

import net.minecraft.client.render.entity.CreeperEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.Identifier;
import net.slimerealm.SlimeRealmMod;

public class GelatinousCreeperRenderer extends CreeperEntityRenderer {
    private static final Identifier TEXTURE = new Identifier(SlimeRealmMod.MOD_ID, "textures/entity/gelatinous_creeper/gelatinous_creeper.png");

    public GelatinousCreeperRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(CreeperEntity creeperEntity) {
        return TEXTURE;
    }
}
