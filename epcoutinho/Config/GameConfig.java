package Config;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameConfig {
    private static Properties properties;
    private static boolean initialized = false;

    // INICIALIZAÇÃO 
    
    public static void initialize() {
        if (initialized) return;
        
        properties = new Properties();
        try (InputStream input = GameConfig.class.getClassLoader().getResourceAsStream("Config/gameConfig.txt")) {
            if (input == null) {
                System.err.println("Arquivo de configuração não encontrado: Config/gameConfig.properties");
                return;
            }
            properties.load(input);
            initialized = true;
        } catch (IOException e) {
            System.err.println("Erro ao carregar configurações: " + e.getMessage());
        }
    }

    // MÉTODOS DE ACESSO 
    
    public static double getDouble(String key) {
        if (!initialized) initialize();
        String value = properties.getProperty(key);
        return value != null ? Double.parseDouble(value) : 0.0;
    }

    public static int getInt(String key) {
        if (!initialized) initialize();
        String value = properties.getProperty(key);
        return value != null ? Integer.parseInt(value) : 0;
    }

    public static long getLong(String key) {
        if (!initialized) initialize();
        String value = properties.getProperty(key);
        return value != null ? Long.parseLong(value) : 0L;
    }

    public static Color getColor(String key) {
        if (!initialized) initialize();
        String value = properties.getProperty(key);
        if (value == null) return Color.WHITE;
        
        switch (value.toUpperCase()) {
            case "BLUE": return Color.BLUE;
            case "GREEN": return Color.GREEN;
            case "RED": return Color.RED;
            case "YELLOW": return Color.YELLOW;
            case "CYAN": return Color.CYAN;
            case "MAGENTA": return Color.MAGENTA;
            case "ORANGE": return Color.ORANGE;
            case "WHITE": return Color.WHITE;
            case "BLACK": return Color.BLACK;
            default: return Color.WHITE;
        }
    }

    // GAME ELEMENT
    
    public static double getGameElementDefaultRadius() {
        return getDouble("gameElement.defaultRadius");
    }

    public static double getGameElementDefaultVX() {
        return getDouble("gameElement.defaultVX");
    }

    public static double getGameElementDefaultVY() {
        return getDouble("gameElement.defaultVY");
    }

    // ENTITY 
    
    public static int getEntityDefaultHP() {
        return getInt("entity.defaultHP");
    }

    public static double getEntityDefaultExplosionTime() {
        return getDouble("entity.defaultExplosionTime");
    }

    public static double getEntityPlayerExplosionTime() {
        return getDouble("entity.playerExplosionTime");
    }

    // PLAYER
    
    public static int getPlayerShootingSpeed() {
        return getInt("player.shootingSpeed");
    }

    public static double getPlayerBaseRadius() {
        return getDouble("player.baseRadius");
    }

    public static double getPlayerShieldRadiusIncrement() {
        return getDouble("player.shieldRadiusIncrement");
    }

    public static double getPlayerInitialX() {
        return getDouble("player.initialX");
    }

    public static double getPlayerInitialY() {
        return getDouble("player.initialY");
    }

    public static double getPlayerMinY() {
        return getDouble("player.minY");
    }

    public static double getPlayerMaxY() {
        return getDouble("player.maxY");
    }

    public static double getPlayerMinX() {
        return getDouble("player.minX");
    }

    public static double getPlayerMaxX() {
        return getDouble("player.maxX");
    }

    // ENEMY 1
    
    public static double getEnemy1Radius() {
        return getDouble("enemy1.radius");
    }

    public static double getEnemy1VX() {
        return getDouble("enemy1.VX");
    }

    public static double getEnemy1VY() {
        return getDouble("enemy1.VY");
    }

    public static double getEnemy1VYRandom() {
        return getDouble("enemy1.VYRandom");
    }

    public static double getEnemy1Angle() {
        return getDouble("enemy1.angle");
    }

    public static double getEnemy1RV() {
        return getDouble("enemy1.RV");
    }

    public static long getEnemy1ShotCooldown() {
        return getLong("enemy1.shotCooldown");
    }

    public static long getEnemy1ShotRandom() {
        return getLong("enemy1.shotRandom");
    }

    public static double getEnemy1ProjectileSpeed() {
        return getDouble("enemy1.projectileSpeed");
    }

    // ENEMY 2
    
    public static double getEnemy2Radius() {
        return getDouble("enemy2.radius");
    }

    public static double getEnemy2VX() {
        return getDouble("enemy2.VX");
    }

    public static double getEnemy2VY() {
        return getDouble("enemy2.VY");
    }

    public static double getEnemy2Angle() {
        return getDouble("enemy2.angle");
    }

    public static double getEnemy2RV() {
        return getDouble("enemy2.RV");
    }

    public static double getEnemy2ThresholdY() {
        return getDouble("enemy2.thresholdY");
    }

    public static double getEnemy2RVChange() {
        return getDouble("enemy2.RVChange");
    }

    public static double getEnemy2ProjectileSpeed() {
        return getDouble("enemy2.projectileSpeed");
    }

    public static int getEnemy2ShotAngles() {
        return getInt("enemy2.shotAngles");
    }

    public static double getEnemy2AngleSpread() {
        return getDouble("enemy2.angleSpread");
    }

    // BOSS
    
    public static double getBossDefaultRadius() {
        return getDouble("boss.defaultRadius");
    }

    public static long getBossSuperAttackDuration() {
        return getLong("boss.superAttackDuration");
    }

    public static long getBossNextSuperAttackDelay() {
        return getLong("boss.nextSuperAttackDelay");
    }

    // BOSS 1
    
    public static double getBoss1Radius() {
        return getDouble("boss1.radius");
    }

    public static double getBoss1VX() {
        return getDouble("boss1.VX");
    }

    public static double getBoss1VY() {
        return getDouble("boss1.VY");
    }

    public static double getBoss1Angle() {
        return getDouble("boss1.angle");
    }

    public static long getBoss1ShieldTime() {
        return getLong("boss1.shieldTime");
    }

    public static long getBoss1ShieldDuration() {
        return getLong("boss1.shieldDuration");
    }

    public static long getBoss1ShieldInterval() {
        return getLong("boss1.shieldInterval");
    }

    public static double getBoss1PositionY() {
        return getDouble("boss1.positionY");
    }

    public static long getBoss1SuperAttackInterval() {
        return getLong("boss1.superAttackInterval");
    }

    public static long getBoss1SuperAttackRandom() {
        return getLong("boss1.superAttackRandom");
    }

    public static long getBoss1SuperAttackCooldown() {
        return getLong("boss1.superAttackCooldown");
    }

    public static long getBoss1NormalShotCooldown() {
        return getLong("boss1.normalShotCooldown");
    }

    public static long getBoss1NormalShotRandom() {
        return getLong("boss1.normalShotRandom");
    }

    public static double getBoss1SuperProjectileSpeed() {
        return getDouble("boss1.superProjectileSpeed");
    }

    public static double getBoss1NormalProjectileSpeed() {
        return getDouble("boss1.normalProjectileSpeed");
    }

    public static double getBoss1NormalProjectileVY() {
        return getDouble("boss1.normalProjectileVY");
    }

    public static double getBoss1SuperProjectileRadius() {
        return getDouble("boss1.superProjectileRadius");
    }

    public static double getBoss1NormalProjectileRadius() {
        return getDouble("boss1.normalProjectileRadius");
    }

    public static double getBoss1BorderX() {
        return getDouble("boss1.borderX");
    }

    public static double getBoss1BorderWidth() {
        return getDouble("boss1.borderWidth");
    }

    // BOSS 2
    
    public static double getBoss2Radius() {
        return getDouble("boss2.radius");
    }

    public static double getBoss2VX() {
        return getDouble("boss2.VX");
    }

    public static double getBoss2VY() {
        return getDouble("boss2.VY");
    }

    public static double getBoss2Angle() {
        return getDouble("boss2.angle");
    }

    public static long getBoss2SuperAttackInterval() {
        return getLong("boss2.superAttackInterval");
    }

    public static long getBoss2SuperAttackRandom() {
        return getLong("boss2.superAttackRandom");
    }

    public static long getBoss2SuperAttackCooldown() {
        return getLong("boss2.superAttackCooldown");
    }

    public static long getBoss2NormalShotCooldown() {
        return getLong("boss2.normalShotCooldown");
    }

    public static long getBoss2NormalShotRandom() {
        return getLong("boss2.normalShotRandom");
    }

    public static double getBoss2ProjectileSpeed() {
        return getDouble("boss2.projectileSpeed");
    }

    public static double getBoss2ProjectileRadius() {
        return getDouble("boss2.projectileRadius");
    }

    public static int getBoss2ShotsCount() {
        return getInt("boss2.shotsCount");
    }

    public static double getBoss2AngleSpread() {
        return getDouble("boss2.angleSpread");
    }

    public static double getBoss2TargetY() {
        return getDouble("boss2.targetY");
    }

    public static double getBoss2MinY() {
        return getDouble("boss2.minY");
    }

    public static double getBoss2MaxY() {
        return getDouble("boss2.maxY");
    }

    public static double getBoss2SuperAttackSpeed() {
        return getDouble("boss2.superAttackSpeed");
    }

    public static double getBoss2ReturnSpeed() {
        return getDouble("boss2.returnSpeed");
    }

    public static double getBoss2BorderX() {
        return getDouble("boss2.borderX");
    }

    public static double getBoss2BorderWidth() {
        return getDouble("boss2.borderWidth");
    }

    public static double getBoss2BorderYMin() {
        return getDouble("boss2.borderYMin");
    }

    public static double getBoss2BorderYMax() {
        return getDouble("boss2.borderYMax");
    }

    // POWERUP
    
    public static double getPowerupDefaultRadius() {
        return getDouble("powerup.defaultRadius");
    }

    public static double getPowerupDefaultVX() {
        return getDouble("powerup.defaultVX");
    }

    public static double getPowerupDefaultVY() {
        return getDouble("powerup.defaultVY");
    }

    public static double getPowerupVYRandom() {
        return getDouble("powerup.VYRandom");
    }

    public static double getPowerupAngle() {
        return getDouble("powerup.angle");
    }

    public static double getPowerupRV() {
        return getDouble("powerup.RV");
    }

    public static int getPowerupColorGradientTick() {
        return getInt("powerup.colorGradientTick");
    }

    public static long getPowerupDefaultDuration() {
        return getLong("powerup.defaultDuration");
    }

    // POWERUP 1
    
    public static long getPowerup1Duration() {
        return getLong("powerup1.duration");
    }

    public static int getPowerup1ColorSteps() {
        return getInt("powerup1.colorSteps");
    }

    public static double getPowerup1SpeedIncrement() {
        return getDouble("powerup1.speedIncrement");
    }

    // POWERUP 2
    
    public static long getPowerup2Duration() {
        return getLong("powerup2.duration");
    }

    public static int getPowerup2ColorSteps() {
        return getInt("powerup2.colorSteps");
    }

    public static double getPowerup2VY() {
        return getDouble("powerup2.VY");
    }

    public static double getPowerup2VYRandom() {
        return getDouble("powerup2.VYRandom");
    }

    public static double getPowerup2SpeedDecrement() {
        return getDouble("powerup2.speedDecrement");
    }

    public static double getPowerup2MaxSpeed() {
        return getDouble("powerup2.maxSpeed");
    }

    // PROJECTILE
    
    public static double getProjectileDefaultRadius() {
        return getDouble("projectile.defaultRadius");
    }

    // LIFE BAR
    
    public static double getLifeBarVY() {
        return getDouble("lifeBar.VY");
    }

    public static double getLifeBarAngle() {
        return getDouble("lifeBar.angle");
    }

    public static double getLifeBarMaxY() {
        return getDouble("lifeBar.maxY");
    }

    public static double getLifeBarWidth() {
        return getDouble("lifeBar.width");
    }

    public static double getLifeBarHeight() {
        return getDouble("lifeBar.height");
    }

    // STAR
    
    public static double getStarCount() {
        return getDouble("star.count");
    }

    public static double getStarVX() {
        return getDouble("star.VX");
    }

    public static double getStarSize() {
        return getDouble("star.size");
    }

    // CORES
    
    public static Color getColorPlayer() {
        return getColor("color.player");
    }

    public static Color getColorPlayerPowerup() {
        return getColor("color.playerPowerup");
    }

    public static Color getColorEnemy1() {
        return getColor("color.enemy1");
    }

    public static Color getColorEnemy1Hit() {
        return getColor("color.enemy1Hit");
    }

    public static Color getColorEnemy2() {
        return getColor("color.enemy2");
    }

    public static Color getColorEnemy2Hit() {
        return getColor("color.enemy2Hit");
    }

    public static Color getColorEnemy2Draw() {
        return getColor("color.enemy2Draw");
    }

    public static Color getColorBoss1() {
        return getColor("color.boss1");
    }

    public static Color getColorBoss1Super() {
        return getColor("color.boss1Super");
    }

    public static Color getColorBoss1Shield() {
        return getColor("color.boss1Shield");
    }

    public static Color getColorBoss2() {
        return getColor("color.boss2");
    }

    public static Color getColorBoss2Super() {
        return getColor("color.boss2Super");
    }

    public static Color getColorPowerup1Green() {
        return getColor("color.powerup1Green");
    }

    public static Color getColorPowerup1Yellow() {
        return getColor("color.powerup1Yellow");
    }

    public static Color getColorPowerup2Yellow() {
        return getColor("color.powerup2Yellow");
    }

    public static Color getColorPowerup2Magenta() {
        return getColor("color.powerup2Magenta");
    }

    public static Color getColorProjectilePlayer() {
        return getColor("color.projectilePlayer");
    }

    public static Color getColorProjectileEnemy() {
        return getColor("color.projectileEnemy");
    }

    public static Color getColorHit() {
        return getColor("color.hit");
    }

    // GAME ENGINE
    
    public static int getGameEnginePlayerProjectilesCount() {
        return getInt("gameEngine.playerProjectilesCount");
    }

    public static int getGameEngineEnemyProjectilesCount() {
        return getInt("gameEngine.enemyProjectilesCount");
    }

    public static int getGameEngineBossProjectilesCount() {
        return getInt("gameEngine.bossProjectilesCount");
    }

    public static int getGameEngineEnemy1Count() {
        return getInt("gameEngine.enemy1Count");
    }

    public static int getGameEngineEnemy2Count() {
        return getInt("gameEngine.enemy2Count");
    }

    public static int getGameEnginePowerup1Count() {
        return getInt("gameEngine.powerup1Count");
    }

    public static int getGameEnginePowerup2Count() {
        return getInt("gameEngine.powerup2Count");
    }

    public static double getGameEngineEnemy2SpawnX() {
        return getDouble("gameEngine.enemy2SpawnX");
    }
} 