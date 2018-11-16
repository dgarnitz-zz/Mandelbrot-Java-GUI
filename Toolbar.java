import javax.swing.*;
import java.awt.*;

public class Toolbar extends JToolBar {

    public Toolbar() {
        setLayout(new GridLayout(1, 5));
        this.add(new JButton ("Test 1"));
        this.add(new JButton ("Test 2"));
        this.add(new JButton ("Test 3"));
        this.add(new JButton ("Test 4"));
        this.add(new JButton ("Test 5"));

    }
}
