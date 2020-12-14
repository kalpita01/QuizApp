package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button trueButton;
    private Button falseButton;
    private ImageView prevButton;
    private ImageView nextButton;
    private TextView displayQuestion;
    private TextView countScore;
    private List<Question> questionList;
    private int currentQuestionIndex=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        prevButton = findViewById(R.id.prev_button);
        nextButton = findViewById(R.id.next_button);
        displayQuestion = findViewById(R.id.question_text);
        countScore = findViewById(R.id.counter_text);



        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {

                displayQuestion.setText(questionArrayList.get(currentQuestionIndex).getAnswer());

                Log.d("Main", "onCreate: " + questionArrayList);
            }
        });

        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.prev_button:
                if(currentQuestionIndex != 0)
                    currentQuestionIndex = (currentQuestionIndex-1) % questionList.size() ;
                else
                    currentQuestionIndex = (questionList.size()-1);
                updateQuestion();
                break;
            case R.id.next_button:
                currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
                updateQuestion();
                break;
            case R.id.true_button:
                checkAnswer(true);
                break;
            case R.id.false_button:
                checkAnswer(false);
                break;
        }

    }

    private void checkAnswer(boolean b) {
        Boolean trueAnswer = questionList.get(currentQuestionIndex).isAnswerTrue();
        if(b == trueAnswer){
            Toast.makeText(MainActivity.this, "The answer is correct",Toast.LENGTH_SHORT)
                    .show();
        }
        else{
            Toast.makeText(MainActivity.this, "The answer is not correct",Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        displayQuestion.setText(question);
        countScore.setText(currentQuestionIndex + " out of " + questionList.size());
    }
}