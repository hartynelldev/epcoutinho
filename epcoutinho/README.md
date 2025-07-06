# EP Coutinho - Sistema de Jogo

## Visão Geral

Este é um jogo de tiro espacial desenvolvido em Java. O sistema agora inclui um **LevelManager** integrado que controla quais inimigos, bosses e powerups aparecem baseado em arquivos de configuração.

## Estrutura do Projeto

```
epcoutinho/
├── Config/
│   ├── gameConfig.txt          # Configurações gerais do jogo
│   └── levels/
│       ├── fase1.txt           # Nível 1
│       └── fase2.txt           # Nível 2
├── Engine/
│   ├── gameEngine.java         # Motor principal do jogo
│   ├── levelManager.java       # Gerenciador de níveis
│   └── ...
├── GameElements/
│   └── Entities/
│       ├── EnemyModels/        # Inimigos
│       ├── PowerUps/           # Powerups
│       └── ProjectileModels/   # Projéteis
└── Main.java                   # Ponto de entrada
```

## Sistema de Configuração

### Arquivo de Configuração Principal (`Config/gameConfig.txt`)

O arquivo `gameConfig.txt` contém todas as configurações do jogo no formato `chave=valor`:

```txt
# Exemplo de configurações
player.shootingSpeed=100
enemy1.radius=9.0
boss1.radius=50.0
color.player=BLUE
```

### Configurações do LevelManager

```txt
# Configurações do LevelManager
levelManager.defaultLevelFile=Config/levels/fase1.txt
levelManager.enemySpawnInterval=500
levelManager.powerupSpawnInterval=500
levelManager.bossSpawnDelay=5000
levelManager.levelTransitionTime=10000
```

## Sistema de Níveis

### Formato dos Arquivos de Nível

Os arquivos de nível (ex: `Config/levels/fase1.txt`) usam o formato:

```txt
# Comentários começam com #
TIPO ID X Y TEMPO_MS

# Exemplos:
INIMIGO 1 100 50 1000    # Enemy1 na posição (100,50) no tempo 1000ms
CHEFE 1 240 150 8000      # Boss1 na posição (240,150) no tempo 8000ms
POWERUP 2 240 200 3000    # Powerup2 na posição (240,200) no tempo 3000ms
```

### Tipos de Entidades

- **INIMIGO**: 
  - ID 1 = Enemy1 (inimigo básico)
  - ID 2 = Enemy2 (inimigo mais difícil)

- **CHEFE**:
  - ID 1 = Boss1 (chefe básico)
  - ID 2 = Boss2 (chefe mais difícil)

- **POWERUP**:
  - ID 1 = Powerup1 (aumenta velocidade de tiro)
  - ID 2 = Powerup2 (diminui velocidade dos inimigos)

## Como Usar

### Executando o Jogo

```java
// No Main.java
GameConfig.initialize();           // Inicializa configurações
gameEngine game = new gameEngine();
game.setUseLevelManager(true);     // Ativa o levelManager
game.loadLevel(1);                // Carrega o nível 1
game.run();                       // Executa o jogo
```

### Criando Novos Níveis

1. Crie um arquivo `.txt` na pasta `Config/levels/`
2. Use o formato especificado acima
3. Carregue o nível no código:

```java
game.loadLevel(2);  // Carrega fase2.txt
```

### Modo Legacy vs LevelManager

O sistema suporta dois modos:

**Modo LevelManager (Padrão):**
- Controla entidades baseado em arquivos de nível
- Mais flexível e configurável
- Permite criação de níveis complexos

**Modo Legacy:**
- Comportamento original do jogo
- Spawn automático de inimigos
- Para usar: `game.setUseLevelManager(false)`

## Controles

- **Setas**: Movimentação do player
- **Ctrl**: Disparo de projéteis
- **ESC**: Sair do jogo

## Funcionalidades do LevelManager

### Eventos Programados
- Spawn de inimigos em tempos específicos
- Aparição de powerups em momentos estratégicos
- Bosses que aparecem em momentos definidos

### Spawn Automático
- Inimigos extras aparecem automaticamente
- Powerups são gerados periodicamente
- Boss aparece após um delay configurável

### Transição de Níveis
- Detecção automática de conclusão do nível
- Transição suave para o próximo nível
- Sistema de pontuação (implementar se necessário)

## Configurações Avançadas

### Ajustando Dificuldade

Modifique os valores no `gameConfig.txt`:

```txt
# Inimigos mais rápidos
enemy1.VY=0.30
enemy2.VY=0.50

# Bosses mais resistentes
boss1.shieldTime=15000
boss2.radius=120.0

# Powerups mais frequentes
levelManager.powerupSpawnInterval=300
```

### Criando Níveis Complexos

```txt
# Onda inicial
INIMIGO 1 100 50 1000
INIMIGO 1 200 50 1200
INIMIGO 1 300 50 1400

# Powerup estratégico
POWERUP 1 240 200 2000

# Onda de Enemy2
INIMIGO 2 150 50 4000
INIMIGO 2 250 50 4200

# Boss final
CHEFE 2 240 150 8000
```

## Extensibilidade

O sistema foi projetado para ser facilmente extensível:

- Adicione novos tipos de inimigos
- Crie novos powerups
- Implemente diferentes bosses
- Adicione novos tipos de eventos nos níveis

## Troubleshooting

### Problemas Comuns

1. **Arquivo de nível não encontrado**: Verifique o caminho em `levelManager.defaultLevelFile`
2. **Entidades não aparecem**: Verifique o formato do arquivo de nível
3. **Configurações não carregam**: Verifique o arquivo `gameConfig.txt`

### Logs de Debug

O sistema fornece logs informativos:
- Configurações carregadas com sucesso
- Nível carregado com X eventos
- Erros de parsing de arquivos
- Valores padrão sendo usados
