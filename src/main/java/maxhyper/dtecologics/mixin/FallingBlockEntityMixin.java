package maxhyper.dtecologics.mixin;

import maxhyper.dtecologics.coconut.FallingCoconutBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin {

    @Inject(
            method = "<init>(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/level/block/state/BlockState;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;setPos(DDD)V"
            )
    )
    private void init(
            Level level,
            double x, double y, double z,
            @NotNull BlockState state,
            CallbackInfo ci
    ) {
        if (state.getBlock() instanceof FallingCoconutBlock) {
            FallingBlockEntity self = (FallingBlockEntity) (Object) this;
            self.refreshDimensions();
        }
    }

    @Inject(
            method = "recreateFromPacket",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;setPos(DDD)V"
            )
    )
    private void recreateFromPacket(
            ClientboundAddEntityPacket packet,
            CallbackInfo ci
    ) {
        FallingBlockEntity self = (FallingBlockEntity) (Object) this;
        if (self.getBlockState().getBlock() instanceof FallingCoconutBlock) {
            self.refreshDimensions();
        }
    }

    @Inject(
            method = "readAdditionalSaveData",
            at = @At("RETURN")
    )
    private void readAdditionalSaveData(
            CompoundTag compound,
            CallbackInfo ci
    ) {
        FallingBlockEntity self = (FallingBlockEntity) (Object) this;
        if (self.getBlockState().getBlock() instanceof FallingCoconutBlock coconutBlock) {
            self.refreshDimensions();
            self.setBoundingBox(coconutBlock.getBoundingBox(self));
        }
    }

}
