package delegate;

import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Toolbar extends JToolBar implements PropertyChangeListener {

    private Model model;

    public Toolbar(Model model) {

        this.model = model;
        model.addObserver(this);
        setLayout(new GridLayout(2, 4));

        JButton Undo = new JButton ("Undo");
        Undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                model.undo();
            }
        });
        this.add(Undo);

        JButton Redo = new JButton ("Redo");
        Redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                model.redo();
            }
        });
        this.add(Redo);

        JTextField EnterMaxIterations = new JTextField();
        this.add(EnterMaxIterations);

        JButton MaxIterations = new JButton ("Change Max Iterations");
        MaxIterations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                model.updateMaxIteration(EnterMaxIterations.getText());
                EnterMaxIterations.setText("");
            }
        });
        this.add(MaxIterations);

        JButton Reset = new JButton ("Reset");
        Reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                model.reset();
            }
        });
        this.add(Reset);


        this.add(new JButton ("Color"));

        JButton MouseZoom = new JButton ("Zoom With Mouse");
        MouseZoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                model.zoomWithMouse();
            }
        });
        this.add(MouseZoom);

        this.add(new JButton ("Pan"));

    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {}
}
