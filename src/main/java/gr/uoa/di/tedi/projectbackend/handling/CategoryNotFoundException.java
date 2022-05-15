package gr.uoa.di.tedi.projectbackend.handling;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String id){super("Could not find Category: "+id);}
}
