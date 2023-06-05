package maxhyper.dtecologics;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BlossomLeavesProperties extends LeavesProperties {

    public static final TypedRegistry.EntryType<LeavesProperties> TYPE = TypedRegistry.newType(BlossomLeavesProperties::new);

    public BlossomLeavesProperties(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected DynamicLeavesBlock createDynamicLeaves(BlockBehaviour.Properties properties) {
        return new DynamicLeavesBlock(this, properties) {
            @Override
            @OnlyIn(Dist.CLIENT)
            public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
                if (level.isEmptyBlock(pos.below()) && random.nextInt(5) == 0) {
                    double windStrength = 5.0D + Math.cos((double) level.getGameTime() / 2000.0D) * 2.0D;
                    double windX = Math.cos((double) level.getGameTime() / 1200.0D) * windStrength;
                    double windZ = Math.sin((double) level.getGameTime() / 1000.0D) * windStrength;
                    level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, windX, -1.0D, windZ);
                }
            }
        };
    }

}
