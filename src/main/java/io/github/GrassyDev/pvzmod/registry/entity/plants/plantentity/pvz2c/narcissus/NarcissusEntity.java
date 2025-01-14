package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.narcissus;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.SpearEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.armorbubble.ArmorBubbleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.bubbles.BubbleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.HitResult;
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

public class NarcissusEntity extends SpearEntity implements IAnimatable, RangedAttackMob {

    private String controllerName = "kelpcontroller";


	private int amphibiousRaycastDelay;

	private boolean isFiring;

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	private boolean dryLand;

    public NarcissusEntity(EntityType<? extends NarcissusEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
		amphibiousRaycastDelay = 1;

		this.setNoGravity(true);
    }

	public NarcissusEntity(World world, double x, double y, double z) {
		this(PvZEntity.NARCISSUS, world);
		this.setPosition(x, y, z);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
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
		if (this.dryLand) {
			if (this.isFiring) {
				event.getController().setAnimation(new AnimationBuilder().playOnce("narcissus.shoot"));
			}
			else {
				event.getController().setAnimation(new AnimationBuilder().loop("narcissus.idle"));
			}
		}
		else {
			if (this.isFiring) {
				event.getController().setAnimation(new AnimationBuilder().playOnce("narcissus.shoot2"));
			}
			else {
				event.getController().setAnimation(new AnimationBuilder().loop("narcissus.idle2"));
			}
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new NarcissusEntity.FireBeamGoal(this));
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, this.random.nextInt(25) + 20, 6.0F));
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
			super.setPosition((double) MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}

		if (this.age != 0) {
			BlockPos blockPos2 = this.getBlockPos();
			if (!blockPos2.equals(blockPos)) {
				this.kill();
			}

		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		BlockPos blockPos = this.getBlockPos();
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}
		if (this.isInsideWaterOrBubbleColumn()){
			kill();
		}
		LivingEntity target = this.getTarget();
		if (target != null){
			if (target instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
				this.setTarget(null);
				snorkelGoal();
			}
		}
		if (--amphibiousRaycastDelay >= 0) {
			amphibiousRaycastDelay = 60;
			HitResult hitResult = amphibiousRaycast(0.25);
			if (hitResult.getType() == HitResult.Type.MISS) {
				kill();
			}
			if (this.age != 0) {
				BlockPos blockPos2 = this.getBlockPos();
				BlockState blockState = this.getLandingBlockState();
				FluidState fluidState = world.getFluidState(this.getBlockPos().add(0, -0.5, 0));
				if (!(fluidState.getFluid() == Fluids.WATER)) {
					this.dryLand = true;
					onWater = false;
				} else {
					this.dryLand = false;
					onWater = true;
				}
				if (!blockPos2.equals(blockPos) || (!(fluidState.getFluid() == Fluids.WATER) && !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.NARCISSUS_SEED_PACKET);
				}
				this.kill();
				}
			}
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.NARCISSUS_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createNarcissusAttributes() {
        return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 6D);
    }

	protected boolean canClimb() {return false;}

	public boolean collides() {return true;}


	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.8F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return PvZCubed.SILENCEVENET;
	}

	@Nullable
	protected SoundEvent getDeathSound() {return PvZCubed.PLANTPLANTEDEVENT;}

	public boolean hurtByWater() {return false;}

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
		private final NarcissusEntity narcissus;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(NarcissusEntity narcissus) {
			this.narcissus = narcissus;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.narcissus.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -8;
			this.animationTicks = -21;
			this.narcissus.getNavigation().stop();
			this.narcissus.getLookControl().lookAt(this.narcissus.getTarget(), 90.0F, 90.0F);
			this.narcissus.velocityDirty = true;
		}

		public void stop() {
			this.narcissus.world.sendEntityStatus(this.narcissus, (byte) 10);
			this.narcissus.setTarget((LivingEntity) null);
		}

		public void tick() {
			LivingEntity livingEntity = this.narcissus.getTarget();
			this.narcissus.getNavigation().stop();
			this.narcissus.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.narcissus.canSee(livingEntity) && this.animationTicks >= 0)){
				this.narcissus.setTarget((LivingEntity) null);
			} else {
				this.narcissus.world.sendEntityStatus(this.narcissus, (byte) 11);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -4) {
					double probability = narcissus.random.nextDouble();
					if (probability <= 0.25 && !this.narcissus.dryLand) {
						double time = 1;
						Vec3d targetPos = livingEntity.getPos();
						Vec3d predictedPos = targetPos.add(livingEntity.getVelocity().multiply(time));
						double d = this.narcissus.squaredDistanceTo(predictedPos);
						float df = (float)d;
						double e = predictedPos.getX() - this.narcissus.getX();
						double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? -0.07500000111758709 : livingEntity.getY() - this.narcissus.getY();
						double g = predictedPos.getZ() - this.narcissus.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						ArmorBubbleEntity proj = new ArmorBubbleEntity(PvZEntity.ARMORBUBBLE, this.narcissus.world);
						proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.33F, 0F);
						proj.updatePosition(this.narcissus.getX(), this.narcissus.getY() + 0.5D, this.narcissus.getZ());
						proj.setOwner(this.narcissus);
						if (livingEntity.isAlive()) {
							this.beamTicks = -2;
							this.narcissus.playSound(PvZCubed.FUMESHROOMSHOOTEVENT, 0.25F, 1.5F);
							this.narcissus.world.spawnEntity(proj);
						}
					}
					double time = 1;
					Vec3d targetPos = livingEntity.getPos();
					Vec3d predictedPos = targetPos.add(livingEntity.getVelocity().multiply(time));
					double d = this.narcissus.squaredDistanceTo(predictedPos);
					float df = (float)d;
					double e = predictedPos.getX() - this.narcissus.getX();
					double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? -0.07500000111758709 : livingEntity.getY() - this.narcissus.getY();
					double g = predictedPos.getZ() - this.narcissus.getZ();
					float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
					BubbleEntity proj = new BubbleEntity(PvZEntity.BUBBLE, this.narcissus.world);
					proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.85F, 0F);
					proj.updatePosition(this.narcissus.getX(), this.narcissus.getY() + 0.5D, this.narcissus.getZ());
					proj.setOwner(this.narcissus);
					if (livingEntity.isAlive()) {
						this.beamTicks = -2;
						this.narcissus.playSound(PvZCubed.FUMESHROOMSHOOTEVENT, 0.25F, 1.5F);
						this.narcissus.world.spawnEntity(proj);
					}
				}
				if (this.animationTicks >= 0) {
					this.narcissus.world.sendEntityStatus(this.narcissus, (byte) 10);
					this.beamTicks = -8;
					this.animationTicks = -21;
				}
				super.tick();
			}
		}
	}
}
