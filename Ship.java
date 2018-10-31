import java.util.ArrayList;
public class Ship implements ShipInterface{
    private int size;
    private ArrayList<ShipStatus> squares;
    public Ship(int size){
        this.size=size;
        squares=new ArrayList<>();
        for(int i=0;i<size;i++){
            squares.add(ShipStatus.INTACT);
        }
    }
    public int getSize(){
        return size;
    
    }
    public boolean  isSunk(){
        boolean sunk=true;
    for(int i=0;i<size;i++){
        if(squares.get(i)==ShipStatus.INTACT){
            sunk=false;
        }
    }
    return sunk;
    }
    public void shoot(int offset){
        squares.set(offset,ShipStatus.HIT);
        if(isSunk()){
            for(int i=0;i<squares.size();i++){
                squares.set(i,ShipStatus.SUNK);
            }
        }
    }
    
    public ShipStatus getStatus(int offset) throws InvalidPositionException{
        if(offset>size){
            throw new InvalidPositionException();
        }else{
            
            return squares.get(offset);
        }
    }
    
}