import java.util.*;
import javafx.util.Pair;
public class Board implements BoardInterface{
    private HashMap<Integer,Square> board;
    private HashMap<ShipInterface,Boolean> ships;
    private HashMap<ShipInterface,Placement> saveInfo;
    private Square emptySquare=new Square();
    public Board(){
        ships=new HashMap<>();
        board=new HashMap<>();
        saveInfo=new HashMap<>();
            for(int i=1;i<=10;i++){
                for(int j=1;j<=10;j++){

                    board.put(i*10+j,emptySquare);
                }
            }
        
    }
    

    public void placeShip(ShipInterface ship, Position position, boolean isVertical) throws InvalidPositionException, ShipOverlapException{
        ships.put(ship,isVertical);
        if(isVertical){

            for(int i=0;i<ship.getSize();i++){
                //System.out.println("y="+(position.getY()+i));
                if(position.getY()+i>10){
                    throw new InvalidPositionException();
                }else{
                    if(board.get((position.getX())*10+position.getY()+i)==emptySquare){
                    Square s=new Square(i,ship);
                    board.put((position.getX())*10+position.getY()+i,s);
                }else{
                    throw new ShipOverlapException("Ship Overlaps another ship at");
                }
                }
            }
        }else{
            for(int i=0;i<ship.getSize();i++){
               // System.out.println("x="+(position.getX()+i));
                if((position.getX()+i)>10){
                    throw new InvalidPositionException();
                }else{
                    if(board.get((position.getX()+i)*10+position.getY())==emptySquare){
                    Square s=new Square(i,ship);
                    board.put((position.getX()+i)*10+position.getY(),s);
                }else{
                    throw new ShipOverlapException("Ship Overlaps another ship");
                }
                }
            }
        }
        ships.put(ship,isVertical);
        //System.out.println("ship="+ship);
        //System.out.println("position="+position);
        //System.out.println("isVertical="+isVertical);
        Placement pl=new Placement(position,isVertical);
        saveInfo.put(ship,pl);
        //System.out.println("save info empty="+saveInfo.isEmpty());
        //System.out.println("board after place ship method");
        //System.out.println(toString());
    }

    public void shoot(Position position) throws InvalidPositionException{
        if(!board.get(position.getX()*10+position.getY()).isNone()){
        
        
        ShipInterface ship=board.get(position.getX()*10+position.getY()).getShip();
        System.out.println("Hashcode of shoot:"+(position.getX()*10+position.getY()));
        System.out.println(position);
        ship.shoot(board.get(position.getX()*10+position.getY()).getOffset());
    }else{System.out.println("MISS");}
}

    public ShipStatus getStatus(Position position) throws InvalidPositionException{
        
        return board.get(position.getX()*10+position.getY()).getStatus();
    }

    public Integer getHashCode(Position pos){
        return pos.getX()*10+pos.getY();
    }

    public boolean allSunk(){
        boolean sunk=true;
        for(ShipInterface ship:ships.keySet()){
            if(!ship.isSunk()){
                sunk=false;
            }
        }
        return sunk;
    }

    public String toString(){
        String str="";
        //str+="---------BATTLESHIPS------------------";
        //str+="--------------------------------------";
        try{
            for(int i=10;i>0;i--){
                str+="\n";
                for(int j=1;j<=10;j++){
                    Position p=new Position(j,i);
                    str+=getNicerStatus(p)+",";
                }

            }
        }catch(InvalidPositionException e){
            System.out.println("invalid position");
        }
        return str;
    }
    public String getNicerStatus(Position p) throws InvalidPositionException{
        ShipStatus s=getStatus(p);
        String str="";
        switch(s){
        case NONE:str="-";
                  break;
        case INTACT:str="I";
        break;
        case HIT: str="H";
        break;
        case SUNK: str="S";
        break;
        }
        return str;
    }
    
    
    public BoardInterface clone(){
        BoardInterface clone=new Board();
        ArrayList<ShipInterface> newShips=new ArrayList<>();
        //System.out.println("-----------Clone Method-------------------");
        //System.out.println(board);
        //System.out.println("clone");
        
        for(Integer pos:board.keySet()){
            try{
            if(!board.get(pos).isNone()&&(board.get(pos).getOffset()==0)){
          //      System.out.println("inner run");
                Ship ship=new Ship(board.get(pos).getShip().getSize());
          //      System.out.println("hash code:"+pos);
                int y=(pos%10);
                if(y==0){
                    y=10;
                    
                }
                int x=((pos-y)/10);
                
                
          //      System.out.println("x="+x+",y="+y);
                Position p=new Position(x,y);
          //      System.out.println(ship);
          //      System.out.println(p);
          //      System.out.println(ships.get(board.get(pos).getShip()));
                clone.placeShip(ship,p,ships.get(board.get(pos).getShip()));
            }}catch(InvalidPositionException e){
                System.out.println("invalid pos in clone method");
            }
            catch(ShipOverlapException e){}
        }
       //System.out.println(clone);
        return clone;
    }
    /*public HashMap<ShipInterface, Placement> saveInfo(){
        System.out.println("ships is empty="+ships.isEmpty());
        System.out.println("save info empty="+saveInfo.isEmpty());
        return saveInfo;
    }*/
}