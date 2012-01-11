package esir.dom11.nsoc.model;

import java.util.UUID;

public class Category {
    
    /*
     * Attributes
     */
    
    private UUID _id;       // dao key
    private String _name;
    private int _lock;
    
    /*
     * Constructors
     */
    
    public Category() {}
    
    public Category(String name, int lock) {
        _id = UUID.randomUUID();
        _name = name;
        _lock = lock;
    }

    public Category(UUID id, String name, int lock) {
        _id = id;
        _name = name;
        _lock = lock;
    }

    /*
     * Getters / Setters
     */

    public UUID getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public int getLock() {
        return _lock;
    }

    public void setLock(int lock) {
        _lock = lock;
    }

    /*
    * Overrides
    */

    @Override
    public String toString() {
        return "\n* * * Category " + getId() + " * * *"
                + "\nName: " + getName()
                + "\nLock: " + getLock() + "\n";
    }

    /*
     * Methods
     */
}
