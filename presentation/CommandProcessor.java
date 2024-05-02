package presentation;

import java.util.ArrayList;
import java.util.List;

public class CommandProcessor {
    private List<Command> commandHistory;

    public CommandProcessor() {
        commandHistory = new ArrayList<>();
    }

    public void executeCommand(Command command) {
        command.execute();
        commandHistory.add(command);
    }

    public void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.remove(commandHistory.size() - 1);
            lastCommand.undo();
        }
    }
}
