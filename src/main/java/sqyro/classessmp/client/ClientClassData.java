package sqyro.classessmp.client;

import java.util.List;

public class ClientClassData {
    private final List<ClientAbility> Abilities;

    public ClientClassData(List<ClientAbility> Abilities) {
        this.Abilities = Abilities;
    }

    public List<ClientAbility> getAbilities() {
        return Abilities;
    }
}