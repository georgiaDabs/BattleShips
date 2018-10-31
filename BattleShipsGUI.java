import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.embed.swing.JFXPanel;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.control.Label;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.stage.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Collection;
public class BattleShipsGUI{
    private HashMap<Button,Position> buttons1;
    private HashMap<Position,Button> reverseB1;
    private HashMap<Button,Position> buttons2;
    private HashMap<Position,Button> reverseB2;
    private PlayerInterface player1;
    private PlayerInterface player2;
    private Label l1;
    private Label l2;
    private HashMap<PlayerInterface, Boolean> isHuman;
    private static BattleShipsGUI thisGUI;
    private Position justClicked=null;
    public static void main(String[] args){
        thisGUI=getGUI();
    }
    public BattleShipsGUI(){
        player1=null;
        player2=null;
        isHuman=new HashMap<>();
        isHuman.put(player1,false);
        isHuman.put(player2, false);
        buttons1=new HashMap<>();
        buttons2=new HashMap<>();
        reverseB1=new HashMap<>();
        reverseB2=new HashMap<>();
        launchFX();
        //this.thisGUI=getGUI();
    }
    
    public void initiliseGUI(){
       // launchFX();
       //thisGUI=getGUI();
        Stage stage=new Stage();
        stage.setTitle("BattleShips");
        
        StackPane root =new StackPane();
        GridPane mainGrid=new GridPane();
        mainGrid.prefWidthProperty().bind(root.widthProperty());
        mainGrid.prefHeightProperty().bind(root.heightProperty());
        ColumnConstraints column1=new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2=new ColumnConstraints();
        column2.setPercentWidth(50);
        mainGrid.getColumnConstraints().addAll(column1,column2);
        RowConstraints row1=new RowConstraints();
        RowConstraints row2=new RowConstraints();
        RowConstraints row3=new RowConstraints();
        RowConstraints row4=new RowConstraints();
        row1.setPercentHeight(5);
        row2.setPercentHeight(5);
        row3.setPercentHeight(75);
        row4.setPercentHeight(15);
        l1=new Label("Player 1");
        l2=new Label("Player 2");
        mainGrid.add(l1,0,0);
        mainGrid.add(l2,1,0);
        mainGrid.setHgap(5);
        Button comp1=new Button("Computer");
        comp1.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                player1=new ComputerPlayer();
                isHuman.put(player1,false);
                disableButtons(buttons1.values());
            }
        
        });
        Button comp2=new Button("Computer");
        comp2.setOnAction(new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            player2=new ComputerPlayer();
            isHuman.put(player2,false);
            disableButtons(buttons2.values());
        }
        });
        Button playerB1=new Button("Player");
        playerB1.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                player1=new HumanGUIPlayer(thisGUI,1);
                isHuman.put(player1,true);
                enableButtons(buttons1.values());
            }
        });
        Button playerB2=new Button("Player");
        playerB2.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                player2=new HumanGUIPlayer(thisGUI,2);
                isHuman.put(player2,true);
                enableButtons(buttons2.values());
            }
        });
        mainGrid.getRowConstraints().addAll(row1,row2,row3,row4);
        GridPane smallGrid1=new GridPane();
        GridPane smallGrid2=new GridPane();
        ColumnConstraints leftC1=new ColumnConstraints();
        ColumnConstraints leftC2=new ColumnConstraints();
        ColumnConstraints rightC1=new ColumnConstraints();
        ColumnConstraints rightC2=new ColumnConstraints();
        leftC1.setPercentWidth(50);
        leftC2.setPercentWidth(50);
        rightC1.setPercentWidth(50);
        rightC2.setPercentWidth(50);
       smallGrid1.add(playerB1,0,0);
       smallGrid1.add(comp1,1,0);
       smallGrid2.add(playerB2,0,0);
       smallGrid2.add(comp2,1,0);
        smallGrid1.getColumnConstraints().addAll(leftC1,rightC1);
        smallGrid2.getColumnConstraints().addAll(leftC2,rightC2);
        //mainGrid.add(smallGrid1,0,1);
        //mainGrid.add(smallGrid2,1,1);
        GridPane grid1=new GridPane();
        GridPane grid2=new GridPane();
        for(int i=0;i<10;i++){
            ColumnConstraints c=new ColumnConstraints();
            c.setPercentWidth(10);
            RowConstraints r=new RowConstraints();
            r.setPercentHeight(10);
            grid1.getColumnConstraints().add(c);
            grid1.getRowConstraints().add(r);
            grid2.getColumnConstraints().add(c);
            grid2.getRowConstraints().add(r);
        }
        mainGrid.add(grid1,0,2);
        mainGrid.add(grid2,1,2);
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                Button b1=new Button("");
                final int x=i+1;
                final int y=10-j;
                b1.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        clicked(b1);
                        System.out.println("Button "+x+","+y+" was pressed");
                    }
                });
                grid1.add(b1,i,j);
                grid1.setFillWidth(b1,true);
                grid1.setFillHeight(b1,true);
                b1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                Button b2=new Button("");
                grid2.setFillWidth(b2,true);
                grid2.setFillHeight(b2,true);
                b2.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        clicked(b2);
                        System.out.println("Button "+x+","+y+" was pressed");
                    }
                });
                b2.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                grid2.add(b2,i,j);
                try{
                Position pos1=new Position(x,y);
                Position pos2=new Position(x,y);
                buttons1.put(b1,pos1);
                reverseB1.put(pos1,b1);
                buttons2.put(b2,pos2);
                reverseB2.put(pos2,b2);
                
            }catch(InvalidPositionException e){
            
            }
        }
        }
        disableButtons(buttons1.values());
        disableButtons(buttons2.values());
        root.getChildren().add(mainGrid);
        stage.setScene(new Scene(root,1000,650));
        stage.show();
    }
    public static BattleShipsGUI getGUI(){
        BattleShipsGUI thisGUI =new BattleShipsGUI();
        return thisGUI;
    }
    public void launchFX(){
        new JFXPanel();
        Platform.setImplicitExit(false);
        Platform.runLater(()->
        initiliseGUI());
    }
    public void disableButtons(Collection<Position> positions){
        for(Position pos:positions){
            if(reverseB1.containsKey(pos)){
                reverseB1.get(pos).setDisable(true);
            }else if(reverseB2.containsKey(pos)){
                reverseB2.get(pos).setDisable(true);
            }
        }
    }
    public void enableButtons(Collection<Position> positions){
        for(Position pos:positions){
            if(reverseB1.containsKey(pos)){
                reverseB1.get(pos).setDisable(false);
            }else if(reverseB2.containsKey(pos)){
                reverseB2.get(pos).setDisable(false);
            }
        }
    }
    public void changeLabel(int player,String str){
        if(player==1){
            l1.setText(str);
        }else if(player==2){
            l2.setText(str);
        }
    }
    public Position justClicked(){
        return justClicked;
    }
    public void clicked(Button but){
        if(buttons1.keySet().contains(but)){
            justClicked=buttons1.get(but);
        }else if(buttons2.keySet().contains(but)){
            justClicked=buttons2.get(but);
        }
    }
    public void changeButton(int player, Position pos, String fx){
        for(Position p:reverseB1.keySet()){
            if((pos.getX()==p.getX())&&(pos.getY()==p.getY())){
                reverseB1.get(p).setStyle(fx);
            }
        }
            
       
    }
    public PlayerInterface getPlayer1(){
        return player1;
    }
    public PlayerInterface getPlayer2(){
        return player2;
    }
}