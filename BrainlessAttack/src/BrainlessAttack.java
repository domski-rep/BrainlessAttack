import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BrainlessAttack extends Canvas {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private int zombiePositionX, zombiePositionY;
    private Map zombies;
    private static final JFrame mainWindow = new JFrame(" *BRAINLESS ATTACK* ");


    public BrainlessAttack() {
        zombies = new HashMap();
        zombiePositionX = WIDTH / 2;
        zombiePositionY = HEIGHT / 2;

        JPanel mainPanel = (JPanel) mainWindow.getContentPane();
        setBounds(0, 0, WIDTH, HEIGHT);
        mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        mainPanel.setLayout(null);
        mainPanel.add(this);

        mainWindow.setBounds(0, 0, WIDTH, HEIGHT);
        mainWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        mainWindow.setVisible(true);
        centerWindow();
    }

    public void paint(Graphics g) {
        g.drawImage(getZombie("zombie.png"), zombiePositionX, zombiePositionY, this);
    }

    public BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(getClass().getClassLoader().getResource(path));
        } catch (IOException e) {
            System.out.println("Error: ");
            System.out.println(e.getMessage());
            System.exit(0);
            return null;
        }
    }

    public BufferedImage getZombie(String path) {
        BufferedImage image = (BufferedImage) zombies.get(path);
        if (image == null) {
            image = loadImage("images/" + path);
            zombies.put(path, image);
        }

        return image;
    }

    private void updateZombieRandomPosition() {
        zombiePositionX = (int) (Math.random() * WIDTH);
        zombiePositionY = (int) (Math.random() * HEIGHT - 50);
    }

    public void runGame() {
        while (isVisible()) {
            updateZombieRandomPosition();
            paint(getGraphics());
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        BrainlessAttack gameStarter = new BrainlessAttack();
        gameStarter.runGame();

    }

    private static void centerWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - mainWindow.getWidth()) / 2;
        int y = (screenSize.height - mainWindow.getHeight()) / 2;
        mainWindow.setLocation(x, y);

    }
}
