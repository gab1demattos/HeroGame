import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

public class Arena {
    private int width;
    private int height;
    private Hero hero;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(new Position(10, 10));
    }

    public boolean canHeroMove(Position position) {
        return (0 <= position.getX() && position.getX() < width) && (0 <= position.getY() && position.getY() < height);
    }

    private void moveHero(Position position) {
        if (canHeroMove(position))
            hero.setPosition(position);
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
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
}
