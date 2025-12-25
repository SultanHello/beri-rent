package sultan.org.itemservice.item.exceptions;

public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException(String message){
        super(message);
    }
}
