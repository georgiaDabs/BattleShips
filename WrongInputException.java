public class WrongInputException extends Exception{
    String number=null;
    public WrongInputException(String number){
        this.number=number;
    }
    public String getNumber(){
        return number;
    }
    
}