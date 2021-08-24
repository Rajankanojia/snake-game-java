import javax.swing.JFrame;

public class GameFrame extends JFrame {
	GameFrame(){
			
		this.add(new GamePanel()); //// GamePannel pannel = new GamePannel();this.add(pannel); same as this.add(new GamePannel());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false); // for window size
		this.pack();
		this.setVisible(true); // this boolean will show the entire gaming frame
		this.setLocationRelativeTo(null);
		
	}
}