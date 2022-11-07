package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.gargantuar.modernday;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.*;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.dancingzombie.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.flagzombie.modernday.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.imp.modernday.HypnoImpEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class HypnoGargantuarEntity extends HypnoZombieEntity implements IAnimatable {
	private String controllerName = "walkingcontroller";

    private MobEntity owner;

	private int animationTicksLeft;
	private int launchAnimation;
    public boolean firstAttack;
    public boolean inAnimation;
	public boolean inLaunchAnimation;

	public AnimationFactory factory = new AnimationFactory(this);

	public HypnoGargantuarEntity(EntityType<? extends HypnoGargantuarEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 12;
        this.firstAttack = true;
		this.getNavigation().setCanSwim(true);
		this.setPathfindingPenalty(PathNodeType.WATER, 8.0F);
		this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
		this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, true);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("Imp", this.getImpStage());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_COUNT, tag.getBoolean("Imp"));
	}

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status == 13) {
			this.inAnimation = true;
		}
		else if (status == 12) {
			this.inAnimation = false;
		}
		if (status == 44) {
			this.inLaunchAnimation = true;
		}
		else if (status == 43) {
			this.inLaunchAnimation = false;
		}
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(HypnoGargantuarEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum ImpStage {
		IMP(true),
		NOIMP(false);

		ImpStage(boolean id) {
			this.id = id;
		}

		final boolean id;

		private boolean getId() {
			return this.id;
		}
	}

	private Boolean getImpStage() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	public void setImpStage(ImpStage impStage) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, impStage.getId());
	}


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		data.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		if (inLaunchAnimation){
			event.getController().setAnimation(new AnimationBuilder().addAnimation("gargantuar.throw", false));
		}
		else if (this.getImpStage()){
			if (inAnimation){
				event.getController().setAnimation(new AnimationBuilder().addAnimation("gargantuar.smash", false));
			}
			else if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("gargantuar.walk", true));
			}else {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("gargantuar.idle", true));
			}
		}
		else {
			if (inAnimation){
				event.getController().setAnimation(new AnimationBuilder().addAnimation("gargantuar.smash2", false));
			}
			else if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("gargantuar.walk2", true));
			}else {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("gargantuar.idle2", true));
			}
		}
        return PlayState.CONTINUE;
    }

	protected void initGoals() {
		this.goalSelector.add(1, new HypnoGargantuarEntity.AttackGoal());
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
		this.initCustomGoals();
	}

	protected void initCustomGoals() {
		this.targetSelector.add(2, new HypnoGargantuarEntity.TrackOwnerTargetGoal(this));
		this.goalSelector.add(1, new HypnoPvZombieAttackGoal(this, 1.0D, true));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, true, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof HypnoDancingZombieEntity) &&
					!(livingEntity instanceof HypnoFlagzombieEntity);
		}));
	}

	//Smash
	public boolean tryAttack(Entity target) {
		if (!this.hasStatusEffect(PvZCubed.FROZEN) && !this.inLaunchAnimation) {
			if (this.firstAttack && this.animationTicksLeft <= 0) {
				this.animationTicksLeft = 90;
				this.firstAttack = false;
			}
			else if (this.animationTicksLeft == 39) {
				if (this.hasStatusEffect(PvZCubed.ICE) && this.squaredDistanceTo(target) < 32D) {
					target.damage(DamageSource.mob(this), 720f);
				} else if (this.squaredDistanceTo(target) < 32D) {
					target.damage(DamageSource.mob(this), 360f);
				}
			}
		}
		return false;
	}

	//Launch Imp
	public boolean tryLaunch(Entity target){
		if (this.getImpStage().equals(Boolean.TRUE) && launchAnimation == 20){
			HypnoImpEntity imp = new HypnoImpEntity(PvZEntity.HYPNOIMP, this.world);
			if (target != null){
				double d = this.squaredDistanceTo(target);
				float df = (float) d;
				double e = target.getX() - this.getX();
				double f = target.getY() - this.getY();
				double g = target.getZ() - this.getZ();
				float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
				imp.setVelocity(e * (double) h, f * (double) h, g * (double) h, 2.25F, 0F);
				imp.updatePosition(this.getX(), this.getY() + 3.95D, this.getZ());
				imp.setOwner(this);
					this.setImpStage(HypnoGargantuarEntity.ImpStage.NOIMP);
					this.playSound(PvZCubed.IMPLAUNCHEVENT, 1F, 1);
					this.world.spawnEntity(imp);
			}
			else {
				imp.setVelocity(random.range(-1, 1), 0, random.range(-1, 1), 2.25F, 0F);
				imp.updatePosition(this.getX(), this.getY() + 3.95D, this.getZ());
				imp.setOwner(this);
					this.setImpStage(HypnoGargantuarEntity.ImpStage.NOIMP);
					this.playSound(PvZCubed.IMPLAUNCHEVENT, 1F, 1);
					this.world.spawnEntity(imp);
			}
		}
		return false;
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void mobTick() {
		super.mobTick();
		if (this.animationTicksLeft <= 0){
			if (this.getHealth() <= 360 && getTarget() != null && this.getImpStage().equals(Boolean.TRUE) && !this.inLaunchAnimation) {
				this.launchAnimation = 50;
				this.inLaunchAnimation = true;
				this.world.sendEntityStatus(this, (byte) 44);
			}
			if (this.launchAnimation > 0) {
				this.getNavigation().stop();
				--launchAnimation;
				tryLaunch(getTarget());
				this.inLaunchAnimation = true;
				this.world.sendEntityStatus(this, (byte) 44);
			}
			else {
				this.inLaunchAnimation = false;
				this.world.sendEntityStatus(this, (byte) 43);
			}
		}
		if (this.animationTicksLeft == 39 && !inLaunchAnimation) {
			this.playSound(PvZCubed.GARGANTUARSMASHEVENT, 1F, 1.0F);
			if (getTarget() != null) {
				this.firstAttack = true;
				tryAttack(getTarget());}
		}
		else if (getTarget() == null){
			this.firstAttack = true;
		}
		if (this.animationTicksLeft > 0) {
			this.getNavigation().stop();
			--this.animationTicksLeft;
			this.world.sendEntityStatus(this, (byte) 13);
		}
		else{
			this.world.sendEntityStatus(this, (byte) 12);
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createHypnoGargantuarAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.15D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 540D);
    }

	protected SoundEvent getAmbientSound() {
		return PvZCubed.GARGANTUARMOANEVENT;
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	protected SoundEvent getHurtSound() {
		return PvZCubed.ZOMBIEBITEEVENT;
	}

	public MobEntity getOwner() {
		return this.owner;
	}

	protected SoundEvent getStepSound() {
		return PvZCubed.SILENCEVENET;
	}

	public boolean isPushable() {
		return false;
	}

	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

    public void setOwner(MobEntity owner) {
        this.owner = owner;
    }


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

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

            /**if (this.getRecentDamageSource() == PvZCubed.HYPNO_DAMAGE) {
                this.playSound(PvZCubed.HYPNOTIZINGEVENT, 1.5F, 1.0F);
                HypnoFootballEntity hypnoFootballEntity = (HypnoFootballEntity) PvZEntity.HYPNOFOOTBALL.create(world);
                hypnoFootballEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                hypnoFootballEntity.initialize(serverWorld, world.getLocalDifficulty(hypnoFootballEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData)null, (NbtCompound) null);
                hypnoFootballEntity.setAiDisabled(this.isAiDisabled());
				hypnoFootballEntity.setHealth(this.getHealth() + 3);
                if (this.hasCustomName()) {
                    hypnoFootballEntity.setCustomName(this.getCustomName());
                    hypnoFootballEntity.setCustomNameVisible(this.isCustomNameVisible());
                }

                hypnoFootballEntity.setPersistent();
                serverWorld.spawnEntityAndPassengers(hypnoFootballEntity);
                this.remove(RemovalReason.DISCARDED);
            }**/

            return true;
        }
    }


	/** /~*~//~*SPAWNING*~//~*~/ **/

    @Override
    public boolean canSpawn(WorldView worldreader) {
        return worldreader.doesNotIntersectEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
    }


	/** /~*~//~*GOALS*~//~*~/ **/

	class TrackOwnerTargetGoal extends TrackTargetGoal {
		private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

        public TrackOwnerTargetGoal(PathAwareEntity mob) {
            super(mob, false);
        }

        public boolean canStart() {
            return HypnoGargantuarEntity.this.owner != null && HypnoGargantuarEntity.this.owner.getTarget() != null && this.canTrack(HypnoGargantuarEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            HypnoGargantuarEntity.this.setTarget(HypnoGargantuarEntity.this.owner.getTarget());
            super.start();
        }
    }

	private class AttackGoal extends HypnoPvZombieAttackGoal {
		public AttackGoal() {
			super(HypnoGargantuarEntity.this, 1.0, true);
		}

		protected double getSquaredMaxAttackDistance(LivingEntity entity) {
			float f = HypnoGargantuarEntity.this.getWidth() - 0.1F;
			return (double)(f * 4F * f * 4F + entity.getWidth());
		}
	}
}
