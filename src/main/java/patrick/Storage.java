package patrick;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import patrick.task.Deadline;
import patrick.task.Event;
import patrick.task.Task;
import patrick.task.TaskList;
import patrick.task.Todo;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            return tasks;
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            return tasks;
        }

        for (String line : lines) {
            String[] parts = line.split(" \\| ");
            if (parts.length < 3) {
                continue;
            }
            String type = parts[0].trim();
            boolean isDone = parts[1].trim().equals("1");
            String description = parts[2].trim();

            Task task = null;
            switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length < 4) {
                    continue;
                }
                try {
                    task = Deadline.parse(description, parts[3].trim());
                } catch (Exception e) {
                    continue;
                }
                break;
            case "E":
                if (parts.length < 4) {
                    continue;
                }
                task = new Event(description, parts[3].trim());
                break;
            default:
                continue;
            }

            if (isDone) {
                task.markAsDone();
            }
            tasks.add(task);
        }

        return tasks;
    }

    public void writeFile(TaskList tasks) {
        Path path = Paths.get(filePath);

        try {
            Path parent = path.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            FileWriter writer = new FileWriter(path.toFile());
            for (Task task : tasks.getTasks()) {
                writer.write(task.toFileString() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}
