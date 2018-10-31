import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.io.File;
import java.util.InputMismatchException ;
public class Game implements GameInterface
{
    private static PlayerInterface player1;
    private static HashMap<PlayerInterface,Boolean> isHuman;
    private static PlayerInterface player2;
    private static HashMap<PlayerInterface,ArrayList<Integer>> misses;
    private static HashMap<PlayerInterface,ArrayList<Position>> shots;
    private BoardInterface board1;
    private BoardInterface board2;

    private boolean newGame;
    private static boolean human1;
    private static boolean human2;
    private static BattleShipsGUI gui;
    private static HashMap<PlayerInterface,HashMap<ShipInterface,Placement>> ships;
    private HashMap<ShipInterface,Placement> h1;
    private HashMap<ShipInterface,Placement> h2;
    private static Game g;
    public Game(PlayerInterface player1, PlayerInterface player2){
        this.player1=player1;
        this.player2=player2;
        board1=new Board();
        board2=new Board();
        ships=new HashMap<>();
        h1=new HashMap<>();
        h2=new HashMap<>();
        ArrayList p1shots=new ArrayList<>();
        ArrayList p2shots=new ArrayList<>();
        shots=new HashMap<>();
        shots.put(player1,p1shots);
        shots.put(player2,p2shots);
        ships.put(player1,h1);
        ships.put(player2,h2);
        misses=new HashMap<>();
        isHuman=new HashMap<>();
        ArrayList<Integer> list1=new ArrayList<>();

        misses.put(player1,list1);
        ArrayList<Integer> list2=new ArrayList<>();

        misses.put(player2,list2);

        newGame=true;
    }

    public HashMap<PlayerInterface,ArrayList<Integer>> getMisses(){
        return misses;
    }

    public void setIsHuman(HashMap<PlayerInterface,Boolean> h){
        this.isHuman=h;
    }

    public static void main(String[] args){
        System.out.println("------BATTLESHIPS-----------");
        boolean ready=false;
        isHuman=new HashMap<>();
        g=new Game(player1,player2);
        //FXMLLoader loader=FXMLLoader(Application.class.getResource("BattleShipScene.fxml"));
        while(!ready){
            if(g==null){
                g=new Game(player1,player2);
            }
            boolean intsEntered=false;
            Scanner sc=new Scanner(System.in);
            int response=0;
            while(!intsEntered){
                try{
                    
                    System.out.println("do you want to play on the GUI or terminal");
                    //attempted GUI it loads but doesn't really do anything else
                    System.out.println("press 1 for GUI, 2 for terminal and 3 to exit");
                    response=sc.nextInt();
                    intsEntered=true;
                }catch(InputMismatchException e){
                    sc.next();
                    System.out.println("please enter a number. try again");
                }
            }    
            if(response==2){
                g.menu();
            }else if(response==1){
                gui=new BattleShipsGUI();
                player1=initiateGUIPlayer("1");
                player2=initiateGUIPlayer("2");
                ready=true;
            }else{
                System.out.println("thanks for playing");
                ready=true;
            }
        }

        //g.play();
    }

    public static PlayerInterface initiateGUIPlayer(String number){
        PlayerInterface player=null;
        int result = JOptionPane.showConfirmDialog(null, "Do you want player "+number+" to be a user?",null, JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.YES_OPTION) {
            player=new HumanGUIPlayer(gui,Integer.parseInt(number));
        } else{
            player=new ComputerPlayer();
        }
        return player;
    }

    public HashMap<PlayerInterface,Boolean> getIsHuman(){
        if(this.isHuman==null){System.out.println("isHuman is null");}
        return this.isHuman;
    }

    public static PlayerInterface initiatePlayer(String number) throws WrongInputException, PauseException{
        PlayerInterface player=null;
        System.out.println("what do you want player "+number+" to be?");
        Scanner sc=new Scanner(System.in);
        boolean intsEntered=false;
        int response=0;
        while(!intsEntered){
            try{
                System.out.println("press 1 for user or 2 for computer");

                response=sc.nextInt();
                intsEntered=true;
            }catch(InputMismatchException e){
                sc.next();
                System.out.println("please enter a number. try again");
            }
        }
        if(response==1){
            System.out.println("enter user name");
            
            String name=sc.next();
            if(name.equals("pause")){
                throw new PauseException();
            }
            player=new HumanConsolePlayer(name);
            if(number.equals("1")){
                human1=true;
            }else{
                human2=true;
            }
        }else if(response==2){
            player=new ComputerPlayer();
            //g.getIsHuman().put(player,false);
            if(number.equals("1")){
                human1=true;
            }else{
                human2=false;
            }
        }else{
            throw new WrongInputException(number);
        }
        return player;
    }

