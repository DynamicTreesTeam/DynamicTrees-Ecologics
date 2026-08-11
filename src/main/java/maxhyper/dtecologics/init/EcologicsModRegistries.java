package maxhyper.dtecologics.init;

import com.dtteam.dynamictrees.block.pod.Pod;
import com.dtteam.dynamictrees.event.TypeRegistryEvent;
import maxhyper.dtecologics.DynamicTreesEcologics;
import maxhyper.dtecologics.coconut.FallingCoconutPod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = DynamicTreesEcologics.MOD_ID)
public class EcologicsModRegistries {

    @SubscribeEvent
    public static void registerPodType(@NotNull final TypeRegistryEvent<Pod> event) {
        if (event.isEntryOfType(Pod.class)) {
            event.registerType(DynamicTreesEcologics.location("falling_coconut"), FallingCoconutPod.TYPE);
        }
    }

}
