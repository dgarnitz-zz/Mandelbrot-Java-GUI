package delegate;

import model.Model;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;


/**
 * The Border class is the highest level component of the GUI and of the delegate package.
 * It contains the Toolbar, MandelbrotPanel and Menu.
 * All three are configured inside the constructor of the Border class. The MouseListenerClass is also added as a
 * listener of the MandelbrotPanel class. The behavior and size of the JFrame is also configured inside the constructor.
 * This class contains static attributes that are used to assist with the toggling of zoom, pan, displaying zoom and
 * coloring.
 * The class implements PropertyChangeListener, allowing it to listen for PropertyChange events sent by the model,
 * telling it how to react to user behavior.
 */
public class Border extends JFrame implements PropertyChangeListener {
    public static Boolean enableZoom = false;
    public static Boolean enablePan = false;
    public static Boolean color = false;
    public static Boolean displayZoom = false;
    private Model model;
    public MandelbrotPanel MbP;
    private Container cp;
    public MouseListenerClass MLC;

    public Border(Model model) {
        this.model = model;
        model.addObserver(this);

        cp = getContentPane();
        cp.setLayout(new BorderLayout());

        //Menu
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        menu.add(file);

        //save
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Look for file to load");
                int selection = fileChooser.showSaveDialog(cp);
                if(selection == fileChooser.APPROVE_OPTION) {
                    File userSelection = fileChooser.getSelectedFile();
                    model.save(userSelection);
                }
            }
        });
        file.add(save);

        //load
        JMenuItem load = new JMenuItem("Load");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Look for file to load");
                int selection = fileChooser.showOpenDialog(cp);
                if(selection == fileChooser.APPROVE_OPTION) {
                    File userSelection = fileChooser.getSelectedFile();
                    model.load(userSelection);
                }
            }
        });
        file.add(load);

        this.setJMenuBar(menu);

        //create Toolbar, configure it, add it to content pane
        Toolbar TB = new Toolbar(model);
        TB.setBounds(0, 0, 1000, 50);
        cp.add(TB, BorderLayout.NORTH);

        //create MandelbrotPanel, configure it, add it to content pane
        MbP = new MandelbrotPanel(model);
        MbP.setBounds(0,51, 1000, 950);
        cp.add(MbP);

        //Create and add the MouseListener class as a listener to the MandelbrotPanel
        MLC = new MouseListenerClass(this.model, MbP);
        MbP.addMouseListener(MLC);
        MbP.addMouseMotionListener(MLC);

        //JFrame configurations
        setSize(1000,1000);
        setVisible (true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * The propertyChange method listens for events and calls the MandelbrotPanel's repaint method whenever it
     * detects one.
     * @param evt A PropertyChangeEvent fired by the model's PropertyChangeSupport object's firePropertyChange method.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                MbP.repaint();
            }
        });
    }

}
