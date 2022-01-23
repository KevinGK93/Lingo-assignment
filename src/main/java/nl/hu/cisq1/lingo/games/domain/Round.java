package nl.hu.cisq1.lingo.games.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.hu.cisq1.lingo.games.domain.enumerations.ErrorMessages;
import nl.hu.cisq1.lingo.games.domain.enumerations.GameProgress;
import nl.hu.cisq1.lingo.games.domain.enumerations.Mark;
import nl.hu.cisq1.lingo.games.domain.exceptions.ExceptionMessages;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "round")
public class Round {
    public static final int MAX_ATTEMPTS = 5;

    @Id
    @GeneratedValue
    private Long id;
    private String wordToGuess;

    @OneToMany
    @JoinColumn
    @Cascade(CascadeType.ALL)
    private final List<Feedback> feedbackHistory = new ArrayList<>();

    public Round(){
        //empty constructor required by Hibernate.
    }

    public Round(String wordToGuess){
        this.wordToGuess = wordToGuess;
    }

    // determines the score based on assignment description \\
    public int determineScore(){
        return 5 * (5 - feedbackHistory.size() + 5);
    }

    public int getFeedbackHistorySize(){
        return feedbackHistory.size();
    }

    // If the feedbackHistorySize equals 6 the player has lost.\\
    public boolean thePlayerHasLost(){
        return getFeedbackHistorySize() == 5;

    }
    // Show the last given feedback mark -1 to show the previous mark. \\
    public List<Mark> getLastGivenFeedback(){
        return feedbackHistory.get(feedbackHistory.size() -1).getMarks();
    }

    //the word is guessed if the last given feedback does not contain any ABSENT or PRESENT chars for each index \\
    public boolean isWordGuessed(){
        var feedbackGiven = this.getLastGivenFeedback();
        return !feedbackGiven.contains(Mark.ABSENT) && !feedbackGiven.contains(Mark.PRESENT);
    }

    public boolean roundActive(GameProgress progress){
        return progress == GameProgress.PLAYING;
    }

    // get the (int) length of the word for the game.\\
    public int getWordToGuessLength(){
        return wordToGuess.length();
    }

    //If the word in length is not equal to the length of the wordToGuess throw new errorMessage. \\
    //add the guess and the marks to feedbackHistory.\\
    public void attemptAtWord(String guess){
        String rightWord = this.getWordToGuess();
        if(guess.length() != rightWord.length()){
            throw new ExceptionMessages(ErrorMessages.WORD_LENGTH_NOT_EQUAL_TO_GUESS_LENGTH.getErrorMessage());
        }
        var feedback = new Feedback();
        var marks = feedback.checkMarks(guess, rightWord);
        feedbackHistory.add(new Feedback(guess, marks));
    }





}

