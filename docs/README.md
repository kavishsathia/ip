# Patrick User Guide

Patrick is a task management chatbot with a Patrick Star personality. It helps you keep track of todos, deadlines, and events through a chat-based interface.

## Quick Start

1. Ensure you have Java 17 installed.
2. Download the latest `.jar` file from [here](https://github.com/kavishsathia/ip/releases).
3. Double-click the file to launch the application.
4. Type commands in the text field and press **Send** or **Enter**.

## Features

> **Note:** All dates must be in `yyyy-mm-dd` format (e.g., `2024-03-01`).

### Adding a todo: `todo`

Adds a task with no date attached.

Format: `todo DESCRIPTION`

Example: `todo read book`

```
Oooh! I remembered something! I added "[T][ ] read book"
```

### Adding a deadline: `deadline`

Adds a task with a due date.

Format: `deadline DESCRIPTION /by DATE`

Example: `deadline submit essay /by 2024-03-01`

```
Oooh! I remembered something! I added "[D][ ] submit essay (by: Mar 01 2024)"
```

### Adding an event: `event`

Adds a task with a start and end date.

Format: `event DESCRIPTION /from DATE /to DATE`

Example: `event orientation camp /from 2024-03-01 /to 2024-03-03`

```
Oooh! I remembered something! I added "[E][ ] orientation camp (from: Mar 01 2024 to: Mar 03 2024)"
```

### Listing all tasks: `list`

Displays all tasks in the current list.

Format: `list`

```
Uhhh... let me think... oh yeah! Here's your stuff:
1. [T][ ] read book
2. [D][ ] submit essay (by: Mar 01 2024)
3. [E][ ] orientation camp (from: Mar 01 2024 to: Mar 03 2024)
```

### Sorting tasks: `list /asc` or `list /desc`

Displays all tasks sorted by name or time in ascending or descending order. When sorting by time, todos (which have no date) appear at the end.

Format: `list /asc FIELD` or `list /desc FIELD`

- `FIELD` can be `name` or `time`

Examples:
- `list /asc time` — earliest deadlines and events first
- `list /desc name` — tasks sorted Z to A by description

### Marking a task as done: `mark`

Marks the specified task as completed.

Format: `mark INDEX`

Example: `mark 1`

```
I did it! I marked this thing as done:
  [T][X] read book
```

### Unmarking a task: `unmark`

Marks the specified task as not done.

Format: `unmark INDEX`

Example: `unmark 1`

```
Wait... it's NOT done? Okay, unmarked:
  [T][ ] read book
```

### Deleting a task: `delete`

Removes the specified task from the list.

Format: `delete INDEX`

Example: `delete 2`

```
It's gone! I made it disappear:
  [D][ ] submit essay (by: Mar 01 2024)
```

### Finding tasks: `find`

Searches for tasks containing the given keyword.

Format: `find KEYWORD`

Example: `find book`

```
I found some stuff! Look look look:
1. [T][ ] read book
```

### Exiting the application: `bye`

Closes the application.

Format: `bye`

## Command Summary

| Action | Format |
|---|---|
| Add todo | `todo DESCRIPTION` |
| Add deadline | `deadline DESCRIPTION /by DATE` |
| Add event | `event DESCRIPTION /from DATE /to DATE` |
| List tasks | `list` |
| Sort tasks | `list /asc name`, `list /desc time`, etc. |
| Mark task | `mark INDEX` |
| Unmark task | `unmark INDEX` |
| Delete task | `delete INDEX` |
| Find tasks | `find KEYWORD` |
| Exit | `bye` |
