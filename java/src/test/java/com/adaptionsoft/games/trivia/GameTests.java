package com.adaptionsoft.games.trivia;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.adaptionsoft.games.uglytrivia.GameResultWriter;
import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.QuestionWriterForMockist;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class GameTests {

    private Game game;
    @Before
    public void setup(){
        game = new Game();
    }

    private ByteArrayOutputStream getConsoleOutput() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(stream);
        System.setOut(printStream);
        return stream;
    }

    @Test
    public void whenGameIsCreatedNothingIsWrittenToTheOutput(){
        ByteArrayOutputStream stream = getConsoleOutput();
        String emptyString = "";

        assertEquals(emptyString, stream.toString());
    }

    @Test
    public void whenPlayerIsAddedHisNameAndPlayerNumberIsWritten(){
        ByteArrayOutputStream stream = getConsoleOutput();
        String playerNameAndNumber = "Adi was added\n" +
                "They are player number 1\n";
        String playerName = "Adi";

        game.add(playerName);

        assertEquals(playerNameAndNumber, stream.toString());
    }

    @Test
    public void whenTwoPlayersAreAddedTheirNameAndPlayerNumbersAreWritten(){
        ByteArrayOutputStream stream = getConsoleOutput();
        String playerNameAndNumber = "Adi was added\n" +
                "They are player number 1\n" +
                "Alex was added\n" +
                "They are player number 2\n";
        String playerName = "Adi";
        String secondPlayerName = "Alex";

        game.add(playerName);
        game.add(secondPlayerName);

        assertEquals(playerNameAndNumber, stream.toString());
    }

    @Test
    public void whenRollingDiceMessageAboutDiceAndPlayerLocationAndCategoryIsWrittenToOutput(){
        ByteArrayOutputStream stream = getConsoleOutput();
        game.add("SomePlayer");

        game.roll(1);

        assertEquals("SomePlayer was added\n" +
                "They are player number 1\n" +
                "SomePlayer is the current player\n" +
                "They have rolled a 1\n" +
                "SomePlayer's new location is 1\n" +
                "The category is Science\n" +
                "Science Question 0\n", stream.toString());
    }

    @Test
    public void writesWinnerMessageWhenWonSixGoldCoins(){
        Game game = new Game();
        GameResultWriter gameResultWriterMock = mock(GameResultWriter.class);
        game.setGameResultWriter(gameResultWriterMock);
        game.add("Adi");
        game.wasCorrectlyAnswered();
        game.wasCorrectlyAnswered();
        game.wasCorrectlyAnswered();
        game.wasCorrectlyAnswered();
        game.wasCorrectlyAnswered();

        game.wasCorrectlyAnswered();

        verify(gameResultWriterMock).writeLine("Adi now has 6 Gold Coins.");
    }

    @Test
    public void tellsQuestionWriterToAskQuestion(){
        QuestionWriterForMockist questionWriter = mock(QuestionWriterForMockist.class);

        game.ask(questionWriter);

        verify(questionWriter).ask("valid question");
    }


}