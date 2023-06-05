package maxhyper.dtecologics.genfeatures;

import com.ferreusveritas.dynamictrees.api.registry.Registry;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeature;
import maxhyper.dtecologics.DynamicTreesEcologics;

public class DTEcologicsGenFeatures {

    public static final GenFeature PALM_FRUIT = new PalmFruitGenFeature(DynamicTreesEcologics.location("palm_fruit"));

    public static void register(final Registry<GenFeature> registry) {
        registry.registerAll(PALM_FRUIT);
    }

}
