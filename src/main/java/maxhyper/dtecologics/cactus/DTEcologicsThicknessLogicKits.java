package maxhyper.dtecologics.cactus;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.registry.Registry;
import com.ferreusveritas.dynamictrees.systems.GrowSignal;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeature;
import com.ferreusveritas.dynamictreesplus.DynamicTreesPlus;
import com.ferreusveritas.dynamictreesplus.block.CactusBranchBlock;
import com.ferreusveritas.dynamictreesplus.systems.thicknesslogic.CactusThicknessLogic;
import maxhyper.dtecologics.DynamicTreesEcologics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class DTEcologicsThicknessLogicKits {

    public static void register(final Registry<CactusThicknessLogic> registry) {
        registry.registerAll(PRICKLY_PEAR);
    }

    public static final CactusThicknessLogic PRICKLY_PEAR = new CactusThicknessLogic(DynamicTreesEcologics.location("prickly_pear")) {
        @Override
        public CactusBranchBlock.CactusThickness thicknessAfterGrowthSignal(Level world, BlockPos pos, GrowSignal signal, CactusBranchBlock.CactusThickness currentThickness) {
            BlockState upState = world.getBlockState(pos.above());
            BlockState downState = world.getBlockState(pos.below());
            return (upState.getBlock() instanceof CactusBranchBlock && downState.getBlock() instanceof CactusBranchBlock) ? CactusBranchBlock.CactusThickness.TRUNK : CactusBranchBlock.CactusThickness.BRANCH;
        }

        @Override
        public CactusBranchBlock.CactusThickness thicknessForBranchPlaced(LevelAccessor world, BlockPos pos, boolean isLast) {
            BlockState downState = world.getBlockState(pos.below());
            if (TreeHelper.isRooty(downState) || isLast)
                return CactusBranchBlock.CactusThickness.BRANCH;
            return CactusBranchBlock.CactusThickness.TRUNK;
        }
    };

}
