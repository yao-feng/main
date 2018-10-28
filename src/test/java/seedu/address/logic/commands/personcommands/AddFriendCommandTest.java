package seedu.address.logic.commands.personcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FORTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import seedu.address.logic.commands.RedoCommand;
//import seedu.address.logic.commands.UndoCommand;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests with the Model and unit tests for {@code AddFriendCommand}.
 *
 * @author agendazhang
 */
public class AddFriendCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeValidIndexUnfilteredListSuccess() throws CommandException {
        Person person1 = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        Person person2 = model.getFilteredPersonList().get(INDEX_THIRD.getZeroBased());
        AddFriendCommand addFriendCommand = new AddFriendCommand(Index.fromZeroBased(INDEX_SECOND.getZeroBased(),
                INDEX_THIRD.getZeroBased()));
        String expectedMessage = String.format(AddFriendCommand.MESSAGE_ADD_FRIEND_SUCCESS,
                person1.getName(), person2.getName());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Person person1Copy = new Person(person1);
        Person person2Copy = new Person(person2);
        AddFriendCommand.addFriendEachOther(person1Copy, person2Copy);

        expectedModel.updatePerson(person1, person1Copy);
        expectedModel.updatePerson(person2, person2Copy);
        expectedModel.commitAddressBook();
        assertCommandSuccess(addFriendCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexUnfilteredListThrowsCommandException() {
        Index outOfBoundIndexes = Index.fromOneBased(model.getFilteredPersonList().size() + 1,
                model.getFilteredPersonList().size() + 2);
        AddFriendCommand addFriendCommand = new AddFriendCommand(outOfBoundIndexes);

        assertCommandFailure(addFriendCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AddFriendCommand addFriendFirstCommand = new AddFriendCommand(Index.fromZeroBased(INDEX_SECOND.getZeroBased(),
                INDEX_THIRD.getZeroBased()));
        AddFriendCommand addFriendSecondCommand = new AddFriendCommand(Index.fromZeroBased(INDEX_THIRD.getZeroBased(),
                INDEX_FORTH.getZeroBased()));

        // same object -> returns true
        assertTrue(addFriendFirstCommand.equals(addFriendFirstCommand));

        // same values -> returns true
        AddFriendCommand addFriendFirstCommandCopy = new AddFriendCommand(Index.fromZeroBased(
                INDEX_SECOND.getZeroBased(), INDEX_THIRD.getZeroBased()));
        assertTrue(addFriendFirstCommand.equals(addFriendFirstCommandCopy));

        // different types -> returns false
        assertFalse(addFriendFirstCommand.equals(1));

        // different person -> returns false
        assertFalse(addFriendFirstCommand.equals(addFriendSecondCommand));
    }
}
