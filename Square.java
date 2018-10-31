
public class Square
{
    private boolean empty;
    private ShipInterface ship;
    private int offset;
    public Square(){
        empty=true;
    }
    public Square(int offset, ShipInterface ship){
        empty=false;
        this.offset=offset;
        this.ship=ship;
    }
    public boolean isNone(){
        return empty;
    }
    public void setShip(ShipInterface ship){
        this.ship=ship;
        empty=false;
    }
    public ShipStatus getStatus(){
        ShipStatus status=ShipStatus.NONE;
        try{
            status= ship.getStatus(offset);
        }catch(InvalidPositionException e){
            
        }catch(NullPointerException e){
        
        }
        return status;
    }
    public ShipInterface getShip(){
        return ship;
    }
    public int getOffset(){
        return offset;
    }
}
