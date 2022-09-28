import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Employee {
    private String name;
    private String id;
    private BufferedImage image;

    public BufferedImage getImage() {
        return image;
    }

    public Employee(String name, String id, BufferedImage image) {
        this.name = name;
        this.id = id;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
