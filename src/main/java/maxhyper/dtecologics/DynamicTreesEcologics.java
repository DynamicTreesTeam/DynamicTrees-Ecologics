package maxhyper.dtecologics;

import com.dtteam.dynamictrees.block.fruit.Fruit;
import com.dtteam.dynamictrees.block.leaves.LeavesProperties;
import com.dtteam.dynamictrees.block.pod.Pod;
import com.dtteam.dynamictrees.block.soil.SoilProperties;
import com.dtteam.dynamictrees.data.GatherDataHelper;
import com.dtteam.dynamictrees.registry.NeoForgeRegistryHandler;
import com.dtteam.dynamictrees.tree.family.Family;
import com.dtteam.dynamictrees.tree.species.Species;
import maxhyper.dtecologics.init.PlusModRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod(DynamicTreesEcologics.MOD_ID)
public class DynamicTreesEcologics {
    public static final String MOD_ID = "dtecologics";

    public DynamicTreesEcologics(@NotNull IEventBus modBus) {
        modBus.addListener(this::gatherData);

        if (ModList.get().isLoaded("dynamictreesplus")) {
            modBus.register(PlusModRegistries.class);
        }

        NeoForgeRegistryHandler.setup(MOD_ID, modBus);
    }

    private void gatherData(final GatherDataEvent event) {
        GatherDataHelper.gatherAllData(MOD_ID, event,
                SoilProperties.REGISTRY,
                Family.REGISTRY,
                Species.REGISTRY,
                Fruit.REGISTRY,
                Pod.REGISTRY,
                LeavesProperties.REGISTRY
        );
    }

    @NotNull
    @Contract("_ -> new")
    public static ResourceLocation location(final String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    @Nullable
    public static LeavesProperties getDynamicLeaves(Block block) {
        for (LeavesProperties properties : LeavesProperties.REGISTRY) {
            @Nullable BlockState state = properties.getPrimitiveLeaves();

            //noinspection ConstantValue
            if (state != null && state.is(block)) {
                return properties;
            }
        }
        return null;
    }

    @NotNull
    public static <T extends Comparable<T>> BlockState copyProperty(
            @NotNull BlockState from,
            @NotNull BlockState to,
            Property<T> property
    ) {
        return to.setValue(property, from.getValue(property));
    }

}
