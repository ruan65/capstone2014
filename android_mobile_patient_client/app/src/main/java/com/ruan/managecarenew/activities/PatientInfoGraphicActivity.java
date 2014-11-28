package com.ruan.managecarenew.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.ValueDependentColor;
import com.ruan.managecarenew.R;
import com.ruan.managecarenew.enntities.AnswersData;
import com.ruan.managecarenew.helpers.NetworkOperations;

import java.util.List;
import java.util.Map;

import static com.jjoe64.graphview.GraphView.GraphViewData;
import static com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;

public class PatientInfoGraphicActivity extends ActionBarActivity {


    Intent intent;
    FrameLayout ch1;
    FrameLayout ch2;
    TextView tvPatientName;
    SharedPreferences prefs;
    String _id;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_infographic);

        ctx = getApplicationContext();

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        intent = getIntent();

        tvPatientName = (TextView) findViewById(R.id.tv_patient_name);
        tvPatientName.setText(getString(R.string.error_patient_not_selected));
        ch1 = (FrameLayout) findViewById(R.id.chart1);
        ch2 = (FrameLayout) findViewById(R.id.chart2);

        _id = intent.getStringExtra(getString(R.string._id));
        Log.d("ml", "_id " + _id);

        if (_id != null) {
            new GetAnsweredData().execute(  // composing url
                    getString(R.string.url_main_server) +
                            getString(R.string.url_part_get_data) + _id);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        startActivity(new Intent(getApplicationContext(), DoctorActivity.class));
    }

    private GraphView createGraphView(List<AnswersData> answers,
                                      String[] answ, String question, final int color) {

        int size = answers.size();

        GraphViewData[] data = new GraphViewData[answers.size()];

        for (int i = 0; i < size; i++) {

            Map answerData = (Map) answers.get(i);
            Map<String, String> m = (Map) answerData.get(getString(R.string.answers));

            String a = m.get(question);
            int level =
                    a.equals(answ[2]) ? 3
                            : a.equals(answ[1]) ? 2
                            : a.equals(answ[0]) ? 1
                            : 0;

            data[i] = new GraphViewData(i, level);
        }

        GraphViewSeriesStyle seriesStyle = new GraphViewSeriesStyle();
        seriesStyle.setValueDependentColor(new ValueDependentColor() {
            @Override
            public int get(GraphViewDataInterface data) {
                int rgb = Color.argb(80 * (int) data.getY(), color, 0, 0);
                return rgb;
            }
        });

        GraphViewSeries series = new GraphViewSeries(getString(R.string.empty_str), seriesStyle, data);

        GraphView graphView = new BarGraphView(getApplicationContext(),
                getString(R.string.empty_str));
        graphView.setManualYAxisBounds(3, 0);
        graphView.addSeries(series);
        graphView.setShowHorizontalLabels(false);
        graphView.setShowVerticalLabels(false);

        return graphView;
    }

    private class GetAnsweredData extends AsyncTask<String, Void, List<AnswersData>> {

        @Override
        protected List<AnswersData> doInBackground(String... urls) {
            return NetworkOperations.getData(urls[0], prefs.getString(getString(R.string.pref_basic), getString(R.string.empty_str)), ctx);
        }

        @Override
        protected void onPostExecute(List<AnswersData> answers) {
            super.onPostExecute(answers);

            tvPatientName.setText(_id == null
                    ? getString(R.string.error_patient_not_selected)
                    : intent.getStringExtra(
                    getString(R.string.pref_fname))
                    + getString(R.string.space)
                    + intent.getStringExtra(getString(R.string.pref_lname)));

            ch1.addView(createGraphView(
                            answers,
                            getResources().getStringArray(R.array.eating_answers),
                            getString(R.string.question_eating),
                            255)
            );

            ch2.addView(createGraphView(
                            answers,
                            getResources().getStringArray(R.array.pain_answers),
                            getString(R.string.question_pain),
                            128)
            );
        }
    }
}
