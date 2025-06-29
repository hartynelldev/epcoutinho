package Entities.EnemyModels.Bosses;
import Entities.Boss;

public class Boss2 {
}
/* ideoa de disparo
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