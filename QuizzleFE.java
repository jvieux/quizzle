import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

//FrontEnd
public class QuizzleFE {
	
	QuizzleBE be = new QuizzleBE();
	Label hi;
	Label a;
	TextField fileEntry;
	Button go;
	Label qNum;
	Label qLabel;
	Label aLabel;
	String currQuestion;
	boolean afterChoice = false;
	boolean qAnswered = false;
	int count = 1;
	int correct = 0;
	double score = 0;
	Label scoree;
	
	public void begin(Group root) {
		hi = new Label("Welcome to Quizzle.");
		a = new Label("What file will we be studying from?");
		fileEntry = new TextField();
		go = new Button("Load File/Start Quiz");
		
		hi.setLayoutY(100);
		hi.setLayoutX(0);
		hi.setAlignment(Pos.CENTER);
		hi.setPrefSize(1000, 100);
		hi.setFont(Font.font(null, FontWeight.BOLD,40));
		
		a.setLayoutY(300);
		a.setLayoutX(0);
		a.setAlignment(Pos.CENTER);
		a.setPrefSize(1000, 100);
		a.setFont(Font.font(null, FontWeight.BOLD, 25));
		
		fileEntry.setLayoutY(500);
		fileEntry.setLayoutX(250);
		//fileEntry.setAlignment(Pos.CENTER);
		fileEntry.setPrefSize(500, 50);
		
		
		go.setLayoutY(600);
		go.setLayoutX(400);
		go.setPrefSize(200, 50);
		go.setBackground(new Background(new BackgroundFill(Color.YELLOWGREEN, null, null)));
		go.setFont(Font.font(null, FontWeight.BOLD, 15));
		
		go.setOnAction((ActionEvent event)->
		{
			if(!be.loadFile(fileEntry.getText())) {
				Label uhOh = new Label("Could not find this file. Please try again.");
				uhOh.setLayoutY(550);
				uhOh.setLayoutX(300);
				uhOh.setPrefSize(400, 50);
				uhOh.setAlignment(Pos.CENTER);
				uhOh.setBackground(new Background(new BackgroundFill(Color.SALMON, null, null)));
				root.getChildren().add(uhOh);
			}
			else {
				gameSetUp(root);
			}
			
		});
		
		
		root.getChildren().add(hi);
		root.getChildren().add(a);
		root.getChildren().add(fileEntry);
		root.getChildren().add(go);
		
	}
	
	public void gameSetUp(Group root) {
		root.getChildren().remove(hi);
		root.getChildren().remove(a);
		root.getChildren().remove(fileEntry);
		root.getChildren().remove(go);
		
		qNum = new Label("Question #1");
		qNum.setLayoutY(10);
		qNum.setLayoutX(0);
		qNum.setAlignment(Pos.CENTER);
		qNum.setPrefSize(1000, 100);
		qNum.setFont(Font.font(null, FontWeight.BOLD,40));
		root.getChildren().add(qNum);
		
		scoree = new Label();
		scoree.setText(score + "%");
		scoree.setFont(Font.font(null, FontWeight.BOLD,30));
		scoree.setLayoutX(400);
		scoree.setAlignment(Pos.CENTER);
		scoree.setLayoutY(650);
		scoree.setPrefSize(200, 60);
		scoree.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, null, null)));
		root.getChildren().add(scoree);
		
		
		askQuestion(root);
		nextBtn(root);
		stOver(root);
	}
	
	public void askQuestion(Group root) {
		VBox qBox = new VBox(10);
		qBox.setPrefSize(500, 480);
		qBox.setLayoutY(150);
		qBox.setLayoutX(250);
		qBox.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, null, null)));
		qBox.setAlignment(Pos.TOP_CENTER);
		
		currQuestion = be.pickQuestion();
		
		qLabel = new Label(be.getQCat() + " " + currQuestion);
		qLabel.setAlignment(Pos.CENTER);
		qLabel.setPrefSize(1000, 100);
		//qLabel.setPrefSize(1000, 60);
		qLabel.setFont(Font.font(null, FontWeight.BOLD,30));
		qBox.getChildren().add(qLabel);
		
		aLabel = new Label(be.getACat() + "?");
		aLabel.setAlignment(Pos.CENTER);
		aLabel.setPrefSize(1000, 60);
		//aLabel.setPrefSize(1000, 60);
		aLabel.setFont(Font.font(null, FontWeight.BOLD,30));
		qBox.getChildren().add(aLabel);
		
		ArrayList<String> answers = be.getAnsOptions(currQuestion);
		
		for(int j=0; j<4; j++) {
			Button ansButton = new Button();
			String bText = answers.get(j);
			ansButton.setText(bText);
			ansButton.setPrefSize(400, 60);
			ansButton.setAlignment(Pos.CENTER);
			ansButton.setLayoutX(50);
			ansButton.setFont(Font.font(null, FontWeight.BOLD,30));
			if(be.isRight(bText,currQuestion)) {
				ansButton.setId("correct");
			}
			ansButton.setOnAction((ActionEvent event)->
			{
				if(!afterChoice) {
					if(be.isRight(bText, currQuestion)) {
						ansButton.setBackground(new Background(new BackgroundFill(Color.LIMEGREEN, null, null)));
						afterChoice = true;
						qAnswered = true;
						count++;
						correct++;
						newScore(root);
					}
					else {
						ansButton.setBackground(new Background(new BackgroundFill(Color.ORANGERED, null, null)));
						qBox.lookup("#correct").setStyle("-fx-background-color:skyblue");
						afterChoice = true;
						qAnswered = true;
						count++;
						newScore(root);
					}
				}
			});
			qBox.getChildren().add(ansButton);
		}
		
		root.getChildren().add(qBox);
	}
	
	public void nextBtn(Group root) {
		Button nxt = new Button();
		nxt.setText("Next");
		nxt.setPrefSize(200, 60);
		nxt.setAlignment(Pos.CENTER);
		nxt.setLayoutX(100);
		nxt.setLayoutY(650);
		nxt.setFont(Font.font(null, FontWeight.BOLD,30));
		nxt.setOnAction((ActionEvent event)->
		{
			if(count<6) {
				askQuestion(root);
				afterChoice = false;
				qNum.setText("Question #" + count);
			}
		});
		root.getChildren().add(nxt);
	}
	
	public void stOver(Group root) {
		Button stOver = new Button();
		stOver.setText("Start Over");
		stOver.setPrefSize(200, 60);
		stOver.setAlignment(Pos.CENTER);
		stOver.setLayoutX(700);
		stOver.setLayoutY(650);
		stOver.setFont(Font.font(null, FontWeight.BOLD,30));
		stOver.setOnAction((ActionEvent event)->
		{
			askQuestion(root);
			afterChoice = false;
			count = 1;
			qNum.setText("Question #1");
			correct = 0;
			newScore(root);
			scoree.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, null, null)));
			scoree.setTextFill(Color.BLACK);
		});
		root.getChildren().add(stOver);
	}
	
	public void newScore(Group root) {
		score = correct * 20;
		scoree.setText(score + "%");
		
		if(count==6) {
			scoree.setBackground(new Background(new BackgroundFill(Color.MIDNIGHTBLUE, null, null)));
			scoree.setTextFill(Color.WHITE);
		}
	}
}