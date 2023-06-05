package maxhyper.dtecologics.cactus;

import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKitConfiguration;
import com.ferreusveritas.dynamictrees.growthlogic.context.DirectionManipulationContext;
import com.ferreusveritas.dynamictrees.growthlogic.context.DirectionSelectionContext;
import com.ferreusveritas.dynamictrees.growthlogic.context.PositionalSpeciesContext;
import com.ferreusveritas.dynamictrees.util.MathHelper;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class PricklyPearCactusLogic extends GrowthLogicKit {

    public PricklyPearCactusLogic(ResourceLocation registryName) {
        super(registryName);
    }

    public Direction selectNewDirection(GrowthLogicKitConfiguration configuration, DirectionSelectionContext context) {
        // Populate the direction probability map.
        final int[] probMap = configuration.populateDirectionProbabilityMap(
                new DirectionManipulationContext(context.level(), context.pos(), context.species(), context.branch(),
                        context.signal(), context.branch().getRadius(context.level().getBlockState(context.pos())),
                        new int[6])
        );

        // Select a direction from the probability map.
        final int choice = MathHelper.selectRandomFromDistribution(context.signal().rand, probMap);
        return Direction.values()[choice != -1 ? choice : 1]; // Default to up if it failed.
    }

}
