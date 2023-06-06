package maxhyper.dtecologics.cactus;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import com.ferreusveritas.dynamictreesplus.DynamicTreesPlus;
import com.ferreusveritas.dynamictreesplus.tree.CactusSpecies;
import maxhyper.dtecologics.DynamicTreesEcologics;
import net.minecraft.resources.ResourceLocation;

public class PricklyPearCactusSpecies extends CactusSpecies {

    public static final TypedRegistry.EntryType<Species> TYPE = createDefaultType(PricklyPearCactusSpecies::new);

    @Override
    public Species setPreReloadDefaults() {
        return super.setPreReloadDefaults().setGrowthLogicKit(GrowthLogicKit.DEFAULT);
    }

    public PricklyPearCactusSpecies(ResourceLocation name, Family family, LeavesProperties leavesProperties) {
        super(name, family, leavesProperties);
    }

    @Override
    public int getProbabilityForCurrentDir() {
        return 2;
    }

    @Override
    public ResourceLocation getSaplingSmartModelLocation() {
        return DynamicTreesEcologics.location("block/"+this.getRegistryName().getPath()+"_sapling");
    }

}
