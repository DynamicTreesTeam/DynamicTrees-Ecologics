package maxhyper.dtecologics.init;

import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import maxhyper.dtecologics.DynamicTreesEcologics;
import maxhyper.dtecologics.cactus.PricklyPearCactusSpecies;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlusRegistries {

    @SubscribeEvent
    public static void registerSpeciesType(final TypeRegistryEvent<Species> event) {
        event.registerType(DynamicTreesEcologics.location("prickly_pear_cactus"), PricklyPearCactusSpecies.TYPE);
    }

}
