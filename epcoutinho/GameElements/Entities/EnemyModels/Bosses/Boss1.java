package GameElements.Entities.EnemyModels.Bosses;

import Engine.GameLib;
import GameElements.Entities.Player;
import GameElements.Entities.EnemyModels.Boss;
import GameElements.Entities.ProjectileModels.ProjectileEnemy;
import Manager.EntityState;
import Engine.ConfigReaders.GameConfig;
import java.awt.*;
import java.util.ArrayList;

public class Boss1 extends Boss {
    
    //  ATRIBUTOS 
    
    // protected boolean shild = false;
    protected long shildRecall;
    protected long shildTime;
    protected long shildDuration;

    //  CONSTRUTOR 
    
    public Boss1(double x, double y, long when, long now, int hp) {
        super(x, y, when, now, hp);
        shildTime = now + GameConfig.getBoss1ShieldTime();
        angle = GameConfig.getBoss1Angle();
        color = GameConfig.getColorBoss1();
        VY = GameConfig.getBoss1VY();
        VX = GameConfig.getBoss1VX();
        setState(EntityState.ACTIVE);
        radius = GameConfig.getBoss1Radius();
    }

    //  MÉTODOS PÚBLICOS 

    public long spawn(long currentTime){
        return currentTime + 1200;
    }
    
    public void update(long delta, Player player, ArrayList<ProjectileEnemy> enemy_Projectiles, long currentTime) {
        hasLife(currentTime);
        if (hitTimeEnd(currentTime)) {
            if (isSuperAttack(currentTime)) {
                setColor(GameConfig.getColorBoss1Super());
            } else setColor(GameConfig.getColorBoss1());
        }

        if (handleExploding(currentTime)) return;

        if (!isActive()) return;

        // Verifica se saiu da tela
        if (handleSaiuDaTela()) {
            VX = VX * (-1);
        }

        shild(currentTime);
        lifeBar.update(delta, currentTime, HP);
        shoot(enemy_Projectiles, player, delta, currentTime);
        updatePosition(delta, currentTime);
    }

    public void shoot(ArrayList<ProjectileEnemy> enemy_Projectiles, Player player, long delta, long now) {
        // Se o super ataque anterior terminou, agenda o próximo
        if (now > nextSuperAtack + superAtackDuration) {
            // Próximo super ataque entre 5 e 10 segundos a partir de agora
            long intervalo = GameConfig.getBoss1SuperAttackInterval() + (long) (Math.random() * GameConfig.getBoss1SuperAttackRandom());
            nextSuperAtack = now + intervalo;
        }

        // Só dispara se for o momento certo e o Boss estiver acima do jogador
        if (now > getNextShot() && getY() < player.getY() && handlePocicionado()) {
            // Corrige movimentação se estiver parada ou errada

            if (isSuperAttack(now)) {
                // SUPER DISPARO: múltiplos projéteis em leque
                if (VX == GameConfig.getBoss1VX() || VX == -GameConfig.getBoss1VX()) {
                    VX = VX * 2;
                }

                for (ProjectileEnemy proj : enemy_Projectiles) {
                    if (!proj.isActive()) {
                        proj.setX(getX());
                        proj.setY(getY());
                        proj.setVX(Math.cos(getAngle()) * GameConfig.getBoss1SuperProjectileSpeed());
                        proj.setVY(Math.sin(getAngle()) * GameConfig.getBoss1SuperProjectileSpeed() * (-1.0));
                        proj.setRadius(GameConfig.getBoss1SuperProjectileRadius());
                        proj.setState(EntityState.ACTIVE);
                        break;
                    }
                }

                setNextShot(now + GameConfig.getBoss1SuperAttackCooldown()); // cooldown do super disparo
            } else {
                // DISPARO NORMAL
                if ((VX != GameConfig.getBoss1VX() && VX != -GameConfig.getBoss1VX())) {
                    VX = VX / 2;
                }
                for (ProjectileEnemy proj : enemy_Projectiles) {
                    if (!proj.isActive()) {
                        proj.setX(getX());
                        proj.setY(getY());
                        proj.setVX(Math.cos(getAngle()) * GameConfig.getBoss1NormalProjectileSpeed());
                        proj.setVY(Math.sin(getAngle()) * GameConfig.getBoss1NormalProjectileVY() * (-1.0));
                        proj.setRadius(GameConfig.getBoss1NormalProjectileRadius());
                        proj.setState(EntityState.ACTIVE);
                        setNextShot((long) (now + GameConfig.getBoss1NormalShotCooldown() + Math.random() * GameConfig.getBoss1NormalShotRandom()));
                        break;
                    }
                }
            }
        }
    }

    public void draw(long now) {
        if (getState() == EntityState.EXPLODING) {
            double alpha = (now - explosionStart) / (explosionEnd - explosionStart);
            GameLib.drawExplosion(getX(), getY(), alpha);
        }
        if (getState() == EntityState.ACTIVE) {
            GameLib.setColor(color);
            GameLib.drawPlayer(getX(), getY(), (-1) * radius);
            if (isSuperAttack(now) && hitTimeEnd(now)) GameLib.setColor(GameConfig.getColorBoss1());
            lifeBar.draw();
            if (isIvulnerable) {
                GameLib.setColor(GameConfig.getColorBoss1Shield());
                GameLib.drawPlayer(getX(), getY(), (-1.5) * radius);
            }
        }
    }

    //  MÉTODOS PRIVADOS 
    
    public void shild(long currentTime) {
        if (shildTime < currentTime && !isIvulnerable) {
            // shild = true;
            shildDuration = currentTime + GameConfig.getBoss1ShieldDuration();
            shildTime = currentTime + GameConfig.getBoss1ShieldInterval();
            isIvulnerable = true;
        }
        if (isIvulnerable && shildDuration < currentTime) {
            isIvulnerable = false;
        }
    }

    public void updatePosition(long delta, long now) {
        if (handlePocicionado()) {
            setX(getX() + getVX() * Math.cos(0) * delta);
        } else {
            setY(getY() + delta * VY);
        }
    }

    public boolean handlePocicionado() {
        if (getY() > GameConfig.getBoss1PositionY()) return true;
        return false;
    }

    public boolean isSuperAttack(long now) {
        return now >= nextSuperAtack && now <= (nextSuperAtack + superAtackDuration);
    }

    // O Boss1 se movera no Eixo X e não no Y;
    public boolean handleSaiuDaTela() {
        if (getX() > GameConfig.getBoss1BorderWidth() + GameConfig.getBoss1BorderX()) {
            // setState(EntityState.INACTIVE);
            return true;
        }
        if (getX() < GameConfig.getBoss1BorderX()) {
            return true;
        }
        return false;
    }
}