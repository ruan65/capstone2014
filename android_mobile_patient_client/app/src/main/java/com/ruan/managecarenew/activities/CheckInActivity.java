package com.ruan.managecarenew.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.ruan.managecarenew.R;
import com.ruan.managecarenew.enntities.CheckIn;
import com.ruan.managecarenew.enntities.MultipleChoiceQuestion;
import com.ruan.managecarenew.enntities.SimpleQuestion;
import com.ruan.managecarenew.fragments.DatePickerFragment;
import com.ruan.managecarenew.fragments.MultipleQFragment;
import com.ruan.managecarenew.fragments.QFragment;
import com.ruan.managecarenew.fragments.TimePickerFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ruan.managecarenew.helpers.NetworkOperations.URL_TYPE_GET;
import static com.ruan.managecarenew.helpers.NetworkOperations.URL_TYPE_POST;
import static com.ruan.managecarenew.helpers.NetworkOperations.downloadCheckIn;
import static com.ruan.managecarenew.helpers.NetworkOperations.uploadCheckin;

public class CheckInActivity extends ActionBarActivity
        implements MultipleQFragment.MultipleQuestionFragmentListener,
        QFragment.QFragmentListener,
        DatePickerFragment.DatePickerListener,
        TimePickerFragment.TimePickerListener {

    SharedPreferences prefs;
    Context appCtx;
    MultipleQFragment multipleQFragment;
    QFragment qFragment;
    DatePickerFragment datePickerFragment;
    TimePickerFragment timePickerFragment;
    List<SimpleQuestion> clarifyingQuestions;

    TextView tv;

    private CheckIn mCheckIn;
    private List<MultipleChoiceQuestion> mQuestions;
    private List<SimpleQuestion> sQuestions;

    private MultipleChoiceQuestion currentMChQuestion;
    private SimpleQuestion currentQuestion;

    private int qCount;
    private int mchCount;
    private int clarifCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        appCtx = getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(appCtx);
        tv = (TextView) findViewById(R.id.tv_checkin_activ);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCheckIn == null) {
            new DownloadCheckIn().execute();
        }
    }
    /**
     * **************** Callbacks ****************
     */
    @Override
    public void onClickOk() {

        if (currentMChQuestion != null) {
            currentMChQuestion.setAnswer(multipleQFragment.getAnswer());
            currentMChQuestion.setAnswered(true);
            currentMChQuestion.setAnswerTimeStamp(new Date());
        }
        processMultipleChoice();
    }

    @Override
    public void onClickYes() {

        if (currentQuestion.isTimeMarkNeeded()) {

            datePickerFragment = new DatePickerFragment();

            datePickerFragment.show(getSupportFragmentManager(), getString(R.string.date_picker));

        } else if (currentQuestion.isClarifyingQuestionsNeeded()) {
            clarifyingQuestions = currentQuestion.getClarifyingQuestions();
            processClarifyingQuestions();
        } else {
            continueProcessQuestion();
        }
    }

    @Override
    public void onClickNo() {

        currentQuestion.setAnswered(true);
        currentQuestion.setAnswerTimeStamp(new Date());
        checkTypeAndGo();
    }

    @Override
    public void onDatePicked() {
        timePickerFragment = new TimePickerFragment();
        timePickerFragment.setCalendar(datePickerFragment.getCalendar());
        timePickerFragment.show(getSupportFragmentManager(), getString(R.string.time_picker));

    }

    @Override
    public void onTimePicked() {
        currentQuestion.setTimeMark(timePickerFragment.getCalendar().getTime());
        continueProcessQuestion();
    }
    /**
     * *************** Logic and Helpers ************************
     */
    private void processCheckIn() {
        mQuestions = mCheckIn.getMultipleChoiceQuestions();
        sQuestions = mCheckIn.getSimpleQuestions();
        processMultipleChoice();
    }

    private void processMultipleChoice() {

        if (mchCount < mQuestions.size()) {

            currentMChQuestion = mQuestions.get(mchCount++);
            multipleQFragment = MultipleQFragment.newInstance(currentMChQuestion.getText(),
                    (ArrayList<String>) currentMChQuestion.getAnswers());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_question, multipleQFragment)
                    .commit();
        } else {
            processSimpleQuestions();
        }
    }

    private void processSimpleQuestions() {

        if (qCount < sQuestions.size()) {

            currentQuestion = sQuestions.get(qCount++);
            startQFragment();

        } else {

            Toast.makeText(getApplicationContext(),
                    getString(R.string.sending_result_msg), Toast.LENGTH_SHORT).show();
            mCheckIn.setChecked(true);
            mCheckIn.setResponseDateTime(new Date());

            new SendCheckinBack().execute(mCheckIn);

            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void startQFragment() {
        qFragment = QFragment.newInstance(currentQuestion.getText());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_question, qFragment)
                .commit();
    }

    private void processClarifyingQuestions() {

        if (clarifyingQuestions.size() > clarifCount) {
            currentQuestion = clarifyingQuestions.get(clarifCount++);
            startQFragment();
        } else {
            clarifyingQuestions = null;
            clarifCount = 0;
            processSimpleQuestions();
        }
    }

    private void continueProcessQuestion() {

        currentQuestion.setYesAnswer(true);
        currentQuestion.setAnswered(true);
        currentQuestion.setAnswerTimeStamp(new Date());
        checkTypeAndGo();
    }


    private void checkTypeAndGo() {
        if (clarifyingQuestions == null) {
            processSimpleQuestions();
        } else {
            processClarifyingQuestions();
        }
    }

    private String getBasic() {
        return prefs.getString(appCtx.getString(R.string.pref_basic), getString(R.string.empty_str));
    }

    private String getUrl(int type) {

        String url = getString(R.string.empty_str);
        switch (type) {
            case URL_TYPE_GET:
                url = getString(R.string.url_checkin);
                break;
            case URL_TYPE_POST:
                url = getString(R.string.url_checkin_send_back);
                break;
        }
        return  url + prefs.getString(appCtx.getString(R.string.pref_id),
                getString(R.string.empty_str));
    }
    /**
     * ****************** Background Tasks *******************
     */
    private class DownloadCheckIn extends AsyncTask<Void, Void, CheckIn> {

        @Override
        protected CheckIn doInBackground(Void... v) {

            return downloadCheckIn(
                    getUrl(URL_TYPE_GET), getBasic(), appCtx);
        }

        @Override
        protected void onPostExecute(CheckIn checkIn) {

            if (checkIn != null) {
                mCheckIn = checkIn;
                processCheckIn();
            } else {
                tv.setText(getString(R.string.connection_error));
            }
        }
    }

    private class SendCheckinBack extends AsyncTask<CheckIn, Void, Boolean> {

        @Override
        protected Boolean doInBackground(CheckIn... checkIns) {

            return uploadCheckin(
                    getUrl(URL_TYPE_POST), getBasic(), checkIns[0], appCtx);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            Toast.makeText(appCtx, aBoolean
                            ? appCtx.getString(R.string.checkin_saved)
                            : appCtx.getString(R.string.connection_error),
                    Toast.LENGTH_LONG).show();
        }
    }
}

















