package sqyro.classessmp.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class IceStormParticle extends SingleQuadParticle {
    private final SpriteSet Sprites;

    protected IceStormParticle(ClientLevel Level, double PosX, double PosY, double PosZ, double RotX, double RotY, double RotZ, SpriteSet Sprites) {
        super(Level, PosX, PosY, PosZ, RotX, RotY, RotZ, Sprites.get(0, 1));

        this.Sprites = Sprites;

        this.xd = RotX;
        this.yd = RotY;
        this.zd = RotZ;

        this.quadSize = 0.15F;

        this.lifetime = 10 + this.random.nextInt(10);

        this.gravity = 0.0F;
        this.friction = 0.98F;

        this.setSpriteFromAge(Sprites);
    }

    @Override
    public void tick() {
        super.tick();

        this.setSpriteFromAge(this.Sprites);

        this.xd += (this.random.nextDouble() - 0.5) * 0.02;
        this.zd += (this.random.nextDouble() - 0.5) * 0.02;

        this.xd *= 0.98;
        this.zd *= 0.98;

        if (this.age > this.lifetime - 5) {
            this.alpha = (this.lifetime - this.age) / 5.0F;
        }
    }

    @Override
    protected Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet Sprites;

        public Provider(SpriteSet Sprites) {
            this.Sprites = Sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType Type, ClientLevel Level, double PosX, double PosY, double PosZ, double RotX, double RotY, double RotZ, RandomSource Random) {
            return new IceStormParticle(Level, PosX, PosY, PosZ, RotX, RotY, RotZ, Sprites);
        }
    }
}
