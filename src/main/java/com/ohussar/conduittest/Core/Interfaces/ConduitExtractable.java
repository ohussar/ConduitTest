package com.ohussar.conduittest.Core.Interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.UUID;

public interface ConduitExtractable<T extends BlockEntity> {

    public enum Type {
        SOURCE,
        DESTINATION,
        CONDUIT
    };

    public Type getExtractionType();

    public T getBlockEntity(Level level, BlockPos pos);

    public UUID getId();

}
