1. Generated ASCII art logo for "Patrick" to replace the original "Duke" logo in Patrick.java
2. Updated EXPECTED.TXT to match the new Patrick ASCII art logo
3. Updated README.md with the new Patrick ASCII art logo
4. Wrote specific error messages for invalid commands (mark, unmark, event, deadline)
5. Refactored Patrick.java to use the Message enum instead of hardcoded strings
6. Extracted Ui, TaskList, Parser (with regex), and Command class hierarchy from Patrick.java to follow single-responsibility principle
7. Organized all classes into packages (patrick, patrick.task, patrick.command) and updated test script accordingly
8. Helped in GUI design, and TaskList design (just design)
9. Refactored Parser.java to eliminate variable recycling — replaced single reused `matcher` variable with individually scoped variables (`markMatcher`, `unmarkMatcher`, etc.)
10. Simplified complicated conditional expression in Parser.java — split combined mark/unmark error handling with ternary into separate, clear `if` blocks
11. Added explanatory comments to silent catch blocks in Storage.java to clarify why exceptions are swallowed
12. Extracted `parseTaskFromLine` method in Storage.java to eliminate data flow anomaly (`Task task = null` pre-assignment)
13. Extracted `formatNumberedList` helper in Tui.java to eliminate duplicate task-listing loops in `showTaskList` and `showFindResults`
14. Extracted `formatNumberedList` helper in Gui.java to eliminate the same duplicate task-listing loops
15. Eliminated duplicated mark/unmark message strings in Gui.java — checkbox handler now calls `showTaskMarked`/`showTaskUnmarked` instead of duplicating their logic
16. Replaced magic number `6` in Gui.java `getTaskDescription` with self-documenting `"[T][ ] ".length()`
17. Refactored TaskList.find() to use Java streams (filter + collect) instead of a for-loop
18. Gave Patrick a Patrick Star (SpongeBob) personality — rewrote all Message enum phrases and Gui response strings in Patrick Star's voice, updated GUI color theme to pink/sandy Bikini Bottom palette, renamed task panel header to "Patrick's To-Do Rock"
