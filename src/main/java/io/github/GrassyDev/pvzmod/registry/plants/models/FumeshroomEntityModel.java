package io.github.GrassyDev.pvzmod.registry.plants.models;

import io.github.GrassyDev.pvzmod.registry.plants.plantentity.FumeshroomEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FumeshroomEntityModel extends AnimatedGeoModel<FumeshroomEntity> {

    @Override
    public Identifier getModelResource(FumeshroomEntity object)
    {
        return new Identifier("pvzmod", "geo/fumeshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(FumeshroomEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/fumeshroom/fumeshroom.png");
    }

    @Override
    public Identifier getAnimationResource(FumeshroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/fumeshroom.json");
    }
}
