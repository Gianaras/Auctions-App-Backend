package gr.uoa.di.tedi.projectbackend.handling;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Long id) {
        super("Could not find Item " + id);
    }
}
