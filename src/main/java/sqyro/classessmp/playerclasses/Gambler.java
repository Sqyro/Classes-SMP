package sqyro.classessmp.playerclasses;

import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import sqyro.classessmp.ClassesSMP;
import sqyro.classessmp.core.PlayerClass;
import sqyro.classessmp.core.SavedData.PlayerClassSavedDataGetter;
import sqyro.classessmp.network.ClassesNetworking;
import sqyro.classessmp.particle.ClassesParticles;
import sqyro.classessmp.sounds.ClassesSounds;

public class Gambler extends PlayerClass {
    private static final Identifier DAMAGE_MODIFIER_ID = Identifier.fromNamespaceAndPath(ClassesSMP.MOD_ID, "gambler_damage");

    private int bonusLevel;
    public final int MIN_BONUS_DAMAGE_LEVEL = -5;
    public final int MAX_BONUS_DAMAGE_LEVEL = 5;

    public final int JACKPOT_EXTRA_DAMAGE = 5;

    private int currentRoll;

    public Gambler(ServerPlayer Player) {
        super(Player);
        bonusLevel = PlayerClassSavedDataGetter.get(Player.level()).getGamblerLevel(Player.getUUID());
    }

    @Override
    public String getID() {
        return "gambler";
    }

    @Override
    public void onTick() {

    }

    @Override
    public void onRespawn() {

    }

    @Override
    public void onKeybind1() {

    }

    @Override
    public void onKeybind2() {

    }

    public int rollDamageModifier() {
        int JackpotDamageModifier = getMaxModifier();
        int NormalDamageModifier = Player.getRandom().nextInt(getMinModifier(), getMaxModifier() - JACKPOT_EXTRA_DAMAGE);

        int RandomJackpot = Player.getRandom().nextInt(getMinModifier(), getMaxModifier() - JACKPOT_EXTRA_DAMAGE + 1);
        if (RandomJackpot == getMaxModifier() - JACKPOT_EXTRA_DAMAGE + 1) {
            return JackpotDamageModifier;
        } else {
            return NormalDamageModifier;
        }
    }

    public void onKill() {
        if (bonusLevel < MAX_BONUS_DAMAGE_LEVEL) {
            setBonusLevel(bonusLevel + 1);
            ClassesSMP.LOGGER.info("{} of class {} leveled up bonus damage (Level {} -> {})", Player.getName().getString(), this.getID(), bonusLevel - 1, bonusLevel);
        }
    }

    public void setBonusLevel(int level) {
        bonusLevel = Mth.clamp(level, MIN_BONUS_DAMAGE_LEVEL, MAX_BONUS_DAMAGE_LEVEL);

        PlayerClassSavedDataGetter.get(Player.level()).setGamblerLevel(Player.getUUID(), bonusLevel);

        ClassesNetworking.sendGamblerLevel(Player, bonusLevel);
    }

    public int getBonusLevel() {
        return bonusLevel;
    }

    private int getMinModifier() {
        if (bonusLevel < 0) {
            return -10 + bonusLevel;
        } else {
            return -10;
        }
    }

    private int getMaxModifier() {
        if (bonusLevel > 0) {
            return 20 + bonusLevel;
        } else {
            return 20;
        }
    }

    public void beginAttack(Entity Target) {
        if (!(Target instanceof LivingEntity)) {
            return;
        }

        AttributeInstance attackDamage = Player.getAttribute(Attributes.ATTACK_DAMAGE);

        if (attackDamage == null) {
            return;
        }

        attackDamage.removeModifier(DAMAGE_MODIFIER_ID);

        currentRoll = rollDamageModifier();
        ClassesNetworking.sendGamblerRoll(Player, currentRoll);

        ClassesSMP.LOGGER.info("{} of class {} rolled {} extra damage (Level {})", Player.getName().getString(), this.getID(), currentRoll, bonusLevel);

        if (currentRoll == this.getMinModifier()) {
            Player.level().playSound(null, Target.getX(), Target.getY(), Target.getZ(), ClassesSounds.GAMBLER_LOOSE, SoundSource.PLAYERS, 1f, 1f);
        } else if (currentRoll == this.getMaxModifier()) {
            Player.level().playSound(null, Target.getX(), Target.getY(), Target.getZ(), ClassesSounds.GAMBLER_JACKPOT, SoundSource.PLAYERS, 1f, 1f);
            spawnJackpotParticles(Target);
        }
        attackDamage.addTransientModifier(new AttributeModifier(DAMAGE_MODIFIER_ID, currentRoll, AttributeModifier.Operation.ADD_VALUE));
    }

    public void endAttack() {
        AttributeInstance attackDamage = Player.getAttribute(Attributes.ATTACK_DAMAGE);

        if (attackDamage != null) {
            attackDamage.removeModifier(DAMAGE_MODIFIER_ID);
        }
    }

    public void onDamageDealt(float Damage) {
        ClassesSMP.LOGGER.info("{} of class {} dealt {} damage", Player.getName().getString(), this.getID(), Damage);
    }

    private void spawnJackpotParticles(Entity Target) {
        ServerLevel Level = Player.level();
        Level.sendParticles(ClassesParticles.DICE_PARTICLE, Target.getX(), Target.getY() + Target.getBbHeight() / 2, Target.getZ(), 100, 0.5, 0.5, 0.5, 0.1);
    }
}