package net.slimerealm.client.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IronGolemEntityRenderer;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.Identifier;
import net.slimerealm.SlimeRealmMod;

public class SlimeGolemRenderer extends IronGolemEntityRenderer {
    private static final Identifier TEXTURE = new Identifier(SlimeRealmMod.MOD_ID, "textures/entity/slime_golem/slime_golem.png");

    public SlimeGolemRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(IronGolemEntity ironGolemEntity) {
        return TEXTURE;
    }
}
