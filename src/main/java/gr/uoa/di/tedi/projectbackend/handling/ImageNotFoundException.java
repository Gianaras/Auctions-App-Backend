package gr.uoa.di.tedi.projectbackend.handling;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(Integer id) {
        super("Could not find Image " + id);
    }
}
