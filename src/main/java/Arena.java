import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private int width;
    private int height;
    private Hero hero;
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(10, 10);
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean canHeroMove(Position position) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position))
                return false;
        }
        return (0 < position.getX() && position.getX() < width - 1) && (0 < position.getY() && position.getY() < height - 1);
    }


    private void moveHero(Position position) {
        if (canHeroMove(position))
            hero.setPosition(position);
        retrieveCoins();
    }

    public void moveMonsters() {
        for (Monster monster : monsters) {
            monster.setPosition(monster.move(this));
        }
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
        for (Wall wall : walls)
            wall.draw(graphics);
        for (Coin coin : coins)
            coin.draw(graphics);
        for (Monster monster : monsters)
            monster.draw(graphics);
    }

    public void processKey(KeyStroke key) {
        System.out.println(key);
        switch (key.getKeyType()) {
            case KeyType.ArrowUp -> moveHero(hero.moveUp());
            case KeyType.ArrowDown -> moveHero(hero.moveDown());
            case KeyType.ArrowLeft -> moveHero(hero.moveLeft());
            case KeyType.ArrowRight -> moveHero(hero.moveRight());
        }
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Coin newcoin = new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
            if ( (!hero.getPosition().equals(newcoin.getPosition())) && (!coins.contains(newcoin)) )
                coins.add(newcoin);
        }
        return coins;
    }

    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for(int i=0; i<5; i++){
            Monster newmonster = new Monster(random.nextInt(width-2) + 1, random.nextInt(height-2)+1);
            if(!monsters.contains(newmonster) && !newmonster.getPosition().equals(hero.getPosition()))
                monsters.add(newmonster);
        }
        return monsters;
    }

    private void retrieveCoins() {
        for (Coin coin : coins) {
            if (hero.getPosition().equals(coin.getPosition())) {
                coins.remove(coin);
                break;
            }
        }
    }

    public boolean verifyCoinsEmpty() {
        return coins.isEmpty();
    }

    public boolean verifyMonsterCollisions() {
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(hero.getPosition()))
                return true;
        }
        return false;
    }

    public void displayMessageWin(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        graphics.setForegroundColor(TextColor.Factory.fromString("#080404"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(getWidth()/2, getHeight()/2), "You won!!");
    }

    public void displayMessageDeath(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        graphics.setForegroundColor(TextColor.Factory.fromString("#080404"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(getWidth()/2, getHeight()/2), "You are dead.");
    }

}
