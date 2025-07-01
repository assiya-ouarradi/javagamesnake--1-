import javax.swing.JFrame ;

 public class GameFrame extends JFrame{

    public GameFrame(){
        GamePanel panel=new GamePanel();
        this.add(panel);
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack(); // Adjusts the frame size to fit the panel
        this.setVisible(true);
    }
  

 }