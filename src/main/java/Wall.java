import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.List;

public class Wall {
    private List<Wall> walls;
    private Position position;

    public Wall(int x, int y) {
        this.position = new Position(x, y);
    }

    public void add(List<Wall> walls) {

    }

    public void draw(TextGraphics graphics) {

    }
}
