import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class Hero extends Element {

    public Hero(int x, int y) {
        super(x, y);
    }

    public Position moveUp() {
        return new Position(getPosition().getX(), getPosition().getY() - 1);
    }

    public Position moveDown() {
        return new Position(getPosition().getX(), getPosition().getY() + 1);
    }

    public Position moveLeft() {
        return new Position(getPosition().getX() - 1, getPosition().getY());
    }

    public Position moveRight() {
        return new Position(getPosition().getX() + 1, getPosition().getY());
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#F72AB6"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), "X");
    }

}
