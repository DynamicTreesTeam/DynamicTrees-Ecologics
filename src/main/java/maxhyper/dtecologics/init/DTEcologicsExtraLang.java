package maxhyper.dtecologics.init;

import com.ferreusveritas.dynamictrees.api.data.Generator;
import com.ferreusveritas.dynamictrees.data.provider.DTLangProvider;

public class DTEcologicsExtraLang implements Generator<DTLangProvider, String> {
    public DTEcologicsExtraLang() {
    }

    public void generate(DTLangProvider provider, String input, Generator.Dependencies dependencies) {
        provider.add("subtitle.dtecologics.falling_fruit.bonk", "Player bonked by fruit");
        provider.add("death.attack.dtecologics.falling_fruit.coconut", "%1$s was bonked in the head by a falling Coconut");
    }

    public Generator.Dependencies gatherDependencies(String input) {
        return new Generator.Dependencies();
    }
}
