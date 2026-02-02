package patrick;

/**
 * Represents an exception thrown when tasks cannot be retrieved from storage.
 */
public class StorageRetrievalException extends Exception {
    public StorageRetrievalException(String message) {
        super(message);
    }
}