    public HashMap<ShipInterface,Placement> getShips(PlayerInterface player){
        return ships.get(player);
    }

    public ArrayList<Placement> setUp(PlayerInterface player,BoardInterface board) throws PauseException{
        BoardInterface clone= board.clone();
        HashMap<ShipInterface,Placement> hashMap=getShips(player);
        ShipInterface aircraft=new Ship(5);
        ShipInterface battleship=new Ship(4);
        ShipInterface destroyer=new Ship(3);
        ShipInterface submarine=new Ship(3);
        ShipInterface patrol=new Ship(2);
        ArrayList<Placement> placements=new ArrayList<>();
        try{
            //System.out.println("clone=");
            //System.out.println(clone);
            Placement p1=player.choosePlacement(aircraft,clone);
            ships.get(player).put(aircraft,p1);
            board.placeShip(aircraft,p1.getPosition(),p1.isVertical());

            //clone.placeShip(aircraft,p1.getPosition(),p1.isVertical());
            //System.out.println("board outside of place ship method");
            //System.out.println(board);
            clone=board.clone();

            //System.out.println("clone=");
            //System.out.println(clone);
            Placement p2=player.choosePlacement(battleship,clone);
            ships.get(player).put(battleship,p2);
            board.placeShip(battleship,p2.getPosition(),p2.isVertical());
            //ships.get(player).put(battleship,p2);
            //clone.placeShip(battleship,p2.getPosition(),p2.isVertical());
            clone=board.clone();
            //System.out.println("clone=");
            //System.out.println(clone);
            Placement p3=player.choosePlacement(destroyer,clone);
            ships.get(player).put(destroyer,p3);
            board.placeShip(destroyer,p3.getPosition(),p3.isVertical());
            //ships.get(player).put(destroyer,p3);
            clone=board.clone();
            //System.out.println("clone=");
            //System.out.println(clone);
            //clone.placeShip(destroyer,p3.getPosition(),p3.isVertical());
            Placement p4=player.choosePlacement(submarine,clone);
            ships.get(player).put(submarine,p4);
            board.placeShip(submarine,p4.getPosition(),p4.isVertical());
            //clone.placeShip(submarine,p4.getPosition(),p4.isVertical());
            //ships.get(player).put(submarine,p4);
            clone=board.clone();
            //System.out.println("clone=");
            //System.out.println(clone);
            Placement p5=player.choosePlacement(patrol,clone);
            ships.get(player).put(patrol,p5);
            board.placeShip(patrol,p5.getPosition(),p5.isVertical());
            //ships.get(player).put(patrol,p5);
            //clone.placeShip(patrol,p5.getPosition(),p5.isVertical());
            //ships.put(player,hashMap);
        }catch(InvalidPositionException e){
            System.out.println("error");
        }catch(ShipOverlapException e){
            System.out.println("Ship overlap");
        }
        return placements;
    }

    public static Game newGame() throws PauseException{
        boolean ready=false;
        while(!ready){
            try{   
                player1=initiatePlayer("1");
                player2=initiatePlayer("2");
                ready=true;
            }catch(WrongInputException e){
                System.out.println("player"+e.getNumber()+ " was not created properly");
            }
        }
        Game g=new Game(player1,player2);

        g.getIsHuman().put(player1,human1);
        g.getIsHuman().put(player2,human2);
        return g;
    }

    public void menu(){
        boolean end=false;
        g=null;
        while(!end){
            System.out.println("---------BattleShips----------");
            System.out.println("1=play current game");
            System.out.println("2=make new game");
            System.out.println("3=load game");
            System.out.println("4=save game");
            System.out.println("5=exit");
            System.out.println("press the number for what you want to do");
            Scanner sc=new Scanner(System.in);

            if(sc.hasNextInt()){
                int response=sc.nextInt();
                if((response>0)&&(response<6)){
                    switch(response){
                        case 1:
                        if(g==null){
                            System.out.println("no game loaded");
                        }else{
                            //g.setNewGame(false);
                            g.play();
                        }

                        break;
                        case 2:try{ g=newGame();}catch(PauseException e){}
                        //g.setIsHuman(new HashMap<PlayerInterface,Boolean>());
                        break;
                        case 3:
                        try{
                        boolean loaded=false;
                        while(!loaded){
                            try{
                                System.out.println("what is the filename that you want to load");
                                String filename=sc.next();
                                if(filename.equals("pause")){
                                    throw new PauseException();
                                }
                                loadGame(filename);
                                loaded=true;

                            }catch(IOException e){
                                System.out.println("file didn't load correctly try again");
                            }
                        }
                    }catch(PauseException e){}
                        break;
                        case 4: 
                        boolean saved=false;
                        try{
                        if(g.getNewGame()){
                            System.out.println("you can't save until the ships have been placed");
                            System.out.println("you can place these in the 'play current game' section");
                        }else{
                            while(!saved){
                                try{
                                    System.out.println("please enter filepath to where you want to save");
                                    String filepath=sc.next();
                                    saveGame(filepath);
                                    saved=true;
                                }catch(IOException e){
                                    System.out.println("save didnt work please try again");
                                }
                            }}
                        }catch(NullPointerException e){
                            System.out.println("game not initiated yet");
                        }
                        case 5: end=true;
                        break;
                    }

                }else{
                    System.out.println("please pick a number that is an option");
                }
            }else{
                System.out.println("not an number try again");
            }
        }
    }

