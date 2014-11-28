package com.ruan.managecarenew.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruan.managecarenew.R;

public class QFragment extends Fragment implements OnClickListener {

    private static final String QUESTION = "question_text";

    private static String mQuestion;

    Context ctx;

    QFragmentListener mListener;

    public interface QFragmentListener {
        public void onClickYes();
        public void onClickNo();
    }

    public QFragment() {}

    public static QFragment newInstance(String question) {
        QFragment qFragment = new QFragment();
        Bundle bundle = new Bundle();
        bundle.putString(QUESTION, question);
        qFragment.setArguments(bundle);
        return qFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuestion = getArguments().getString(QUESTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.q_fragment, container, false);

        ((TextView) rootView.findViewById(R.id.tv_q_fragment)).setText(mQuestion);

        rootView.findViewById(R.id.btn_yes_q_fragment).setOnClickListener(this);
        rootView.findViewById(R.id.btn_no_q_fragment).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ctx = getActivity();
        try {
            mListener = (QFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + ctx.getString(R.string.error_must_impl_listener));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_yes_q_fragment:
                mListener.onClickYes();
                break;
            case R.id.btn_no_q_fragment:
                mListener.onClickNo();
                break;
            default:
                break;
        }
    }
}































