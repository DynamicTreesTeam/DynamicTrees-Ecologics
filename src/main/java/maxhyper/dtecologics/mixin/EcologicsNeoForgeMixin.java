package maxhyper.dtecologics.mixin;

import com.dtteam.dynamictrees.block.leaves.DynamicLeavesBlock;
import com.dtteam.dynamictrees.block.leaves.LeavesProperties;
import com.llamalad7.mixinextras.sugar.Local;
import maxhyper.dtecologics.DynamicTreesEcologics;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import samebutdifferent.ecologics.block.FloweringAzaleaLogBlock;
import samebutdifferent.ecologics.neoforge.EcologicsNeoForge;

@Mixin(EcologicsNeoForge.class)
public abstract class EcologicsNeoForgeMixin {

    @Inject(
            method = "onRightClick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
                    )
            )
    )
    private static void onRightClick(
            PlayerInteractEvent.RightClickBlock event,
            CallbackInfo ci,
            @Local(name = "state") @NotNull BlockState state,
            @Local(name = "direction") Direction direction
    ) {
        if (!(state.getBlock() instanceof DynamicLeavesBlock dynamicLeavesBlock)) {
            return;
        }

        @Nullable BlockState primitiveState = dynamicLeavesBlock.getLeavesProperties().getPrimitiveLeaves();

        //noinspection ConstantValue
        if (primitiveState == null || !primitiveState.is(Blocks.FLOWERING_AZALEA_LEAVES)) {
            return;
        }

        @Nullable LeavesProperties leavesProperties = DynamicTreesEcologics.getDynamicLeaves(Blocks.AZALEA_LEAVES);
        if (leavesProperties == null) {
            return;
        }

        BlockState dynamicState = leavesProperties.getDynamicLeavesState(state.getValue(DynamicLeavesBlock.DISTANCE));

        for (Property<?> property : state.getProperties()) {
            if (dynamicState.hasProperty(property)) {
                dynamicState = DynamicTreesEcologics.copyProperty(state, dynamicState, property);
            }
        }

        Player player = event.getEntity();
        InteractionHand hand = event.getHand();

        FloweringAzaleaLogBlock.shearAzalea(
                event.getLevel(),
                player,
                event.getPos(),
                event.getItemStack(),
                hand,
                direction,
                dynamicState
        );
        player.swing(hand, true);
    }

}
