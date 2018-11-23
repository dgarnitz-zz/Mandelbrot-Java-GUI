package delegate;

import model.Model;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;


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
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        menu.add(file);

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

        Toolbar TB = new Toolbar(model);
        TB.setBounds(0, 0, 1000, 50);
        cp.add(TB, BorderLayout.NORTH);

        MbP = new MandelbrotPanel(model);
        MbP.setBounds(0,51, 1000, 950);
        cp.add(MbP);

        MLC = new MouseListenerClass(this.model, MbP);
        MbP.addMouseListener(MLC);
        MbP.addMouseMotionListener(MLC);

        setSize(1000,1000);
        setVisible (true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                MbP.repaint();
            }
        });
    }

}
