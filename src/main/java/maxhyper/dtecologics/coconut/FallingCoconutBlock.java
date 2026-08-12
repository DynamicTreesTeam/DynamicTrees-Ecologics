package maxhyper.dtecologics.coconut;

import com.dtteam.dtaddon_lib.blocks.fruit.FallingPodBlock;
import com.dtteam.dtaddon_lib.blocks.fruit.IFallingFruit;
import com.dtteam.dtaddon_lib.init.DTAddonLibRegistries;
import com.dtteam.dynamictrees.block.pod.Pod;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import samebutdifferent.ecologics.entity.CoconutCrab;
import samebutdifferent.ecologics.platform.ConfigPlatformHelper;
import samebutdifferent.ecologics.registry.ModEntityTypes;
import samebutdifferent.ecologics.registry.ModSoundEvents;

public class FallingCoconutBlock extends FallingPodBlock implements Fallable {

    public FallingCoconutBlock(Properties properties, Pod pod) {
        super(properties, pod);
    }

    @NotNull
    public VoxelShape getShape(@NotNull BlockState state) {
        return pod.getBlockShape(state.getValue(FACING), getAge(state));
    }

    @NotNull
    public AABB getBoundingBox(@NotNull FallingBlockEntity fallingBlockEntity) {
        return getShape(fallingBlockEntity.getBlockState())
                .bounds()
                .move(
                        fallingBlockEntity.getX() - 0.5,
                        fallingBlockEntity.getY(),
                        fallingBlockEntity.getZ() - 0.5
                );
    }

    @Override
    public FallingBlockEntity getFallingEntity(Level level, BlockPos pos, BlockState state) {
        if (getAge(state) != getMaxAge() || level.random.nextDouble() > ConfigPlatformHelper.coconutCrabSpawnChance()) {
            return super.getFallingEntity(level, pos, state);
        }

        return new FallingBlockEntity(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, state) {

            @Override
            public boolean causeFallDamage(float pFallDistance, float pMultiplier, @NotNull DamageSource pSource) {
                int i = (int)Math.ceil(pFallDistance - 1.0F);
                if (i <= 0) {
                    return false;
                }

                Level level = level();

                for (Entity entity : level.getEntities(this, this.getBoundingBox())) {
                    if (!(entity instanceof LivingEntity)) {
                        continue;
                    }

                    entity.hurt(
                            getDamageSource(level),
                            (float)Math.min(Math.floor(i * IFallingFruit.fallDamageAmount), IFallingFruit.fallDamageMax) * pMultiplier
                    );
                    level.playSound(
                            null, getX(), getY(), getZ(),
                            DTAddonLibRegistries.FRUIT_BONK.get(),
                            SoundSource.BLOCKS,
                            1.0F, 1.0F
                    );
                }

                return false;
            }

            @Override
            public ItemEntity spawnAtLocation(@NotNull ItemStack pStack, float pOffsetY) {
                return null;
            }

            @Override
            public void callOnBrokenAfterFall(@NotNull Block block, @NotNull BlockPos pos) {
                super.callOnBrokenAfterFall(block, pos);

                Level level = level();

                CoconutCrab coconutCrab = ModEntityTypes.COCONUT_CRAB.create(level);
                if (coconutCrab == null) {
                    return;
                }

                AABB bb = getBoundingBox();

                double x = (bb.minX + bb.maxX) / 2.0;
                double z = (bb.minZ + bb.maxZ) / 2.0;

                switch (getBlockState().getValue(FACING)) {
                    case NORTH -> z = bb.maxZ;
                    case SOUTH -> z = bb.minZ;
                    case WEST  -> x = bb.maxX;
                    case EAST  -> x = bb.minX;
                }

                coconutCrab.setPos(x, bb.minY, z);
                level.addFreshEntity(coconutCrab);
            }

        };
    }

    @Override
    public void onBrokenAfterFall(
            @NotNull Level level,
            @NotNull BlockPos pos,
            @NotNull FallingBlockEntity fallingBlock
    ) {
        level.playSound(
                null, pos,
                ModSoundEvents.COCONUT_SMASH,
                SoundSource.BLOCKS,
                0.7f,
                0.9f + level.getRandom().nextFloat() * 0.2f
        );
    }

    @NotNull
    @Override
    public DamageSource getFallDamageSource(@NotNull Entity entity) {
        return getDamageSource(entity.level());
    }

}
