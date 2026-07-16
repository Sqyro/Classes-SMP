package sqyro.classessmp.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class IceParticle extends SingleQuadParticle {
    private final SpriteSet Sprites;

    private final float rotationSpeed;

    protected IceParticle(ClientLevel Level, double PosX, double PosY, double PosZ, double RotX, double RotY, double RotZ, SpriteSet Sprites) {
        super(Level, PosX, PosY, PosZ, RotX, RotY, RotZ, Sprites.get(0, 1));

        this.Sprites = Sprites;

        this.xd = RotX;
        this.yd = RotY;
        this.zd = RotZ;

        this.quadSize = 0.15F;

        this.lifetime = 20 + this.random.nextInt(10);

        this.gravity = 0.0F;
        this.friction = 0.96F;

        this.rotationSpeed = (this.random.nextBoolean() ? 1 : -1) * (0.05F + this.random.nextFloat() * 0.15F);

        this.roll = this.random.nextFloat() * ((float) Math.PI * 2);

        this.setSpriteFromAge(Sprites);
    }

    @Override
    public void tick() {
        super.tick();

        this.oRoll = this.roll;
        this.roll += rotationSpeed;

        this.setSpriteFromAge(this.Sprites);

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
            return new IceParticle(Level, PosX, PosY, PosZ, RotX, RotY, RotZ, Sprites);
        }
    }
}
