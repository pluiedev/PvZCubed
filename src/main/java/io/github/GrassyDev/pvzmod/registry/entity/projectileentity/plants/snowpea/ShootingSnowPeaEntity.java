package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.snowpea;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.UUID;

public class ShootingSnowPeaEntity extends ThrownItemEntity implements IAnimatable {

	private String controllerName = "projectilecontroller";
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	@Override
	public void registerControllers(AnimationData animationData) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		animationData.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		event.getController().setAnimation(new AnimationBuilder().loop("peashot.idle"));
		return PlayState.CONTINUE;
	}

    public static final Identifier PacketID = new Identifier(PvZEntity.ModID, "snowpeaproj");

    public ShootingSnowPeaEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(true);
    }

    public ShootingSnowPeaEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public ShootingSnowPeaEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.SNOWPEAPROJ, world);
		updatePosition(x, y, z);
		updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation, interpolate);
		setId(id);
		setUuid(uuid);
    }

    public void tick() {
		super.tick();
		HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
		RandomGenerator randomGenerator = this.random;
		boolean bl = false;
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
			BlockState blockState = this.world.getBlockState(blockPos);
			if (blockState.isOf(Blocks.NETHER_PORTAL)) {
				this.setInNetherPortal(blockPos);
				bl = true;
			} else if (blockState.isOf(Blocks.END_GATEWAY)) {
				BlockEntity blockEntity = this.world.getBlockEntity(blockPos);
				if (blockEntity instanceof EndGatewayBlockEntity && EndGatewayBlockEntity.canTeleport(this)) {
					EndGatewayBlockEntity.tryTeleportingEntity(this.world, blockPos, blockState, this, (EndGatewayBlockEntity)blockEntity);
				}

				bl = true;
			}
		}

		if (hitResult.getType() != HitResult.Type.MISS && !bl) {
			this.onCollision(hitResult);
		}

		if (!this.world.isClient && this.isInsideWaterOrBubbleColumn()) {
			this.world.sendEntityStatus(this, (byte) 3);
			this.remove(RemovalReason.DISCARDED);
		}

		if (!this.world.isClient && this.age == 60) {
			this.world.sendEntityStatus(this, (byte) 3);
			this.remove(RemovalReason.DISCARDED);
		}
		double d = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
		double e = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;
		double f = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;

		for (int j = 0; j < 2; ++j) {
			this.world.addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), d, e, f);
		}
	}

    @Override
    protected Item getDefaultItem() {
        return null;
    }


    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
		ZombiePropEntity zombiePropEntity2 = null;
		for (Entity entity1 : entity.getPassengerList()) {
			if (entity1 instanceof ZombiePropEntity zpe) {
				zombiePropEntity2 = zpe;
			}
		}
        if (!world.isClient && entity instanceof Monster monster &&
				!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
				!(zombiePropEntity2 != null && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
				  !(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())) {
			if (!((LivingEntity) entity).hasStatusEffect(PvZCubed.WARM) && !entity.isOnFire() && !((LivingEntity) entity).hasStatusEffect(PvZCubed.FROZEN)){
				if (!(entity instanceof ZombieShieldEntity)) {
					((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 60, 1)));
				}
			}
			String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
			SoundEvent sound;
			sound = switch (zombieMaterial) {
				case "metallic" -> PvZCubed.BUCKETHITEVENT;
				case "plastic" -> PvZCubed.CONEHITEVENT;
				default -> PvZCubed.PEAHITEVENT;
			};
			if (entity instanceof ZombieShieldEntity){
				entity.playSound(sound, 0.2F, 1F);
			}
			else {
				entity.playSound(PvZCubed.SNOWPEAHITEVENT, 0.2F, 1F);
			}
			float damage = 4F;
			if (damage > ((LivingEntity) entity).getHealth() &&
					!(entity instanceof ZombieShieldEntity) &&
					entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())){
				float damage2 = damage - ((LivingEntity) entity).getHealth();
				entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
				generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage2);
			}
			else {
				entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
			}
            this.world.sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }


    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }

			double d = this.random.nextDouble() / 2 * this.random.range(-1, 1);
			double e = this.random.nextDouble() / 2 * this.random.range(-1, 1);
			double f = this.random.nextDouble() / 2 * this.random.range(-1, 1);

			for (int j = 0; j < 16; ++j) {
				this.world.addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), d, e, f);
			}
        }

    }
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte)3);
            this.remove(RemovalReason.DISCARDED);
        }
    }

    public boolean collides() {
        return false;
    }
}
