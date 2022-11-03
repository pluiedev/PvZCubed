package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.HypnoSummonerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.HypnoZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.*;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.hypnoshroom.HypnoshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.potatomine.PotatomineEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.potatomine.UnarmedPotatomineEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.puffshroom.PuffshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;
import java.util.function.Predicate;

public class DancingZombieEntity extends SpellcastingIllagerEntity implements IAnimatable {
    private MobEntity owner;
    public AnimationFactory factory = new AnimationFactory(this);
    private String controllerName = "walkingcontroller";
    private static final Predicate<Difficulty> DOOR_BREAK_DIFFICULTY_CHECKER;
    private final BreakDoorGoal breakDoorsGoal;
    private boolean canBreakDoors;
    private boolean isAggro;
    private boolean dancing;

    public DancingZombieEntity(EntityType<? extends DancingZombieEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.breakDoorsGoal = new BreakDoorGoal(this, DOOR_BREAK_DIFFICULTY_CHECKER);
        this.experiencePoints = 12;
        this.isAggro = false;
        this.dancing = false;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (!this.dancing) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("dancingzombie.moonwalking", true));
        } else  {
            if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("dancingzombie.dancewalk", true));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("dancingzombie.dancing", true));
            }
        }
        return PlayState.CONTINUE;
    }

    public DancingZombieEntity(World world) {
        this(PvZEntity.DANCINGZOMBIE, world);
    }

        protected void initGoals() {
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
            this.targetSelector.add(6, new RevengeGoal(this, new Class[0]));
        this.initCustomGoals();
    }

    @Override
    public void addBonusForWave(int wave, boolean unused) {

    }

    protected void initCustomGoals() {
        this.targetSelector.add(2, new DancingZombieEntity.TrackOwnerTargetGoal(this));
        this.goalSelector.add(1, new DancingZombieEntity.summonZombieGoal());
        this.goalSelector.add(1, new PvZombieAttackGoal(this, 1.0D, true));
		this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
		this.targetSelector.add(1, new TargetGoal<>(this, PuffshroomEntity.class, false, true));
		this.targetSelector.add(1, new TargetGoal<>(this, UnarmedPotatomineEntity.class, false, true));
		this.targetSelector.add(1, new TargetGoal<>(this, PotatomineEntity.class, false, true));
		this.targetSelector.add(1, new TargetGoal<>(this, ReinforceEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, EnforceEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, ContainEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, HypnoshroomEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, EnchantEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, PlayerEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, AppeaseEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, PepperEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, WinterEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, BombardEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, AilmentEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, EnlightenEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, FilamentEntity.class, false, true));
		this.targetSelector.add(4, new TargetGoal<>(this, MerchantEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, IronGolemEntity.class, false, true));
		////////// Hypnotized Zombie targets ///////
		this.targetSelector.add(1, new TargetGoal<>(this, HypnoZombieEntity.class, false, true));
		this.targetSelector.add(1, new TargetGoal<>(this, HypnoSummonerEntity.class, false, true));
    }

    public static DefaultAttributeContainer.Builder createDancingZombieAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 50D);
    }

    public MobEntity getOwner() {
        return this.owner;
    }

    public void setOwner(MobEntity owner) {
        this.owner = owner;
    }

    public boolean canBreakDoors() {
        return this.canBreakDoors;
    }

    public void setCanBreakDoors(boolean canBreakDoors) {
        if (this.shouldBreakDoors() && NavigationConditions.hasMobNavigation(this)) {
            if (this.canBreakDoors != canBreakDoors) {
                this.canBreakDoors = canBreakDoors;
                ((MobNavigation)this.getNavigation()).setCanPathThroughDoors(canBreakDoors);
                if (canBreakDoors) {
                    this.goalSelector.add(1, this.breakDoorsGoal);
                } else {
                    this.goalSelector.remove(this.breakDoorsGoal);
                }
            }
        } else if (this.canBreakDoors) {
            this.goalSelector.remove(this.breakDoorsGoal);
            this.canBreakDoors = false;
        }

    }

    protected boolean shouldBreakDoors() {
        return true;
    }

    public boolean damage(DamageSource source, float amount) {
        if (!super.damage(source, amount)) {
            return false;
        } else if (!(this.world instanceof ServerWorld)) {
            return false;
        } else {
            ServerWorld serverWorld = (ServerWorld)this.world;
            LivingEntity livingEntity = this.getTarget();
            if (livingEntity == null && source.getAttacker() instanceof LivingEntity) {
                livingEntity = (LivingEntity)source.getAttacker();
            }

            if (this.getRecentDamageSource() == PvZCubed.HYPNO_DAMAGE) {
                this.playSound(PvZCubed.HYPNOTIZINGEVENT, 1.5F, 1.0F);
                HypnoDancingZombieEntity hypnoDancingZombieEntity = (HypnoDancingZombieEntity)PvZEntity.HYPNODANCINGZOMBIE.create(world);
                hypnoDancingZombieEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                hypnoDancingZombieEntity.initialize(serverWorld, world.getLocalDifficulty(hypnoDancingZombieEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData)null, (NbtCompound) null);
                hypnoDancingZombieEntity.setAiDisabled(this.isAiDisabled());
				hypnoDancingZombieEntity.setHealth(this.getHealth());
                if (this.hasCustomName()) {
                    hypnoDancingZombieEntity.setCustomName(this.getCustomName());
                    hypnoDancingZombieEntity.setCustomNameVisible(this.isCustomNameVisible());
                }

                hypnoDancingZombieEntity.setPersistent();
                serverWorld.spawnEntityAndPassengers(hypnoDancingZombieEntity);
                this.remove(RemovalReason.DISCARDED);
            }

            if (source.getAttacker() instanceof LivingEntity) {
                this.isAggro = true;
            }

            return true;
        }
    }

    protected SoundEvent getAmbientSound() {
        return PvZCubed.ZOMBIEMOANEVENT;
    }

    protected SoundEvent getHurtSound() {
        return PvZCubed.SILENCEVENET;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("CanBreakDoors", this.canBreakDoors());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setCanBreakDoors(nbt.getBoolean("CanBreakDoors"));
    }

	public boolean onKilledOther(ServerWorld serverWorld, LivingEntity livingEntity) {
		super.onKilledOther(serverWorld, livingEntity);
		boolean bl = super.onKilledOther(serverWorld, livingEntity);
		if ((serverWorld.getDifficulty() == Difficulty.NORMAL || serverWorld.getDifficulty() == Difficulty.HARD) && livingEntity instanceof VillagerEntity) {
			if (serverWorld.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
				return bl;
			}

			VillagerEntity villagerEntity = (VillagerEntity) livingEntity;
			ZombieVillagerEntity zombieVillagerEntity = (ZombieVillagerEntity) villagerEntity.convertTo(EntityType.ZOMBIE_VILLAGER, false);
			zombieVillagerEntity.initialize(serverWorld, serverWorld.getLocalDifficulty(zombieVillagerEntity.getBlockPos()), SpawnReason.CONVERSION, new ZombieEntity.ZombieData(false, true), (NbtCompound) null);
			zombieVillagerEntity.setVillagerData(villagerEntity.getVillagerData());
			zombieVillagerEntity.setGossipData((NbtElement) villagerEntity.getGossip().serialize(NbtOps.INSTANCE).getValue());
			zombieVillagerEntity.setOfferData(villagerEntity.getOffers().toNbt());
			zombieVillagerEntity.setXp(villagerEntity.getExperience());
			if (!this.isSilent()) {
				serverWorld.syncWorldEvent((PlayerEntity) null, 1026, this.getBlockPos(), 0);
			}
		}

		return bl;
	}

    @Override
    public void registerControllers(AnimationData data)
    {
        AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory()
    {
        return this.factory;
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        float f = difficulty.getClampedLocalDifficulty();

        if (entityData instanceof DancingZombieEntity.ZombieData) {
            DancingZombieEntity.ZombieData zombieData = (DancingZombieEntity.ZombieData)entityData;

            this.setCanBreakDoors(this.shouldBreakDoors() && this.random.nextFloat() < f * 0.1F);
        }
        return (EntityData)entityData;
    }

    @Override
    public SoundEvent getCelebratingSound() {
        return null;
    }

    public static boolean method_29936(Random random) {
        return random.nextFloat() < 0.05F;
    }

    static {
        DOOR_BREAK_DIFFICULTY_CHECKER = (difficulty) -> {
            return difficulty == Difficulty.HARD;
        };
    }

    public static class ZombieData implements EntityData {

        public ZombieData(boolean baby, boolean bl) {
        }
    }

    public static boolean canDancingZombieSpawn(EntityType<DancingZombieEntity> entity, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
        return pos.getY() > 55;
    }

    @Override
    public boolean canSpawn(WorldView worldreader) {
        return worldreader.doesNotIntersectEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
    }

    @Override
    public void onDeath(DamageSource source){

    }

    protected SoundEvent getCastSpellSound() {
        return PvZCubed.ENTITYRISINGEVENT;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.isClient && this.isSpellcasting()) {
            this.world.addParticle(ParticleTypes.FLASH, this.getX(), this.getY() + 1D, this.getZ(), 0, 0, 0);
        }

    }

    protected void mobTick() {
        if (this.isAggro) {
            this.world.sendEntityStatus(this, (byte) 13);
        }
        else {
            this.world.sendEntityStatus(this, (byte) 12);
        }
        super.mobTick();
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 13) {
            this.dancing = true;
        }
        else if (status == 12) {
            this.dancing = false;
        }
    }

    class summonZombieGoal extends DancingZombieEntity.CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;

        private summonZombieGoal() {
            super();
			this.closeZombiePredicate = (TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility().ignoreDistanceScalingFactor());
		}

        public boolean canStart() {
            if (DancingZombieEntity.this.isAggro) {
                if (!super.canStart()) {
                    return false;
                } else {
                    int b = DancingZombieEntity.this.world.getTargets(BackupDancerEntity.class, this.closeZombiePredicate, DancingZombieEntity.this, DancingZombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    int p = DancingZombieEntity.this.world.getTargets(BackupDancerEntity.class, this.closeZombiePredicate, DancingZombieEntity.this, DancingZombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    int d = DancingZombieEntity.this.world.getTargets(BackupDancerEntity.class, this.closeZombiePredicate, DancingZombieEntity.this, DancingZombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    int t = DancingZombieEntity.this.world.getTargets(BackupDancerEntity.class, this.closeZombiePredicate, DancingZombieEntity.this, DancingZombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    return DancingZombieEntity.this.random.nextInt(8) + 1 > b &&
                            DancingZombieEntity.this.random.nextInt(8) + 1 > p &&
                            DancingZombieEntity.this.random.nextInt(8) + 1 > d &&
                            DancingZombieEntity.this.random.nextInt(8) + 1 > t ;
                }
            } else {
                return false;
            }
        }

        protected int getSpellTicks() {
            return 100;
        }

        protected int startTimeDelay() {
            return 340;
        }

        protected void castSpell() {
            ServerWorld serverWorld = (ServerWorld)DancingZombieEntity.this.world;

            for(int b = 0; b < 1; ++b) { // 1 backup
                BlockPos blockPos = DancingZombieEntity.this.getBlockPos().add(-1, 0.1, 0);
                BackupDancerEntity backupDancerEntity = (BackupDancerEntity)PvZEntity.BACKUPDANCER.create(DancingZombieEntity.this.world);
                backupDancerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                backupDancerEntity.initialize(serverWorld, DancingZombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound) null);
                backupDancerEntity.setOwner(DancingZombieEntity.this);
                serverWorld.spawnEntityAndPassengers(backupDancerEntity);
            }
            for(int p = 0; p < 1; ++p) { // 1 backup
                BlockPos blockPos = DancingZombieEntity.this.getBlockPos().add(0, 0.1, +1);
                BackupDancerEntity backupDancerEntity = (BackupDancerEntity)PvZEntity.BACKUPDANCER.create(DancingZombieEntity.this.world);
                backupDancerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                backupDancerEntity.initialize(serverWorld, DancingZombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                backupDancerEntity.setOwner(DancingZombieEntity.this);
                serverWorld.spawnEntityAndPassengers(backupDancerEntity);
            }
            for(int d = 0; d < 1; ++d) { // 1 backup
                BlockPos blockPos = DancingZombieEntity.this.getBlockPos().add(+1, 0.1, 0);
                BackupDancerEntity backupDancerEntity = (BackupDancerEntity)PvZEntity.BACKUPDANCER.create(DancingZombieEntity.this.world);
                backupDancerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                backupDancerEntity.initialize(serverWorld, DancingZombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                backupDancerEntity.setOwner(DancingZombieEntity.this);
                serverWorld.spawnEntityAndPassengers(backupDancerEntity);
            }
            for(int t = 0; t < 1; ++t) { // 1 backup
                BlockPos blockPos = DancingZombieEntity.this.getBlockPos().add(0, 0.1, -1);
                BackupDancerEntity backupDancerEntity = (BackupDancerEntity) PvZEntity.BACKUPDANCER.create(DancingZombieEntity.this.world);
                backupDancerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                backupDancerEntity.initialize(serverWorld, DancingZombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                backupDancerEntity.setOwner(DancingZombieEntity.this);
                serverWorld.spawnEntityAndPassengers(backupDancerEntity);
            }
        }

        protected SoundEvent getSoundPrepare() {
            return PvZCubed.ZOMBIEDANCINGEVENT;
        }

        protected DancingZombieEntity.Spell getSpell() {
            return DancingZombieEntity.Spell.SUMMON_VEX;
        }
    }

    class TrackOwnerTargetGoal extends TrackTargetGoal {
		private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

        public TrackOwnerTargetGoal(PathAwareEntity mob) {
            super(mob, false);
        }

        public boolean canStart() {
            return DancingZombieEntity.this.owner != null && DancingZombieEntity.this.owner.getTarget() != null && this.canTrack(DancingZombieEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            DancingZombieEntity.this.setTarget(DancingZombieEntity.this.owner.getTarget());
            super.start();
        }
    }
}