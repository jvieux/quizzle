import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Quizzle extends Application{
	
	QuizzleFE yo = new QuizzleFE();
	
	public static void main( String[] args)
		{launch(args);}
	
	
	
	@Override
    public void start(Stage stage)
    { initUI(stage); }

    private void initUI(Stage stage)
    {   
    	Group root = new Group();
    	
    	yo.begin(root);
       
        Scene scene = new Scene(root, 1000, 1000);
        
        scene.setFill(Color.WHEAT);

        stage.setTitle("Quizzle");
	    stage.setScene(scene);
	     
	    stage.show();
	        
   }
}
