package winFlotte;

import javax.swing.JFrame;

public class Fenetre extends JFrame {

	private Pan pc;
	
	Fenetre()
	{
		
		this.setSize(800, 600);
		this.setResizable(true);
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		pc = new Pan();
		this.setContentPane(pc);
		this.setTitle("Gestionnaire de Flotte");
		this.setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Fenetre f = new Fenetre();
	}

}
