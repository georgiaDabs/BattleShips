import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane ;
public class HumanGUIPlayer implements PlayerInterface
{
    private BattleShipsGUI gui;
    private int player;
    public HumanGUIPlayer(BattleShipsGUI gui, int player){
        this.gui=gui;
        this.player=player;
    }

    public Placement choosePlacement(ShipInterface ship, BoardInterface board) throws PauseException{

        gui.changeLabel(player,"pick position for front of "+ship.getSize()+" sized ship");
        try{
            TimeUnit.SECONDS.sleep(10);
        }catch(InterruptedException e){}
        boolean isVerticle=false;
        int result = JOptionPane.showConfirmDialog(null, "Do you want the ship to be verticle?",null, JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.YES_OPTION) {
            isVerticle=true;
        } 
        Position pos=gui.justClicked();
        Placement placement=new Placement(pos,isVerticle);
        return placement;
    }

    public Position chooseShot() throws PauseException{
        gui.changeLabel(player,"pick where you want to shoot on opponents board");
        try{
            TimeUnit.SECONDS.sleep(10);
        }catch(InterruptedException e){}
        Position pos=gui.justClicked();
        return pos;
    }

    public void shotResult(Position position, ShotStatus status){
        String fx="";
        switch(status){
            case HIT: fx="Color.RED";
            break;
            case MISS:fx="Color.GRAY";
            break;
            case SUNK: fx="Color.BLACK";
            break;
        }
        if(player==1){
            gui.changeButton(2,position,fx);
        }else{
            gui.changeButton(1,position,fx);
        }
    }

    public void opponentShot(Position position){
        
    }

    public String toString(){
        String str="";
        return str;
    }
}
