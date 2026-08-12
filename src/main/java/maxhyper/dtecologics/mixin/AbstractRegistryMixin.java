package maxhyper.dtecologics.mixin;

import com.dtteam.dynamictrees.api.registry.AbstractRegistry;
import com.dtteam.dynamictrees.block.leaves.LeavesProperties;
import maxhyper.dtecologics.DynamicTreesEcologics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractRegistry.class)
public abstract class AbstractRegistryMixin {

    @Inject(
            method = "lock",
            at = @At("RETURN")
    )
    private void afterLock(CallbackInfo ci) {
        //noinspection ConstantValue
        if ((Object) this == LeavesProperties.REGISTRY) {
            DynamicTreesEcologics.updateAzaleaProperties();
        }
    }

}
