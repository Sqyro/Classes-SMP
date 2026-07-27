package sqyro.classessmp.client;

import net.minecraft.resources.Identifier;

public class ClientAbility {
    private final String ID;
    private final Identifier Background;
    private final Identifier Color;
    private final int maxCooldown;

    public ClientAbility(String ID, Identifier Background, Identifier Color, int maxCooldown) {
        this.ID = ID;
        this.Background = Background;
        this.Color = Color;
        this.maxCooldown = maxCooldown;
    }

    public String getID() {
        return ID;
    }

    public Identifier getBackground() {
        return Background;
    }

    public Identifier getColor() {
        return Color;
    }

    public int getMaxCooldown() {
        return maxCooldown;
    }


}