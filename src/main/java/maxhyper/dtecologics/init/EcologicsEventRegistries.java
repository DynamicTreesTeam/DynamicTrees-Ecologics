package maxhyper.dtecologics.init;

import maxhyper.dtecologics.DynamicTreesEcologics;
import maxhyper.dtecologics.coconut.FallingCoconutBlock;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid = DynamicTreesEcologics.MOD_ID)
public class EcologicsEventRegistries {

    @SubscribeEvent
    public static void onEntitySize(@NotNull final EntityEvent.Size event) {
        if (!(event.getEntity() instanceof FallingBlockEntity fallingBlock)) {
            return;
        }

        @Nullable BlockState blockState = fallingBlock.getBlockState();

        //noinspection ConstantValue
        if (blockState == null || !(blockState.getBlock() instanceof FallingCoconutBlock coconutBlock)) {
            return;
        }

        AABB bounds = coconutBlock.getShape(blockState).bounds();

        event.setNewSize(EntityDimensions.fixed(
                (float) Math.max(bounds.getXsize(), bounds.getZsize()),
                (float) bounds.getYsize()
        ));
    }

}
