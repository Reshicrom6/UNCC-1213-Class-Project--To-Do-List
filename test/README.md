# Project Tests

## How to run tests

1. Download JUnit 5 (Jupiter) and place the JARs in your `lib/` folder:
   - [junit-jupiter-api-5.10.2.jar](https://search.maven.org/artifact/org.junit.jupiter/junit-jupiter-api/5.10.2/jar)
   - [junit-jupiter-engine-5.10.2.jar](https://search.maven.org/artifact/org.junit.jupiter/junit-jupiter-engine/5.10.2/jar)
   - [junit-platform-console-standalone-1.10.2.jar](https://search.maven.org/artifact/org.junit.platform/junit-platform-console-standalone/1.10.2/jar)

2. Compile tests (from project root):
   ```powershell
   javac -cp ".;src;lib/*" -d test-classes test/*.java
   ```

3. Run all tests:
   ```powershell
   java -jar lib/junit-platform-console-standalone-1.10.2.jar --class-path "test-classes;src;lib/*" --scan-class-path
   ```

- You can also use VS Code's Java Test Runner extension for a GUI test experience.
- Tests cover ToDoList, Task, Date, Time, DeadLine, and TaskDAO (DB integration).
