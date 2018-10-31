import java.util.Scanner;
import java.lang.NumberFormatException;
import java.util.HashMap;
import java.util.ArrayList;
public class HumanConsolePlayer  implements PlayerInterface
{
    private String name;
    private HashMap<Position,ShotStatus> results;
    private HashMap<Integer,ShipStatus> myShips;
    private ShipStatus[][] myboard;
    private ShotStatus[][] oppBoard;
    
    public HumanConsolePlayer(String name){
        this.name=name;
        myShips=new HashMap<>();
        results=new HashMap<>();
        myboard=new ShipStatus[10][10];
        oppBoard=new ShotStatus[10][10];
        for(int i=11;i<=110;i++){
            myShips.put(i,ShipStatus.INTACT);
        }
    }
    public Placement getPlacement(ShipInterface ship, BoardInterface board) throws PauseException,InvalidPositionException , ShipOverlapException{
         Scanner sc=new Scanner(System.in);
        System.out.println(board);
        System.out.println("Where will you put your size "+ship.getSize()+" ship");
        System.out.println("x=");
        int x=tryStringToInt(sc.next());
        System.out.println("y=");
        int y=tryStringToInt(sc.next());
        System.out.println("would you like the ship to be vertical");
        String verticalResponse=sc.next();
        boolean isVertical=false;
        if(verticalResponse.equals("pause")){
            throw new PauseException();
        }else{
            if(verticalResponse.equals("yes")){
                isVertical=true;
            }

        } 
        Position pos=new Position(x,y);
                board.placeShip(ship,pos,isVertical);
              return new Placement(pos,isVertical);  
    }
    public Placement choosePlacement(ShipInterface ship, BoardInterface board) throws PauseException{
        boolean done=false;
        Placement placement=null;
        while(!done){
            try{
                placement=getPlacement(ship, board);
                done=true;
            }catch(InvalidPositionException e){
                System.out.println("invalid position try again");
                
            }catch(ShipOverlapException e){
                System.out.println("ship ovelaps another ship try again");
            }
            
        }
        for(int i=0;i<ship.getSize();i++){
            Integer pos=0;
            if(placement.isVertical()){
                 pos=placement.getPosition().getX()*10+placement.getPosition().getY()+i;
                
            }else{
                 pos=(placement.getPosition().getX()+i)*20+placement.getPosition().getY();
            }
            myShips.put(pos,ShipStatus.INTACT);
        
        }
        
        
        return placement;
    }

    public int tryStringToInt(String str)throws PauseException{
        boolean done=false;
        int number=0;
        while(!done){
            if(str.equals("pause")){
                throw new PauseException();
            }else{
                try{
                    number=Integer.parseInt(str);
                    done=true;
                }catch(NumberFormatException e){
                    System.out.println("not a number try again");
                    Scanner sc=new Scanner(System.in);
                    str=sc.next();
                }
            }
        }
        return number;
    }
    
    public Position chooseShot() throws PauseException{
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter where you want to shoot");
        System.out.println("x=");
        int x=tryStringToInt(sc.next());
        System.out.println("y=");
        int y=tryStringToInt(sc.next());
        Position pos=null;
        boolean done=false;
        while(!done){
            try{
                pos=new Position(x,y);
                done=true;
            }catch(InvalidPositionException e){
                System.out.println("position out of range try again");
                System.out.println("x=");
                x=tryStringToInt(sc.next());
                System.out.println("y=");
                y=tryStringToInt(sc.next());
            }
        }
        return pos;
    }

    public void shotResult(Position position, ShotStatus status){
        results.put(position,status);
        if(status==ShotStatus.SUNK){
            System.out.println("SUNK A SHIP");
        }
        /*oppBoard[position.getX()-1][position.getY()-1]=status;
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(oppBoard[i][j]==null){
                    System.out.print("-");
                }else{
                    System.out.print(oppBoard[i][j]);
                }
            }
            System.out.println("");
        }
        
        */
    }

    public void opponentShot(Position position){
        int shoot=position.getX()*10+position.getY();
        myShips.put(shoot,ShipStatus.HIT);
    }

    public String toString(){
        return name;
    }
    
}
