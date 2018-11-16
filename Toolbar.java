import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Toolbar extends JToolBar implements PropertyChangeListener { //should this implement this interface???

    private Model model;

    public Toolbar(Model model) {

        this.model = model;
        model.addObserver(this);
        setLayout(new GridLayout(2, 4));
        this.add(new JButton ("Undo"));
        this.add(new JButton ("Redo"));
        JTextField JTF = new JTextField();
        this.add(JTF);
        JButton JB = new JButton ("Change Max Iterations");
        JB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                model.updateMaxIteration(JTF.getText());
                JTF.setText("");
            }
        });
        this.add(JB);
        this.add(new JButton ("Display Zoom"));
        this.add(new JButton ("Zoom Label"));
        this.add(new JButton ("Zoom"));
        this.add(new JButton ("Pan"));

    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {

    }
}