    public void setNewGame(boolean nGame){
        newGame=nGame;
    }

    public boolean getNewGame(){
        return newGame;
    }

    public PlayerInterface getP1(){
        return player1;
    }

    public  PlayerInterface getP2(){
        return player2;
    }

    public BoardInterface getB1(){
        return board1;
    }

    public BoardInterface getB2(){
        return board2;
    }

    public PlayerInterface play(){
        PlayerInterface winner=null;
        try{
            //System.out.println("Would you like to play with the GUI or terminal?");
            //System.out.println("press 1 for GUI or 2 for terminal");
            //Scanner sc=new Scanner(System.in);
            //if(sc.nextInt()==1){
            if(g.getNewGame()){
                setUp(g.getP1(),g.getB1());
                setUp(g.getP2(),g.getB2());
            }
            g.setNewGame(false);
            boolean finished=false;
            PlayerInterface current=g.getP1();
            BoardInterface currentBoard=g.getB1();
            PlayerInterface opponent=g.getP2();

            BoardInterface opponentBoard=g.getB2();
            while(!finished){

                try{
                    if(isHuman==null){
                        System.out.println("isHuman is null");
                    }
                    if(current==null){
                        System.out.println("current is null");
                    }
                    System.out.println(g.getIsHuman().get(current));
                    if(g.getIsHuman().get(current)){
                        System.out.println(opponentBoard);
                        System.out.println("-------------opponents board------------");
                        String hidden=makeHidden(opponentBoard.toString(),current);
                        System.out.print(hidden);
                    }else{
                        System.out.println("computer turn");
                    }
                    Position pos=current.chooseShot();
                    shots.get(current).add(pos);
                    opponentBoard.shoot(pos);

                    ShotStatus state=g.logShot(opponentBoard,pos,current);
                    /*switch(opponentBoard.getStatus(pos)){
                    case NONE:
                    state=ShotStatus.MISS;
                    g.getMisses().get(current).add((10-pos.getX())*10+pos.getY()-1);
                    break;
                    case HIT:
                    state=ShotStatus.HIT;
                    break;
                    case SUNK:
                    state=ShotStatus.SUNK;
                    break;
                    }*/
                    current.shotResult(pos,state);
                    opponent.opponentShot(pos);
                    if(!isHuman.get(current)){
                        System.out.println("computers go result:"+state);
                        System.out.println(showMisses(opponentBoard.toString(),current));
                    }
                }catch(InvalidPositionException e){}
                if(opponentBoard.allSunk()){
                    System.out.println(current+" has won");
                    winner=current;
                    finished=true;
                }else{
                    PlayerInterface playerTemp=current;
                    current=opponent;
                    opponent=playerTemp;
                    BoardInterface boardTemp=currentBoard;
                    currentBoard=opponentBoard;
                    opponentBoard=boardTemp;
                }

            }
            //}else{
            //  BattleShipsGUI gui=BattleShipsGUI.getGUI();
            //  player1=gui.getPlayer1();
            //  player2=gui.getPlayer2();
            //}
        }catch(PauseException e){}
        return winner;
    }

    public ShotStatus logShot(BoardInterface opponentBoard,Position pos, PlayerInterface current){
        ShotStatus state=null;
        try{
            switch(opponentBoard.getStatus(pos)){
                case NONE:
                state=ShotStatus.MISS;
                //changed x and y here
                g.getMisses().get(current).add((10-pos.getY())*10+pos.getX()-1);
                break;
                case HIT:
                state=ShotStatus.HIT;
                break;
                case SUNK:
                state=ShotStatus.SUNK;
                break;
            }
        }catch(InvalidPositionException e){}
        return state;
    }

