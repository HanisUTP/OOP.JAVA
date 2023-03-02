package com.example.testlmg2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestion,tvScore,tvQuestionNo,tvTimer;
    private RadioGroup radioGroup;
    private RadioButton rb1,rb2,rb3,rb4;
    private Button btnNext;
    int totalQuestion;
    int qCounter = 0;
    int score = 0;
    private QuestionModel currentQuestion;
    ColorStateList dfRbColor;
    boolean answered;

    CountDownTimer countDownTimer;




    private List<QuestionModel> questionslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionslist = new ArrayList<>();
        tvQuestion = findViewById(R.id.textQuestion);
        tvScore = findViewById(R.id.textScore);
        tvQuestionNo = findViewById(R.id.textQuestionNo);
        tvTimer = findViewById(R.id.textTimer);

        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        btnNext = findViewById(R.id.btnNext);

        dfRbColor = rb1.getTextColors();


        addQuestions();
        totalQuestion = questionslist.size();
        showNextQuestion();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answered==false){
                    if(rb1.isChecked()||rb2.isChecked()||rb3.isChecked()){
                        checkAnswer();
                        countDownTimer.cancel();
                    }else {
                        Toast.makeText(QuizActivity.this,"please select an option",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    showNextQuestion();
                }
            }
        });


    }

    private void checkAnswer() {
        answered= true;
        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNo = radioGroup.indexOfChild(rbSelected)+1;
        if(answerNo == currentQuestion.getCorrectAnsNo()){
            score++;
            tvScore.setText("SCORE : "+score);
        }
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);
        switch (currentQuestion.getCorrectAnsNo()){
            case 1:
                rb1.setTextColor(Color.GREEN);
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                break;
        }
        if(qCounter<totalQuestion){
            btnNext.setText("Next");
        }else{
            btnNext.setText("Finish");
        }


    }

    private void showNextQuestion() {
        radioGroup.clearCheck();
        rb1.setTextColor(dfRbColor);
        rb2.setTextColor(dfRbColor);
        rb3.setTextColor(dfRbColor);
        rb4.setTextColor(dfRbColor);


        if(qCounter < totalQuestion){
            timer();
            currentQuestion = questionslist.get(qCounter);
            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());
            qCounter++;
            btnNext.setText("SUBMIT");
            tvQuestionNo.setText("Question:"+qCounter+"/"+totalQuestion);
            answered = false;
        }else{finish();}
    }

    private void timer() {
        countDownTimer = new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long l) {
                tvTimer.setText("00:"+ 1/1000);
            }

            @Override
            public void onFinish() {
                showNextQuestion();
            }
        }.start();
    }

    private void addQuestions() {
        questionslist.add(new QuestionModel("A is correct","A","B","C","D",1));
        questionslist.add(new QuestionModel("B is correct","A","B","C","D",1));
        questionslist.add(new QuestionModel("C is correct","A","B","C","D",1));
        questionslist.add(new QuestionModel("D is correct","A","B","C","D",1));
        questionslist.add(new QuestionModel("D is correct","A","B","C","D",1));
    }
}