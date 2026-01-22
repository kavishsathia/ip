public class Storage {
    private final Task[] data;
    private int next;

    public Storage(int length) {
        this.data = new Task[100];
        this.next = 0;
    }

    public void store(String s) {
        this.data[this.next] = new Task(s);
        this.next++;
    }

    public Task get(int index) {
        return this.data[index];
    }

    public String list() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.next; i++) {
            s.append(i + 1).append(". ").append(this.data[i]).append("\n");
        }
        return s.toString();
    }
}