    public HashMap<PlayerInterface,ArrayList<Position>> getShots(){
        return shots;
    }

    public String makeHidden(String originalBoard,PlayerInterface player){
        System.out.println("original Board in makeHidden method");
        System.out.println(originalBoard);
        String newStr="";
        originalBoard=showMisses(originalBoard,player);
        //originalBoard = originalBoard.substring(originalBoard.indexOf("\n")+1);
        String words[]=originalBoard.split("(?<=,)");
        System.out.println("there are "+words.length+" words");
        for(int i=0;i<words.length;i++){
            //System.out.println(str);
            if(words[i].equals("I,")){
                newStr+="-,";
            }else if(words[i].equals("\nI,")){
                newStr+="\n-,";

            }else{
                newStr+=(words[i]);
            }

        }
        newStr+="\n";
        System.out.println("Board after");
        System.out.println(newStr);
        return newStr;
    }

    public ArrayList<Position> getShots(PlayerInterface player){
        return shots.get(player);
    }

    public String showMisses(String originalBoard,PlayerInterface player){
        String newStr="";
        System.out.println("original Board in show misses method");
        System.out.println(originalBoard);
        String words[]=originalBoard.split("(?<=,)");
        for(int i=0;i<words.length;i++){
            if(g.getMisses().get(player).contains(i)){
                if(words[i].equals("\n-,")){
                    newStr+="\nM,";
                }else{
                    newStr+="M,";
                }
            }else{
                newStr+=words[i];
            }
        }
        System.out.println("board after");
        System.out.println(newStr);
        return newStr;
    }

    /*public HashMap<ShipInterface,Placement> p1Ships(){
    return h1;
    }

    public HashMap<ShipInterface,Placement> p2Ships(){
    return h2;
    }
     */
    public void saveGame(String filename) throws IOException{
        FileWriter writer=new FileWriter(filename);

        for(int i=1;i<3;i++){
            writer.write(""+i+" ");
            PlayerInterface player=null;
            HashMap<ShipInterface,Placement> h=null;
            if(i==1){
                player=player1;

            }else{
                player=player2;

            }
            h=getShips(player);
            writer.write(player.toString()+" " );
            if(isHuman.get(player)){
                writer.write("U ");
            }else{
                writer.write("C ");
            }
            //HashMap<ShipInterface,Placement> saveInfo1=((Board)board1).saveInfo();
            //System.out.println(((Board)board1).saveInfo());
            //System.out.println(ships.size());
            for(ShipInterface ship:h.keySet()){
                writer.write(""+ship.getSize()+" ");
                writer.write(h.get(ship).getPosition().getX()+" ");
                writer.write(h.get(ship).getPosition().getY()+" ");
                writer.write(""+h.get(ship).isVertical()+" ");

            }
            writer.write(System.lineSeparator());
            for(Position pos:g.getShots(player)){
                writer.write(pos.getX()+" "+pos.getY()+" ");
            }
            writer.write(System.lineSeparator());
        }

        writer.close();
    }

