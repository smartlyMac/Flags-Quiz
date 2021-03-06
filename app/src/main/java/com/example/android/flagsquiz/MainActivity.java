package com.example.android.flagsquiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    int score;
    @BindView(R.id.q1slovenia) RadioButton q1Slovenia;
    @BindView(R.id.q2blue) CheckBox q2Blue;
    @BindView(R.id.q2green) CheckBox q2Green;
    @BindView(R.id.q2red) CheckBox q2Red;
    @BindView(R.id.q2yellow) CheckBox q2Yellow;
    @BindView(R.id.q2white) CheckBox q2White;
    @BindView(R.id.q3ivorycoast) RadioButton q3IvoryCoast;
    @BindView(R.id.q4answer) EditText q4AnswerField;
    @BindView(R.id.q5answerD) RadioButton q5AnswerD;
    @BindView(R.id.q6chad) CheckBox q6Chad;
    @BindView(R.id.q6colombia) CheckBox q6Colombia;
    @BindView(R.id.q6moldova) CheckBox q6Moldova;
    @BindView(R.id.q6romania) CheckBox q6Romania;
    @BindView(R.id.q7answer) EditText q7AnswerField;
    @BindView(R.id.q8netherlands) RadioButton q8netherlands;
    @BindView(R.id.q9answerB) RadioButton q9AnswerB;
    @BindView(R.id.q10answerA) EditText q10AnswerFieldA;
    @BindView(R.id.q10answerB) EditText q10AnswerFieldB;
    @BindView(R.id.q1Layout) View q1Layout;
    @BindView(R.id.q2Layout) View q2Layout;
    @BindView(R.id.q3Layout) View q3Layout;
    @BindView(R.id.q4Layout) View q4Layout;
    @BindView(R.id.q5Layout) View q5Layout;
    @BindView(R.id.q6Layout) View q6Layout;
    @BindView(R.id.q7Layout) View q7Layout;
    @BindView(R.id.q8Layout) View q8Layout;
    @BindView(R.id.q9Layout) View q9Layout;
    @BindView(R.id.q10Layout) View q10Layout;
    int q1LayoutState, q2LayoutState, q3LayoutState, q4LayoutState, q5LayoutState, q6LayoutState, q7LayoutState, q8LayoutState, q9LayoutState, q10LayoutState;
    @BindView(R.id.q2Image) ImageView q2Image;
    @BindView(R.id.q4Image) ImageView q4Image;
    @BindView(R.id.q6Image) ImageView q6Image;
    @BindView(R.id.q7Image) ImageView q7Image;
    @BindView(R.id.q10Image) ImageView q10Image;
    TextView resultScore, resultsMessage;
    View dialogView, cheatView, resetView;
    AlertDialog.Builder builder;
    AlertDialog resultsDialog, cheatDialog, resetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialogView = inflater.inflate(R.layout.results_dialog, null);
        resultScore = dialogView.findViewById(R.id.resultScore);
        resultsMessage = dialogView.findViewById(R.id.resultsMessage);

        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(dialogView)
                .setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resultsDialog.dismiss();
                    }
                });
        resultsDialog = builder.create();

        cheatView = inflater.inflate(R.layout.cheat_dialog, null);
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(cheatView)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cheatDialog.dismiss();
                        setCorrectAnswers();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cheatDialog.dismiss();
                    }
                });
        cheatDialog = builder.create();

        resetView = inflater.inflate(R.layout.reset_dialog, null);
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(resetView)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetDialog.dismiss();
                        resetAll();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetDialog.dismiss();
                    }
                });
        resetDialog = builder.create();
    }

    /**
     * Saves the state of the app
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("q1LayoutState", q1LayoutState);
        savedInstanceState.putInt("q2LayoutState", q2LayoutState);
        savedInstanceState.putInt("q3LayoutState", q3LayoutState);
        savedInstanceState.putInt("q4LayoutState", q4LayoutState);
        savedInstanceState.putInt("q5LayoutState", q5LayoutState);
        savedInstanceState.putInt("q6LayoutState", q6LayoutState);
        savedInstanceState.putInt("q7LayoutState", q7LayoutState);
        savedInstanceState.putInt("q8LayoutState", q8LayoutState);
        savedInstanceState.putInt("q9LayoutState", q9LayoutState);
        savedInstanceState.putInt("q10LayoutState", q10LayoutState);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Restores the state of the app
     */
    @Override
    public void onRestoreInstanceState(Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);
        if (saveInstanceState != null) {
            q1LayoutState = saveInstanceState.getInt("q1LayoutState");
            q2LayoutState = saveInstanceState.getInt("q2LayoutState");
            q3LayoutState = saveInstanceState.getInt("q3LayoutState");
            q4LayoutState = saveInstanceState.getInt("q4LayoutState");
            q5LayoutState = saveInstanceState.getInt("q5LayoutState");
            q6LayoutState = saveInstanceState.getInt("q6LayoutState");
            q7LayoutState = saveInstanceState.getInt("q7LayoutState");
            q8LayoutState = saveInstanceState.getInt("q8LayoutState");
            q9LayoutState = saveInstanceState.getInt("q9LayoutState");
            q10LayoutState = saveInstanceState.getInt("q10LayoutState");
        }
        if (q1LayoutState == 1) {
            q1Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
        } else if (q1LayoutState == 2) {
            q1Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
        }
        if (q2LayoutState == 1) {
            q2Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
            q2Image.setImageDrawable(getResources().getDrawable(R.drawable.norway));
        } else if (q2LayoutState == 2) {
            q2Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
        }
        if (q3LayoutState == 1) {
            q3Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
        } else if (q3LayoutState == 2) {
            q3Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
        }
        if (q4LayoutState == 1) {
            q4Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
            q4Image.setImageDrawable(getResources().getDrawable(R.drawable.australia));
        } else if (q4LayoutState == 2) {
            q4Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
        }
        if (q5LayoutState == 1) {
            q5Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
        } else if (q5LayoutState == 2) {
            q5Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
        }
        if (q6LayoutState == 1) {
            q6Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
            q6Image.setImageDrawable(getResources().getDrawable(R.drawable.romania_chad));
        } else if (q6LayoutState == 2) {
            q6Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
        }
        if (q7LayoutState == 1) {
            q7Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
            q7Image.setImageDrawable(getResources().getDrawable(R.drawable.nepal));
        } else if (q7LayoutState == 2) {
            q7Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
        }
        if (q8LayoutState == 1) {
            q8Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
        } else if (q8LayoutState == 2) {
            q8Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
        }
        if (q9LayoutState == 1) {
            q9Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
        } else if (q9LayoutState == 2) {
            q9Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
        }
        if (q10LayoutState == 1) {
            q10Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
            q10Image.setImageDrawable(getResources().getDrawable(R.drawable.vatican_swiss));
        } else if (q10LayoutState == 2) {
            q10Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
        }
    }

    /**
     * Checks all answers and displays a dialog with the results of the quiz
     */
    public void checkResults(View view) {
        score = 0;
        score = checkQuestion1(score);
        score = checkQuestion2(score);
        score = checkQuestion3(score);
        score = checkQuestion4(score);
        score = checkQuestion5(score);
        score = checkQuestion6(score);
        score = checkQuestion7(score);
        score = checkQuestion8(score);
        score = checkQuestion9(score);
        score = checkQuestion10(score);

        if (score == 0) {
            resultsMessage.setText(getResources().getString(R.string.score0));
        } else if (score > 0 && score < 4) {
            resultsMessage.setText(getResources().getString(R.string.score1to3));
        } else if (score > 3 && score < 7) {
            resultsMessage.setText(getResources().getString(R.string.score4to6));
        } else if (score > 6 && score < 11) {
            resultsMessage.setText(getResources().getString(R.string.score7to10));
        } else {
            resultsMessage.setText(getResources().getString(R.string.score11));
        }

        String scoreString = String.valueOf(score);
        String scoreString2 = getString(R.string.resultScore, scoreString);
        resultScore.setText(scoreString2);

        Toast resultsToast = Toast.makeText(this, scoreString2, Toast.LENGTH_SHORT);
        resultsToast.show();
        resultsDialog.show();
    }

    /**
     * Check the results for Question 1
     */
    public int checkQuestion1(int score) {
        if (q1Slovenia.isChecked()) {
            score += 1;
            q1Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
            q1LayoutState = 1;
        } else {
            q1Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
            q1LayoutState = 2;
        }
        return score;
    }

    /**
     * Check the results for Question 2
     */
    public int checkQuestion2(int score) {
        if (q2Blue.isChecked() && q2Red.isChecked() && q2White.isChecked() && !q2Green.isChecked() && !q2Yellow.isChecked()) {
            score += 1;
            q2Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
            q2Image.setImageDrawable(getResources().getDrawable(R.drawable.norway));
            q2LayoutState = 1;
        } else {
            q2Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
            q2LayoutState = 2;
        }
        return score;
    }

    /**
     * Check the results for Question 3
     */
    public int checkQuestion3(int score) {
        if (q3IvoryCoast.isChecked()) {
            score += 1;
            q3Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
            q3LayoutState = 1;
        } else {
            q3Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
            q3LayoutState = 2;
        }
        return score;
    }

    /**
     * Check the results for Question 4
     */
    public int checkQuestion4(int score) {
        String q4Answer = q4AnswerField.getText().toString().trim();
        if (q4Answer.equals(getResources().getString(R.string.q4correctanswer))) {
            score += 1;
            q4Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
            q4Image.setImageDrawable(getResources().getDrawable(R.drawable.australia));
            q4LayoutState = 1;
        } else {
            q4Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
            q4LayoutState = 2;
        }
        return score;
    }

    /**
     * Check the results for Question 5
     */
    public int checkQuestion5(int score) {
        if (q5AnswerD.isChecked()) {
            score += 1;
            q5Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
            q5LayoutState = 1;
        } else {
            q5Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
            q5LayoutState = 2;
        }
        return score;
    }

    /**
     * Check the results for Question 6
     */
    public int checkQuestion6(int score) {
        if (q6Chad.isChecked() && q6Romania.isChecked() && !q6Colombia.isChecked() && !q6Moldova.isChecked()) {
            score += 1;
            q6Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
            q6Image.setImageDrawable(getResources().getDrawable(R.drawable.romania_chad));
            q6LayoutState = 1;
        } else {
            q6Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
            q6LayoutState = 2;
        }
        return score;
    }

    /**
     * Check the results for Question 7
     */
    public int checkQuestion7(int score) {
        String q7Answer = q7AnswerField.getText().toString().trim();
        if (q7Answer.equalsIgnoreCase(getResources().getString(R.string.q7correctanswer))) {
            score += 1;
            q7Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
            q7Image.setImageDrawable(getResources().getDrawable(R.drawable.nepal));
            q7LayoutState = 1;
        } else {
            q7Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
            q7LayoutState = 2;
        }
        return score;
    }

    /**
     * Check the results for Question 8
     */
    public int checkQuestion8(int score) {
        if (q8netherlands.isChecked()) {
            score += 1;
            q8Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
            q8LayoutState = 1;
        } else {
            q8Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
            q8LayoutState = 2;
        }
        return score;
    }

    /**
     * Check the results for Question 9
     */
    public int checkQuestion9(int score) {
        if (q9AnswerB.isChecked()) {
            score += 1;
            q9Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
            q9LayoutState = 1;
        } else {
            q9Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
            q9LayoutState = 2;
        }
        return score;
    }

    /**
     * Check the results for Question 10
     */
    public int checkQuestion10(int score) {
        String q10AnswerA = q10AnswerFieldA.getText().toString().trim();
        String q10AnswerB = q10AnswerFieldB.getText().toString().trim();
        if ((q10AnswerA.equalsIgnoreCase(getResources().getString(R.string.q10answer1)))) {
            score += 1;
        }
        if ((q10AnswerA.equalsIgnoreCase(getResources().getString(R.string.q10answer2)))) {
            score += 1;
        }
        if ((q10AnswerB.equalsIgnoreCase(getResources().getString(R.string.q10answer1))) && (!q10AnswerA.equalsIgnoreCase(getResources().getString(R.string.q10answer1)))) {
            score += 1;
        }
        if ((q10AnswerB.equalsIgnoreCase(getResources().getString(R.string.q10answer2))) && (!q10AnswerA.equalsIgnoreCase(getResources().getString(R.string.q10answer2)))) {
            score += 1;
        }
        if ((q10AnswerA.equalsIgnoreCase(getResources().getString(R.string.q10answer1)) && q10AnswerB.equalsIgnoreCase(getResources().getString(R.string.q10answer2))) || (q10AnswerA.equalsIgnoreCase(getResources().getString(R.string.q10answer2)) && q10AnswerB.equalsIgnoreCase(getResources().getString(R.string.q10answer1)))) {
            q10Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
            q10Image.setImageDrawable(getResources().getDrawable(R.drawable.vatican_swiss));
            q10LayoutState = 1;
        } else {
            q10Layout.setBackground(getResources().getDrawable(R.drawable.question_border_incorrect));
            q10LayoutState = 2;
        }
        return score;
    }

    /**
     * This methods sets all the answers to the correct ones
     */
    public void setCorrectAnswers () {
        q1Slovenia.setChecked(true);
        q2Blue.setChecked(true);
        q2Red.setChecked(true);
        q2White.setChecked(true);
        q2Green.setChecked(false);
        q2Yellow.setChecked(false);
        q3IvoryCoast.setChecked(true);
        q4AnswerField.setText(getResources().getString(R.string.q4correctanswer));
        q5AnswerD.setChecked(true);
        q6Chad.setChecked(true);
        q6Romania.setChecked(true);
        q6Colombia.setChecked(false);
        q6Moldova.setChecked(false);
        q7AnswerField.setText(getResources().getString(R.string.q7correctanswer));
        q8netherlands.setChecked(true);
        q9AnswerB.setChecked(true);
        q10AnswerFieldA.setText(getResources().getString(R.string.q10answer1));
        q10AnswerFieldB.setText(getResources().getString(R.string.q10answer2));
        q1Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
        q2Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
        q3Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
        q4Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
        q5Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
        q6Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
        q7Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
        q8Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
        q9Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
        q10Layout.setBackground(getResources().getDrawable(R.drawable.question_border_correct));
        q2Image.setImageDrawable(getResources().getDrawable(R.drawable.norway));
        q4Image.setImageDrawable(getResources().getDrawable(R.drawable.australia));
        q6Image.setImageDrawable(getResources().getDrawable(R.drawable.romania_chad));
        q7Image.setImageDrawable(getResources().getDrawable(R.drawable.nepal));
        q10Image.setImageDrawable(getResources().getDrawable(R.drawable.vatican_swiss));
        q1LayoutState = 1;
        q2LayoutState = 1;
        q3LayoutState = 1;
        q4LayoutState = 1;
        q5LayoutState = 1;
        q6LayoutState = 1;
        q7LayoutState = 1;
        q8LayoutState = 1;
        q9LayoutState = 1;
        q10LayoutState = 1;
    }

    /**
     * This method calls the Reveal Answers dialog
     */
    public void showCorrectAnswers(View view) {
        cheatDialog.show();
    }

    /**
     * This methods resets the app
     */
    public void resetAll () {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

    /**
     * This method calls the Reset dialog
     */
    public void resetAllButton(View view) {
        resetDialog.show();
    }

}
