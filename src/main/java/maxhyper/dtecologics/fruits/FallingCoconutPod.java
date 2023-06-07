package maxhyper.dtecologics.fruits;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.PodBlock;
import com.ferreusveritas.dynamictrees.systems.pod.Pod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FallingCoconutPod extends Pod {

    public static final TypedRegistry.EntryType<Pod> TYPE = TypedRegistry.newType(FallingCoconutPod::new);

    public FallingCoconutPod(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected PodBlock createBlock(BlockBehaviour.Properties properties) {
        return new FallingCoconutBlock(properties, this);
    }

}
