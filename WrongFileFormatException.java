

public class WrongFileFormatException extends Exception
{
    public WrongFileFormatException(String str){
        System.out.println("problem reading in "+str+" section");
    }
}
