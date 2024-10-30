import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com. googlecode. lanterna. input. KeyStroke;

import javax.swing.*;
import java.io.IOException;

public class Game {
    private Screen screen;
    private int x = 10;
    private int y = 10;

    public Game() {
        try {
            TerminalSize terminalSize = new TerminalSize(40, 20);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);


            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);

            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void draw() throws IOException {
        screen.clear();
        screen.setCharacter(x, y, TextCharacter.fromCharacter('X')[0]);
        screen.refresh();
    }

    public void run() {
        while(true) {
            try {
                draw();
                KeyStroke key = screen.readInput();
                if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') screen.close();
                if (key.getKeyType() == KeyType.EOF) break;
                processKey(key);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processKey(KeyStroke key) {
        System.out.println(key);
        switch (key.getKeyType()) {
            case KeyType.ArrowUp -> y--;
            case KeyType.ArrowDown -> y++;
            case KeyType.ArrowLeft -> x--;
            case KeyType.ArrowRight -> x++;
        }
    }
}
