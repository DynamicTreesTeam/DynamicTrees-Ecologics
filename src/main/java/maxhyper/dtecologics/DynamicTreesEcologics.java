package maxhyper.dtecologics;

import com.ferreusveritas.dynamictrees.api.GatherDataHelper;
import com.ferreusveritas.dynamictrees.api.registry.RegistryHandler;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.block.rooty.SoilProperties;
import com.ferreusveritas.dynamictrees.systems.pod.Pod;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DynamicTreesEcologics.MOD_ID)
public class DynamicTreesEcologics {
    public static final String MOD_ID = "dtecologics";

    public DynamicTreesEcologics() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::gatherData);

        MinecraftForge.EVENT_BUS.register(this);

        RegistryHandler.setup(MOD_ID);

        DTEcologicsRegistries.SOUNDS.register(modEventBus);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
//        Block glowSapling = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MOD_ID, "glow_shroom_sapling"));
//        assert glowSapling != null;
//        ItemBlockRenderTypes.setRenderLayer(glowSapling, RenderType.translucent());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        //DTEcologicsRegistries.setup();

//        for (BlossomTreeConfig config : BlossomTreesModule.trees.values()) {
//            config.biomeConfig = CompoundBiomeConfig.fromBiomeTypes(false);
//        }
    }

    private void gatherData(final GatherDataEvent event) {
        GatherDataHelper.gatherAllData(MOD_ID, event,
                SoilProperties.REGISTRY,
                Family.REGISTRY,
                Species.REGISTRY,
                Pod.REGISTRY,
                LeavesProperties.REGISTRY);
    }

    public static ResourceLocation location(final String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}
