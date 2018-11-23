package delegate;

import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Class for creating a Toolbar that holds the buttons and text field needed to make the GUI work properly. This class
 * is a property change listener, allowing it to detect user interaction with the interface components.
 * This class has buttons to zoom, pan, undo, redo, reset, toggle the color, toggle the zoom display and change the max
 * iterations.
 * The class' only method in use is its constructor, which creates all the interface components, applies all the
 * action listeners, and implements the corresponding method calls from the model.
 */
public class Toolbar extends JToolBar implements PropertyChangeListener {

    private Model model;

    public Toolbar(Model model) {

        this.model = model;
        model.addObserver(this);
        setLayout(new GridLayout(2, 5));

        //Undo
        JButton Undo = new JButton ("Undo");
        Undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                model.undo();
            }
        });
        this.add(Undo);

        //Redo
        JButton Redo = new JButton ("Redo");
        Redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                model.redo();
            }
        });
        this.add(Redo);

        //Max Iterations Text Field & Button
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

        //Display Zoom
        JButton DisplayZoom = new JButton("Display Zoom");
        DisplayZoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                model.displayZoom();
            }
        });
        this.add(DisplayZoom);

        //Reset
        JButton Reset = new JButton ("Reset");
        Reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                model.reset();
            }
        });
        this.add(Reset);

        //Color
        JButton Color = new JButton ("Color");
        Color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                model.addColor();
            }
        });
        this.add(Color);

        //Zoom
        JButton MouseZoom = new JButton ("Zoom");
        MouseZoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                model.zoomWithMouse();
            }
        });
        this.add(MouseZoom);

        //Pan
        JButton MousePan =  new JButton ("Pan");
        MousePan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                model.panWithMouse();
            }
        });
        this.add(MousePan);

    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {}
}