    public void loadGame(String filename) throws IOException{
        File file= new File(filename);
        Scanner sc=new Scanner(file);
        String pNumber=sc.next();
        PlayerInterface player;
        BoardInterface board;
        BoardInterface b1=null;
        BoardInterface b2=null;
        HashMap<Integer,ArrayList<Position>> shotsFromFile=new HashMap<>();
        HashMap<ShipInterface,Placement> ships1=new HashMap<>();
        HashMap<ShipInterface,Placement> ships2=new HashMap<>();
        for(int j=1;j<3;j++){
            ArrayList<Position> shotsForPlayer=new ArrayList<>();
            try{
                System.out.println("pNumber equals="+pNumber);
                HashMap<ShipInterface,Placement> shipsFromFile=null;
                if(pNumber.equals(""+j)){
                    if(j==1){
                        shipsFromFile=ships1;
                        player=playerFromFile(1,player1,sc);
                        player1=player;
                        b1=new Board();
                        board=b1;
                    }else{
                        shipsFromFile=ships2;
                        player=playerFromFile(2,player2,sc);
                        player2=player;
                        b2=new Board();
                        board=b2;
                    }
                }else{
                    throw new WrongFileFormatException("player number");
                }

                for(int i=0;i<5;i++){
                    int size;
                    int x;
                    int y;
                    if(sc.hasNextInt()){
                        size=sc.nextInt();
                    }else{
                        throw new WrongFileFormatException("size on ship "+i);
                    }
                    if(sc.hasNextInt()){
                        x=sc.nextInt();
                    }else{
                        throw new WrongFileFormatException("x on ship "+i);
                    }
                    if(sc.hasNextInt()){
                        y=sc.nextInt();
                    }else{
                        throw new WrongFileFormatException("y on ship "+i);
                    }
                    boolean isVertical;
                    if(sc.hasNextBoolean()){
                        isVertical=sc.nextBoolean();
                    }else{
                        throw new WrongFileFormatException("is Vertical on ship "+i);
                    }
                    try{
                        ShipInterface ship=new Ship(size);
                        Position pos=new Position(x,y);
                        Placement pl=new Placement(pos,isVertical);
                        board.placeShip(ship,pos,isVertical);
                        shipsFromFile.put(ship,pl);
                    }catch( InvalidPositionException e){

                    }catch(ShipOverlapException e){

                    }

                }
                Scanner s;
                String line=sc.nextLine();
                if(sc.hasNextLine()){

                    line=sc.nextLine();
                    //System.out.println("line="+line);
                    s=new Scanner(line);
                }else{
                    throw new WrongFileFormatException("next line");
                }

                while(s.hasNext()){
                    int x=s.nextInt();
                    //System.out.println("x of shot="+x);
                    int y=s.nextInt();
                    //System.out.println("y of shot="+y);

                    try{
                        Position pos=new Position(x,y);
                        shotsForPlayer.add(pos);
                    }catch(InvalidPositionException e){}
                }
            }catch(WrongFileFormatException e){}
            if(sc.hasNext()){
                pNumber=sc.next();
            }
            shotsFromFile.put(j,shotsForPlayer);
            //System.out.println("next pNumber (should be two)="+pNumber);
        }
        g=new Game(player1,player2);
        g.setNewGame(false);
        try{
            g.setBoard1(b1);
            g.setBoard2(b2);
            g.setP1(player1);
            g.setP2(player2);
            for(Position pos:shotsFromFile.get(1)){
                try{
                    g.getB2().shoot(pos);
                    g.getShots().get(player1).add(pos);
                    g.logShot(g.getB2(),pos,g.getP1());
                }catch(InvalidPositionException e){}
            }
            for(Position pos:shotsFromFile.get(2)){
                try{
                    g.getB1().shoot(pos);
                    g.getShots().get(player2).add(pos);
                    g.logShot(g.getB1(),pos,g.getP2());
                }catch(InvalidPositionException e){}
            }
            System.out.println("player 1 isHuman:"+human1);
            System.out.println("player 2 isHuman:"+human2);
            g.getIsHuman().put(player1,human1);
            g.getIsHuman().put(player2,human2);
            HashMap<ShipInterface,Placement> h1=g.getShips(g.getP1());
            HashMap<ShipInterface,Placement> h2=g.getShips(g.getP2());
            for(ShipInterface s:ships1.keySet()){
                h1.put(s,ships1.get(s));
            }
            for(ShipInterface s:ships2.keySet()){
                h2.put(s,ships2.get(s));
            }
        }catch(NullPointerException e){
            System.out.println(e.getMessage()+" was null");
            System.out.println("boards/players not initialised properly");
        }

    }

    public void setP1(PlayerInterface p1) throws NullPointerException{
        if(p1==null){throw new NullPointerException("player 1");}
        this.player1=p1;
    }

    public void setP2(PlayerInterface p2)throws NullPointerException{
        if(p2==null){throw new NullPointerException("player 2");}
        this.player2=p2;
    }

    public void setBoard1(BoardInterface b1) throws NullPointerException{
        if(b1==null){
            throw new NullPointerException("board 1");
        }
        this.board1=b1;
    }

    public void setBoard2(BoardInterface b2){
        if(b2==null){throw new NullPointerException("board 2");}
        this.board2=b2;
    }

    public PlayerInterface playerFromFile(int i,PlayerInterface player, Scanner sc) throws WrongFileFormatException{
        String name=sc.next();
        //HashMap<PlayerInterface,Boolean> isH=g.getIsHuman();
        String type=sc.next();
        if(type.equals("U")){
            player=new HumanConsolePlayer(name);
            if(i==1){
                human1=true;
                //System.out.println("inner if of playerFromFile called");
            }else{
                human2=true;
                //System.out.println("inner if of playerFromFile called");
            }
        }else if(type.equals("C")){
            if(i==1){
                human1=false;
                //System.out.println("inner if of playerFromFile called");
            }else{
                human2=false;
                //System.out.println("inner if of playerFromFile called");
            }
            player=new ComputerPlayer();
            //isHuman.put(player,false);
        }else{
            throw new WrongFileFormatException("type");
        }
        if(player==null){
            System.out.println("player is null");
        }
        return player;
    }
}
