package com.denfop.tiles.base;

import crazypants.enderio.entity.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntitySkeleton;

public class SkeletonFix {
    public static EntitySkeleton init(EntitySkeleton entity) {
        if ((entity instanceof EntityWitherSkeleton)) {
            EntitySkeleton entity1 = new EntitySkeleton(entity.worldObj);
            entity1.setSkeletonType(1);
        }
        return entity;

    }
}
