package maxhyper.dtecologics.cactus;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.registry.Registry;
import com.ferreusveritas.dynamictrees.systems.GrowSignal;
import com.ferreusveritas.dynamictrees.util.CoordUtils;
import com.ferreusveritas.dynamictreesplus.block.CactusBranchBlock;
import com.ferreusveritas.dynamictreesplus.systems.thicknesslogic.CactusThicknessLogic;
import maxhyper.dtecologics.DynamicTreesEcologics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
//            for (Direction dir : Direction.values()){
//                BlockState offState = world.getBlockState(pos.offset(dir.getNormal()));
//                if (offState.hasProperty(CactusBranchBlock.TRUNK_TYPE) &&
//                        offState.getValue(CactusBranchBlock.TRUNK_TYPE) == CactusBranchBlock.CactusThickness.TRUNK)
//                    return CactusBranchBlock.CactusThickness.BRANCH;
//            }
            if (CoordUtils.coordHashCode(pos, 3) % 2 == 0)
                return CactusBranchBlock.CactusThickness.TRUNK;
            else return CactusBranchBlock.CactusThickness.BRANCH;
        }

        @Override
        public CactusBranchBlock.CactusThickness thicknessForBranchPlaced(LevelAccessor world, BlockPos pos, boolean isLast) {
            if (TreeHelper.isRooty(world.getBlockState(pos.below())))
                return CactusBranchBlock.CactusThickness.TRUNK;
            return  CactusBranchBlock.CactusThickness.BRANCH;
        }
    };

}
