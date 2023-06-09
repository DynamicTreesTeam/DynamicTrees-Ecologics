package maxhyper.dtecologics.init;

import com.ferreusveritas.dynamictrees.api.registry.RegistryEvent;
import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeature;
import com.ferreusveritas.dynamictrees.systems.pod.Pod;
import maxhyper.dtecologics.DynamicTreesEcologics;
import maxhyper.dtecologics.fruits.FallingCoconutPod;
import maxhyper.dtecologics.genfeatures.DTEcologicsGenFeatures;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DTEcologicsRegistries {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DynamicTreesEcologics.MOD_ID);

    public static final RegistryObject<SoundEvent> FRUIT_BONK = registerSound("falling_fruit.bonk");

    public static RegistryObject<SoundEvent> registerSound (String name){
        return SOUNDS.register(name, ()-> new SoundEvent(DynamicTreesEcologics.location(name)));
    }

    @SubscribeEvent
    public static void registerPodType(final TypeRegistryEvent<Pod> event) {
        event.registerType(DynamicTreesEcologics.location("falling_palm"), FallingCoconutPod.TYPE);
    }

    @SubscribeEvent
    public static void onGenFeatureRegistry (final RegistryEvent<GenFeature> event) {
        DTEcologicsGenFeatures.register(event.getRegistry());
    }

}
