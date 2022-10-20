package io.github.GrassyDev.pvzmod.registry.plants.models;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.RepeaterEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RepeaterEntityModel extends AnimatedGeoModel<RepeaterEntity> {

    @Override
    public Identifier getModelResource(RepeaterEntity object)
    {
        return new Identifier("pvzmod", "geo/repeater.geo.json");
    }

    @Override
    public Identifier getTextureResource(RepeaterEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/peashooter/peashooter.png");
    }

    @Override
    public Identifier getAnimationResource(RepeaterEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashooter.json");
    }
}
