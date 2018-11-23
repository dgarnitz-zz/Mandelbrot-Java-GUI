import delegate.Border;
import model.Model;

/**
 * Class holding the main method of the project, existing outside both the delegate and model packages.
 * @author David Garnitz, dag8@st-andrews.ac.uk
 */
public class MandelbrotMain {

    /**
     * Main method of the MandelbrotMain class, used create the Model and Delegate objects that create the GUI.
     * @param args
     */
    public static void main(String[] args){

        Model model = new Model();
        Border border = new Border(model);

    }
}
