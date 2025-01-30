package app.joshie.sourswelter.entity.render;

import app.joshie.sourswelter.entity.SourscourgeEntity;
import app.joshie.sourswelter.entity.model.Sourscourge;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SourscourgeEntityRenderer extends MobRenderer<SourscourgeEntity, Sourscourge<SourscourgeEntity>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("sourswelter", "textures/entity/sourscourge.png");

    public SourscourgeEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new Sourscourge<>(context.bakeLayer(Sourscourge.LAYER_LOCATION)), 0.5F); // 0.5F is the shadow size
    }

    @Override
    public ResourceLocation getTextureLocation(SourscourgeEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(SourscourgeEntity entity, PoseStack poseStack, float partialTickTime) {
        super.scale(entity, poseStack, partialTickTime); // Optional: Adjust size here
    }
}