package maxhyper.dtecologics.init;

import com.ferreusveritas.dynamictrees.api.registry.RegistryEvent;
import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import com.ferreusveritas.dynamictreesplus.systems.thicknesslogic.CactusThicknessLogic;
import maxhyper.dtecologics.DynamicTreesEcologics;
import maxhyper.dtecologics.cactus.DTEcologicsGrowthLogicKits;
import maxhyper.dtecologics.cactus.DTEcologicsThicknessLogicKits;
import maxhyper.dtecologics.cactus.PricklyPearCactusSpecies;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlusRegistries {

    @SubscribeEvent
    public static void registerCactusThicknessLogic(final RegistryEvent<CactusThicknessLogic> event) {
        DTEcologicsThicknessLogicKits.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerGrowthLogic(final RegistryEvent<GrowthLogicKit> event) {
        DTEcologicsGrowthLogicKits.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerSpeciesType(final TypeRegistryEvent<Species> event) {
        event.registerType(DynamicTreesEcologics.location("prickly_pear_cactus"), PricklyPearCactusSpecies.TYPE);
    }

}
