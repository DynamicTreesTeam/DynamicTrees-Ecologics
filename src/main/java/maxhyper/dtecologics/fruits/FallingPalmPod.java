package maxhyper.dtecologics.fruits;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.block.PodBlock;
import com.ferreusveritas.dynamictrees.systems.pod.Pod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FallingPalmPod extends Pod {

    public static final TypedRegistry.EntryType<Pod> TYPE = TypedRegistry.newType(FallingPalmPod::new);

    public FallingPalmPod(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected PodBlock createBlock(BlockBehaviour.Properties properties) {
        return new FallingPodBlock(properties, this);
    }

}
