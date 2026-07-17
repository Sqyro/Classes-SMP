package sqyro.classessmp.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class DiceParticle extends SingleQuadParticle {
    private final SpriteSet Sprites;

    protected DiceParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, SpriteSet Sprites) {
        super(level, x, y, z, xd, yd, zd, Sprites.get(0, 1));

        this.Sprites = Sprites;

        this.rCol = level.random.nextFloat();
        this.gCol = level.random.nextFloat();
        this.bCol = level.random.nextFloat();

        this.lifetime = 20 + level.random.nextInt(20);

        this.quadSize = 0.15f + level.random.nextFloat() * 0.1f;

        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
    }

    @Override
    protected Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();

        this.yd -= 0.03;

        this.setSpriteFromAge(this.Sprites);

        if (this.age > this.lifetime - 5) {
            this.alpha = (this.lifetime - this.age) / 5.0F;
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet Sprites;

        public Provider(SpriteSet Sprites) {
            this.Sprites = Sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd, RandomSource Random) {
            return new DiceParticle(level, x, y, z, xd, yd, zd, Sprites);
        }
    }
}
