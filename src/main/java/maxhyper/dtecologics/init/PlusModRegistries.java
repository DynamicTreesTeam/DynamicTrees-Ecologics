package maxhyper.dtecologics.init;

import com.dtteam.dynamictrees.event.TypeRegistryEvent;
import com.dtteam.dynamictrees.tree.species.Species;
import maxhyper.dtecologics.DynamicTreesEcologics;
import maxhyper.dtecologics.cactus.PricklyPearCactusSpecies;
import net.neoforged.bus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class PlusModRegistries {

    @SubscribeEvent
    public static void registerSpeciesType(@NotNull final TypeRegistryEvent<Species> event) {
        if (event.isEntryOfType(Species.class)) {
            event.registerType(DynamicTreesEcologics.location("prickly_pear_cactus"), PricklyPearCactusSpecies.TYPE);
        }
    }

}
