package GameElements.Entities.EnemyModels.Bosses;

import Engine.GameLib;
import GameElements.Entities.Player;
import GameElements.Entities.EnemyModels.Boss;
import GameElements.Entities.ProjectileModels.ProjectileEnemy;
import Manager.EntityState;
import Engine.ConfigReaders.GameConfig;
import java.awt.*;
import java.util.ArrayList;

public class Boss2 extends Boss {
    // protected boolean shild = false;
    // protected long shildRecall;
    // protected long shildTime;
    // protected long shildDuration;

    // ===== CONSTRUTOR =====
    
    public Boss2(double x, double y, long when, long now, int hp) {
        super(x, y, when, now, hp);
        // shildTime = now + 10000;
        angle = GameConfig.getBoss2Angle();
        color = GameConfig.getColorBoss2();
        VY = GameConfig.getBoss2VY();
        VX = GameConfig.getBoss2VX();
        setState(EntityState.ACTIVE);
        radius = GameConfig.getBoss2Radius();
    }

    // ===== MÉTODOS PÚBLICOS =====
    public long spawn(long currentTime){
        return currentTime + 1200;
    }
    
    public void update(long delta, Player player, ArrayList<ProjectileEnemy> enemy_Projectiles, long currentTime) {
        hasLife(currentTime);
        if (hitTimeEnd(currentTime)) {
            if (isSuperAttack(currentTime)) {
                setColor(GameConfig.getColorBoss2Super());
            } else setColor(GameConfig.getColorBoss2());
        }

        if (handleExploding(currentTime)) return;

        if (!isActive()) return;

        // Verifica se saiu da tela
        if (handleSaiuDaTelaX()) {
            VX = VX * (-1);
        }
        if (handleSaiuDaTelaY()) {
            // setY(0);
            VY = VY * (-1);
            // setX(Math.random()*1000/GameLib.WIDTH);
        }

        // shild(currentTime);
        lifeBar.update(delta, currentTime, HP);
        shoot(enemy_Projectiles, player, delta, currentTime);
        updatePosition(delta, currentTime);
    }

    public void shoot(ArrayList<ProjectileEnemy> enemy_Projectiles, Player player, long delta, long now) {
        // Se o super ataque anterior terminou, agenda o próximo
        if (now > nextSuperAtack + superAtackDuration) {
            // Próximo super ataque entre 5 e 10 segundos a partir de agora
            long intervalo = GameConfig.getBoss2SuperAttackInterval() + (long) (Math.random() * GameConfig.getBoss2SuperAttackRandom());
            nextSuperAtack = now + intervalo;
        }

        // Só dispara se for o momento certo e o Boss estiver acima do jogador
        if (now > getNextShot() && getY() < player.getY() && handlePocionado()) {
            // Corrige movimentação se estiver parada ou errada

            if (isSuperAttack(now)) {
                // SUPER DISPARO: múltiplos projéteis em leque
                setNextShot(now + GameConfig.getBoss2SuperAttackCooldown()); // cooldown do super disparo
            } else {
                // DISPARO NORMAL
                int disparos = GameConfig.getBoss2ShotsCount();
                double anguloBase = getAngle();
                double spread = GameConfig.getBoss2AngleSpread(); // ângulo total
                double passo = spread / (disparos - 1);

                for (int i = 0; i < disparos; i++) {
                    for (ProjectileEnemy proj : enemy_Projectiles) {
                        if (!proj.isActive()) {
                            double angulo = anguloBase - spread / 2 + i * passo;
                            proj.setX(getX());
                            proj.setY(getY());
                            proj.setVX(Math.cos(angulo) * GameConfig.getBoss2ProjectileSpeed());
                            proj.setVY(Math.sin(angulo) * GameConfig.getBoss2ProjectileSpeed() * (-1.0));
                            proj.setRadius(GameConfig.getBoss2ProjectileRadius());
                            proj.setState(EntityState.ACTIVE);
                            setNextShot((long) (now + GameConfig.getBoss2NormalShotCooldown() + Math.random() * GameConfig.getBoss2NormalShotRandom()));
                            break;
                        }
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

            GameLib.drawDiamond(getX(), getY(), radius);
            GameLib.drawDiamond(getX(), getY(), radius * Math.sin(getY()));
            GameLib.drawDiamond(getX(), getY(), radius * Math.sin(getX()));

            if (isSuperAttack(now) && hitTimeEnd(now)) GameLib.setColor(GameConfig.getColorBoss2());
            lifeBar.draw();
            // GameLib.fillRect(240, getY() - 150, 5000*HP/GameLib.WIDTH, 40);
        }
    }

    // ===== MÉTODOS PRIVADOS =====
    
    // public void shild(long currentTime) {
    //     if (shildTime < currentTime && !isIvulnerable) {
    //         // shild = true;
    //         shildDuration = currentTime + 10000;
    //         shildTime = currentTime + 20000;
    //         isIvulnerable = true;
    //     }
    //     if (isIvulnerable && shildDuration < currentTime) {
    //         isIvulnerable = false;
    //     }
    // }

    public void updatePosition(long delta, long now) {
        double targetY = GameConfig.getBoss2TargetY(); // centro da faixa desejada (200–230)

        if (isSuperAttack(now)) {
            // Durante o super ataque: desce rápido
            setY(getY() + delta * VY * GameConfig.getBoss2SuperAttackSpeed());
        } else if (getY() > GameConfig.getBoss2MaxY()) {
            // Após super ataque: volta para cima suavemente
            setY(getY() - delta * GameConfig.getBoss2ReturnSpeed()); // sobe suavemente
            if (getY() < targetY) setY(targetY); // trava no centro
        } else if (handlePocionado()) {
            // Já está posicionado: mover horizontalmente
            setX(getX() + getVX() * delta);
        } else {
            // Ainda se posicionando: descer lentamente até a faixa
            setY(getY() + delta * VY);
        }
    }

    public boolean handlePocionado() {
        if (getY() > GameConfig.getBoss2MinY() && getY() < GameConfig.getBoss2MaxY()) return true;
        return false;
    }

    public boolean isSuperAttack(long now) {
        return now >= nextSuperAtack && now <= (nextSuperAtack + superAtackDuration);
    }

    // O Boss2 se move nos Eixos X e Y;
    public boolean handleSaiuDaTelaX() {
        if (getX() > GameConfig.getBoss2BorderWidth() + GameConfig.getBoss2BorderX()) {
            // setState(EntityState.INACTIVE);
            return true;
        }
        if (getX() < GameConfig.getBoss2BorderX()) {
            return true;
        }
        return false;
    }

    public boolean handleSaiuDaTelaY() {
        if (getY() > GameConfig.getBoss2BorderYMax() || getY() < GameConfig.getBoss2BorderYMin()) {
            return true;
        }
        return false;
    }
}

/* ideia de disparo
int disparos = 5;
double anguloBase = getAngle();
double spread = Math.toRadians(45); // ângulo total
double passo = spread / (disparos - 1);

for (int i = 0; i < disparos; i++) {
    for (ProjectileEnemy proj : enemy_Projectiles) {
        if (!proj.isActive()) {
            double angulo = anguloBase - spread / 2 + i * passo;
            proj.setX(getX());
            proj.setY(getY());
            proj.setVX(Math.cos(angulo) * 0.5);
            proj.setVY(Math.sin(angulo) * 0.5 * (-1.0));
            proj.setRadius(8);
            proj.setState(EntityState.ACTIVE);
            break;
        }
    }
}
 */