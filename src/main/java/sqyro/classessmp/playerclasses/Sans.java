package sqyro.classessmp.playerclasses;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.network.ClassesNetworking;
import sqyro.classessmp.sounds.ClassesSounds;

public class Sans extends PlayerClass {
    public static final Identifier HEALTH_MODIFIER_ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "sans_health");

    public static final String BUT_IT_REFUSED_ID = "but_it_refused";
    public static final int BUT_IT_REFUSED_COOLDOWN = 7000;
    public static final int BUT_IT_REFUSED_DURATION = 3520;
    public static final int BUT_IT_REFUSED_CHANCE = 95;
    public static final int BUT_IT_REFUSED_HEALTH_REDUCTION = -19;

    public static final String SANS_DASH_ID = "sans_dash";
    public static final int SANS_DASH_COOLDOWN = 100;
    public static final float SANS_DASH_STRENGTH = 1.3f;

    private boolean dashFallImmunity = false;
    public boolean isDodging = false;
    private int isDodgingTimer = 0;

    public Sans(ServerPlayer Player) {
        super(Player);
    }

    @Override
    public String getID() {
        return "sans";
    }

    @Override
    public void onTick() {
        if (isDodging) {
            isDodgingTimer++;
        }

        if (isDodgingTimer >= BUT_IT_REFUSED_DURATION) {
            isDodging = false;
            isDodgingTimer = 0;


            AttributeInstance health = Player.getAttribute(Attributes.MAX_HEALTH);
            AttributeModifier modifier = health.getModifier(HEALTH_MODIFIER_ID);

            if (modifier != null) {
                health.removeModifier(modifier);
            }

            ClassesNetworking.sendButItRefusedStop(Player);
        }

        if (dashFallImmunity) {
            Player.resetFallDistance();

            if (Player.onGround()) {
                dashFallImmunity = false;
            }
        }
    }

    @Override
    public void onRespawn() {

    }

    @Override
    public void onKill(Entity Target) {

    }

    @Override
    public void onKeybind1() {
        if (isOnCooldown(BUT_IT_REFUSED_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate but it refused, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(BUT_IT_REFUSED_ID));
            return;
        }

        ClassesSMP.LOGGER.info("{} of class: {} activated but it refused", Player.getName().getString(), this.getID());
        setCooldown(BUT_IT_REFUSED_ID, BUT_IT_REFUSED_COOLDOWN);

        this.isDodging = true;
        this.isDodgingTimer = 0;

        AttributeInstance health = Player.getAttribute(Attributes.MAX_HEALTH);

        if (health != null && health.getModifier(HEALTH_MODIFIER_ID) == null) {
            health.addPermanentModifier(new AttributeModifier(HEALTH_MODIFIER_ID, BUT_IT_REFUSED_HEALTH_REDUCTION, AttributeModifier.Operation.ADD_VALUE));
        }

        ClassesNetworking.sendButItRefusedStart(Player);
    }

    public void dodgeAttack(DamageSource source) {
        Entity attacker = source.getEntity();

        if (attacker == null) {
            return;
        }

        Vec3 direction = Player.position().subtract(attacker.position()).normalize();
        Vec3 side = new Vec3(-direction.z, 0, direction.x).normalize();

        double strength = 1.0;

        Player.push(side.x * strength, 0.15, side.z * strength);
        Player.level().playSound(null, Player.getX(), Player.getY(), Player.getZ(), ClassesSounds.SANS_DODGE, SoundSource.PLAYERS);

        Player.hurtMarked = true;
    }

    @Override
    public void onKeybind2() {
        if (isOnCooldown(SANS_DASH_ID)) {
            ClassesSMP.LOGGER.info("{} of class: {} tried to activate Sans Dash, but it was on cooldown: {}", Player.getName().getString(), this.getID(), this.getCooldownTicks(SANS_DASH_ID));
            return;
        }

        ClassesSMP.LOGGER.info("{} of class: {} activated Sans Dash", Player.getName().getString(), this.getID());
        setCooldown(SANS_DASH_ID, SANS_DASH_COOLDOWN);

        Vec3 direction = Player.getLookAngle().normalize();
        Player.push(direction.x * SANS_DASH_STRENGTH, direction.y * SANS_DASH_STRENGTH * 0.6f + 0.3f, direction.z * SANS_DASH_STRENGTH);

        Player.level().playSound(null, Player.getX(), Player.getY(), Player.getZ(), SoundEvents.SNIFFER_SNIFFING, SoundSource.PLAYERS);
        Player.level().sendParticles(ParticleTypes.EXPLOSION, Player.getX(), Player.getY(), Player.getZ(), 10, 0.5, 0.5, 0.5, 0.1);

        Player.hurtMarked = true;
        this.dashFallImmunity = true;
    }


    @Override
    public void onKeybind3() {

    }

    @Override
    public void beginAttack(Entity Target) {

    }

    @Override
    public void endAttack() {

    }
}