package maxhyper.dtecologics.coconut;

import com.dtteam.dtaddon_lib.fruits.FallingPalmPod;
import com.dtteam.dynamictrees.api.registry.TypedRegistry;
import com.dtteam.dynamictrees.block.pod.Pod;
import com.dtteam.dynamictrees.block.pod.PodBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class FallingCoconutPod extends FallingPalmPod {

    public static final TypedRegistry.EntryType<Pod> TYPE = TypedRegistry.newType(FallingCoconutPod::new);

    public FallingCoconutPod(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected PodBlock createBlock(Block.Properties properties) {
        return new FallingCoconutBlock(properties, this);
    }

}
