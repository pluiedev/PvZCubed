package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.flamingpea;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.PepperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.flamingpea.ShootingFlamingPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.plasmapea.ShootingPlasmaPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.EnumSet;

public class FlamingpeaEntity extends PepperEntity implements IAnimatable, RangedAttackMob {
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private String controllerName = "peacontroller";

	public boolean isFiring;

	public FlamingpeaEntity(EntityType<? extends FlamingpeaEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
    }

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status == 11) {
			this.isFiring = true;
		} else if (status == 10) {
			this.isFiring = false;
		}
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
		if (this.isFiring) {
			if (this.isWet()){
				event.getController().setAnimation(new AnimationBuilder().playOnce("peashooter.wetflame.shoot"));
			}
			else {
				event.getController().setAnimation(new AnimationBuilder().playOnce("peashooter.shoot"));
			}
		} else {
			if (this.isWet()){
				event.getController().setAnimation(new AnimationBuilder().loop("peashooter.wetflame.idle"));
			}
			else {
				event.getController().setAnimation(new AnimationBuilder().loop("peashooter.idle"));
			}
		}
		return PlayState.CONTINUE;
	}

	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new FlamingpeaEntity.FireBeamGoal(this));
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, this.random.nextInt(30) + 25, 15.0F));
		this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) &&
					!(livingEntity instanceof ZombiePropEntity) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel());
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof GeneralPvZombieEntity);
		}));
		snorkelGoal();
	}
	protected void snorkelGoal() {
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
			return livingEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel() && !(snorkelEntity.getHypno());
		}));
	}



	@Override
	public void attack(LivingEntity target, float pullProgress) {

	}


	/** /~*~//~*POSITION*~//~*~/ **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double)MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}

		if (this.age != 0) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.FIRE_PEA_SEED_PACKET);
				}
				this.kill();
			}

		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}
		LivingEntity target = this.getTarget();
		if (target != null){
			if (target instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
				this.setTarget(null);
				snorkelGoal();
			}
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.kill();
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/
	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.FIRE_PEA_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createFlamingpeaAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15D);
	}

	protected boolean canClimb() {
		return false;
	}

	public boolean collides() {
		return true;
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.60F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return PvZCubed.SILENCEVENET;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return PvZCubed.PLANTPLANTEDEVENT;
	}

	public boolean hurtByWater() {
		return false;
	}


	public boolean isOnFire() {
		return false;
	}

	public boolean isPushable() {
		return false;
	}

	protected void pushAway(Entity entity) {
	}

	public boolean startRiding(Entity entity, boolean force) {
		return super.startRiding(entity, force);
	}

	public void stopRiding() {
		super.stopRiding();
		this.prevBodyYaw = 0.0F;
		this.bodyYaw = 0.0F;
	}


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	public boolean handleAttack(Entity attacker) {
		if (attacker instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity) attacker;
			return this.damage(DamageSource.player(playerEntity), 9999.0F);
		} else {
			return false;
		}
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		if (fallDistance > 0F) {
			this.playSound(PvZCubed.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.kill();
		}
		this.playBlockFallSound();
		return true;
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	static class FireBeamGoal extends Goal {
		private final FlamingpeaEntity flamingpeaEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(FlamingpeaEntity flamingpeaEntity) {
			this.flamingpeaEntity = flamingpeaEntity;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.flamingpeaEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -7;
			this.animationTicks = -16;
			this.flamingpeaEntity.getNavigation().stop();
			this.flamingpeaEntity.getLookControl().lookAt(this.flamingpeaEntity.getTarget(), 90.0F, 90.0F);
			this.flamingpeaEntity.velocityDirty = true;
		}

		public void stop() {
			this.flamingpeaEntity.world.sendEntityStatus(this.flamingpeaEntity, (byte) 10);
			this.flamingpeaEntity.setTarget((LivingEntity) null);
		}

		public void tick() {
			LivingEntity livingEntity = this.flamingpeaEntity.getTarget();
			this.flamingpeaEntity.getNavigation().stop();
			this.flamingpeaEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if (!this.flamingpeaEntity.canSee(livingEntity)) {
				this.flamingpeaEntity.setTarget((LivingEntity) null);
			} else {
				this.flamingpeaEntity.world.sendEntityStatus(this.flamingpeaEntity, (byte) 11);
				++this.beamTicks;
				++this.animationTicks;
				double probability = flamingpeaEntity.random.nextDouble();
				double time = (this.flamingpeaEntity.squaredDistanceTo(livingEntity) > 36) ? 50 : 1;
				Vec3d targetPos = livingEntity.getPos();
				Vec3d predictedPos = targetPos.add(livingEntity.getVelocity().multiply(time));
				double d = this.flamingpeaEntity.squaredDistanceTo(predictedPos);
				float df = (float)d;
				double e = predictedPos.getX() - this.flamingpeaEntity.getX();
				double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? -0.07500000111758709 : livingEntity.getY() - this.flamingpeaEntity.getY();
				double g = predictedPos.getZ() - this.flamingpeaEntity.getZ();
				float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
				if (this.beamTicks >= 0 && this.animationTicks <= -7) {
					if (probability <= 0.33) {
						ShootingPlasmaPeaEntity proj = new ShootingPlasmaPeaEntity(PvZEntity.PLASMAPEA, this.flamingpeaEntity.world);
						proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.33F, 0F);
						proj.updatePosition(this.flamingpeaEntity.getX(), this.flamingpeaEntity.getY() + 0.75D, this.flamingpeaEntity.getZ());
						proj.setOwner(this.flamingpeaEntity);
						if (livingEntity.isAlive()) {
							this.beamTicks = -7;
							this.flamingpeaEntity.world.sendEntityStatus(this.flamingpeaEntity, (byte) 11);
							this.flamingpeaEntity.playSound(PvZCubed.PEASHOOTEVENT, 1F, 1);
							this.flamingpeaEntity.world.spawnEntity(proj);
						}
					}
					else if (!this.flamingpeaEntity.isInsideWaterOrBubbleColumn()) {
						ShootingFlamingPeaEntity proj = new ShootingFlamingPeaEntity(PvZEntity.FIREPEA, this.flamingpeaEntity.world);
						proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.33F, 0F);
						proj.updatePosition(this.flamingpeaEntity.getX(), this.flamingpeaEntity.getY() + 0.75D, this.flamingpeaEntity.getZ());
						proj.setOwner(this.flamingpeaEntity);
						if (livingEntity.isAlive()) {
							this.beamTicks = -7;
							this.flamingpeaEntity.world.sendEntityStatus(this.flamingpeaEntity, (byte) 11);
							this.flamingpeaEntity.playSound(PvZCubed.PEASHOOTEVENT, 1F, 1);
							if (this.flamingpeaEntity.isWet()){
								this.flamingpeaEntity.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1F, 1);
							}
							this.flamingpeaEntity.world.spawnEntity(proj);
						}
					}
				}
				if (this.animationTicks >= 0) {
					this.flamingpeaEntity.world.sendEntityStatus(this.flamingpeaEntity, (byte) 10);
					this.beamTicks = -7;
					this.animationTicks = -16;
				}
				super.tick();
			}
		}
	}
}
