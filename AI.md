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
