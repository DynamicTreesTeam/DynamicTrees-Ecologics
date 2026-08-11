package maxhyper.dtecologics.cactus;

import com.dtteam.dtaddon_lib.growthlogic.DTAddonLibGrowthLogicKits;
import com.dtteam.dynamictrees.api.registry.TypedRegistry;
import com.dtteam.dynamictrees.block.leaves.LeavesProperties;
import com.dtteam.dynamictrees.tree.family.Family;
import com.dtteam.dynamictrees.tree.species.Species;
import com.dtteam.dynamictreesplus.tree.CactusSpecies;
import maxhyper.dtecologics.DynamicTreesEcologics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PricklyPearCactusSpecies extends CactusSpecies {

    public static final TypedRegistry.EntryType<Species> TYPE = createDefaultType(PricklyPearCactusSpecies::new);

    @NotNull
    @Override
    public Species setPreReloadDefaults() {
        return super.setPreReloadDefaults().setGrowthLogicKit(DTAddonLibGrowthLogicKits.PRICKLY_PEAR);
    }

    public PricklyPearCactusSpecies(ResourceLocation name, Family family, LeavesProperties leavesProperties) {
        super(name, family, leavesProperties);
    }

    @Override
    public int getProbabilityForCurrentDir() {
        return 2;
    }

    @NotNull
    @Override
    public ResourceLocation getSaplingSmartModelLocation() {
        return DynamicTreesEcologics.location("block/"+this.getRegistryName().getPath()+"_sapling");
    }

}
