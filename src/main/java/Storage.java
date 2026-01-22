
import java.util.ArrayList;


public class Storage {
    private final ArrayList<Task> data;

    public Storage() {
        this.data = new ArrayList<>();
    }

    public void store(Task s) {
        this.data.add(s);
    }

    public Task get(int index) throws StorageRetrievalException {
        if (index < 0 || index >= this.data.size()) {
            throw new StorageRetrievalException("Invalid index: " + (index + 1));
        }
        return this.data.get(index);
    }

    public Task pop(int index) throws StorageRetrievalException {
        if (index < 0 || index >= this.data.size()) {
            throw new StorageRetrievalException("Invalid index: " + (index + 1));
        }
        Task removed = this.data.remove(index);
        return removed;
    }

    public String list() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.data.size(); i++) {
            s.append(i + 1).append(". ").append(this.data.get(i)).append("\n");
        }
        return s.toString();
    }
}