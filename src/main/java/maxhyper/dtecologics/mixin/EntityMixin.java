package maxhyper.dtecologics.mixin;

import maxhyper.dtecologics.coconut.FallingCoconutBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(
            method = "makeBoundingBox",
            at = @At("HEAD"),
            cancellable = true
    )
    protected void makeBoundingBox(CallbackInfoReturnable<AABB> cir) {
        if (!((Entity) (Object) this instanceof FallingBlockEntity fallingBlock)) {
            return;
        }

        @Nullable BlockState blockState = fallingBlock.getBlockState();

        if (blockState == null || !(blockState.getBlock() instanceof FallingCoconutBlock coconutBlock)) {
            return;
        }

        cir.setReturnValue(coconutBlock.getBoundingBox(fallingBlock));
        cir.cancel();
    }

}
