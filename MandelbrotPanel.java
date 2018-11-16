import java.awt.Graphics;
import javax.swing.JPanel;

public class MandelbrotPanel extends JPanel  {

    /*private Model model;

    public MandelbrotPanel(Model model){
        this.model = model;
    }*/


    @Override
    public void paint (Graphics g) {
        //g.drawLine (0, 0, 75, 75);
        renderDefaultMandelbrot(g);

    }

	public void renderDefaultMandelbrot(Graphics g){

        int x = Model.defaultX;
        int y = Model.defaultY;

        int[][] MC_data = Model.defaultMB(Model.MAX_ITERATIONS);
        for(int i=0; i<y; i++){
            String row = "";
            for(int j=0; j<x; j++){
                row = row + MC_data[i][j] + " ";
                if(MC_data[i][j] >= MandelbrotCalculator.INITIAL_MAX_ITERATIONS) {
                    g.drawLine(j, i, j, i);
                }
            }
        }
	}

	//NEED another method for rendering a configured Mandelbrot
}
