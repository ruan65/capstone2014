package com.ruan.managecarenew.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ruan.managecarenew.R;

import java.util.ArrayList;
import java.util.List;

public class MultipleQFragment extends Fragment {

    private static final String QUESTION = "question_text";
    private static final String ANSWERS = "answers";

    private String mQuestion;
    private List<String> mAnswers;

    private String answer;

    MultipleQuestionFragmentListener mListener;

    Context ctx;
    RadioGroup radioGroup;

    public MultipleQFragment() {}

    public interface MultipleQuestionFragmentListener {
        public void onClickOk();
    }

    public static MultipleQFragment newInstance(String question, ArrayList<String> answers) {
        MultipleQFragment fragment = new MultipleQFragment();
        Bundle args = new Bundle();
        args.putString(QUESTION, question);
        args.putStringArrayList(ANSWERS, answers);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuestion = getArguments().getString(QUESTION);
            mAnswers = getArguments().getStringArrayList(ANSWERS);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_multiple_question, container, false);
        rootView.findViewById(R.id.btn_ok_mq_fragment).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (mListener != null) {
                    int id = radioGroup.getCheckedRadioButtonId();

                    if (id == -1) {
                        Toast.makeText(ctx, ctx.getString(R.string.toast_pick_answer), Toast.LENGTH_SHORT).show();
                    } else {
                        setAnswer(mAnswers.get(id));
                        mListener.onClickOk();
                    }
                }
            }
        });

        ((TextView)rootView.findViewById(R.id.tv_question_mq_fragment)).setText(mQuestion);

        radioGroup = (RadioGroup) rootView.findViewById(R.id.rg_mq_fragment);

        for (int i = 0; i < mAnswers.size(); i++) {
            RadioButton rb = new RadioButton(ctx);
            rb.setText(mAnswers.get(i));
            rb.setTextSize(20);
            rb.setId(i);
            radioGroup.addView(rb);
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ctx = getActivity();
        try {
            mListener = (MultipleQuestionFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + activity.getString(R.string.error_must_impl_listener));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
