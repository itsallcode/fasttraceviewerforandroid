package itsallcode.org.fasttraceviewerforandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import itsallcode.org.fasttraceviewerforandroid.repository.FastTraceRepository;

public class StartActivity extends AppCompatActivity {

    @Inject
    FastTraceRepository mFastTraceRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        FastTraceApp.getInstance().getApplicationComponent().inject(this);
    }
}
