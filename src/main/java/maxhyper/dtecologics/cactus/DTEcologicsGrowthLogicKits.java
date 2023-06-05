package maxhyper.dtecologics.cactus;

import com.ferreusveritas.dynamictrees.api.registry.Registry;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import maxhyper.dtecologics.DynamicTreesEcologics;

public class DTEcologicsGrowthLogicKits {

    public static final GrowthLogicKit PRICKLY_PEAR = new PricklyPearCactusLogic(DynamicTreesEcologics.location("prickly_pear"));

    public static void register(final Registry<GrowthLogicKit> registry) {
        registry.registerAll(PRICKLY_PEAR);
    }

}
