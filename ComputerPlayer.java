import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.HashSet;
public class ComputerPlayer implements PlayerInterface
{
    private Position last;
    private Position first;
    private HashMap<Position,ShotStatus> results;
    // private boolean isGUI;
    public ComputerPlayer(){
        results=new HashMap<>();
        last=null;
        //isGUI=false;
    }

    public Placement choosePlacement(ShipInterface ship, BoardInterface board) throws PauseException{
        boolean works=false;
        Random rand=new Random();
        boolean vertical=true;
        Position pos=null;

        while(!works){
            BoardInterface bClone=board.clone();
            try{

                pos=randomPosition();
                vertical=rand.nextBoolean();
                bClone.placeShip(ship,pos,vertical);
                works=true;
                //board.placeShip(ship,pos,vertical);
                //System.out.println(board);
                //System.out.println("placed "+ship.getSize()+" sized ship");
            }catch(ShipOverlapException e){

            }catch(InvalidPositionException e){

            }

        }
        return new Placement(pos,vertical);
    }

    public Position randomPosition() {
        Position pos=null;
        try{Random rand=new Random();
            int x=rand.nextInt(10)+1;
            int y=rand.nextInt(10)+1;
            pos=new Position(x,y);
        }catch(InvalidPositionException e){

        }
        return pos;
    }

    public Position chooseShot() throws PauseException{
        Position pos=null;  
        boolean original=false;
        if(last==null){

            while(!original){
                pos=randomPosition();
                original=notInResults(pos);
            }
        }else{
            boolean works=false;
            HashSet<Integer> tries=new HashSet<>();
            long startTime=System.nanoTime();
            while(!original){
                works=false;
                /*long endTime=System.nanoTime();
                long duration=endTime-startTime;
                if(duration==2000000000){
                    System.out.println("tries size="+tries.size());
                    System.out.println("last="+last);
                    System.out.println("first="+first);
                }
                */
                if((tries.size()==3)&&(first!=last)){
                    last=first;
                }
                while(!works){

                    try{
                        pos=getRandomNeighbour();
                        original=notInResults(pos);
                        works=true;
                    }catch(InvalidPositionException e){}
                }
                tries.add(pos.getY()*100+pos.getY());
            }
        }
        System.out.println("computer shoots at "+pos);
        return pos;
    }

    public Position getRandomNeighbour() throws InvalidPositionException{
        Position pos=null;
        Random rand=new Random();
        int n=rand.nextInt(4);

        switch(n){
            case 0: pos=new Position(last.getX()-1,last.getY());
            System.out.println("try left");
            break;
            case 1: pos=new Position(last.getX(),last.getY()-1);
            System.out.println("try down");
            break;
            case 2:pos=new Position(last.getX()+1,last.getY());
            System.out.println("try right");
            break;
            case 3:pos=new Position(last.getX(),last.getY()+1);
            System.out.println("try up");
            break;
        }
        return pos;
    }

    public boolean notInResults(Position pos){
        boolean original=true;
        for(Position p:results.keySet()){
            if(p.getX()==pos.getX()&&p.getY()==pos.getY()){
                original=false;
            }
        }
        return original;
    }

    public void shotResult(Position position, ShotStatus status){
        switch(status){
            case MISS: results.put(position,status);
            break;
            case HIT: 
            if(first==null){
                first=position;
            }
            results.put(position,status);
            last=position;
            break;
            case SUNK: results.put(position,status);
            first=null;
            last=null;
            break;

        }
    }

    public void opponentShot(Position position){}

    public String toString(){
        return "Computer";
    }
}