package org.kendar.kendarnotifications;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private String strSDesc;
    private String strIncidentNo;
    private String strDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());
        FirebaseMessaging.getInstance().subscribeToTopic(MyConstants.TOPIC);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if (extras != null) {
            setContentView(R.layout.activity_main);

            final TextView IncidentTextView = (TextView) findViewById(R.id.txtIncidentNo);
            strIncidentNo = extras.getString(MyConstants.STR_INCIDENT_NO, "");

            final TextView SDescTextView = (TextView) findViewById(R.id.txtShortDesc);
            strSDesc = extras.getString(MyConstants.STR_SHORT_DESC, "");

            final TextView DescTextView = (TextView) findViewById(R.id.txtDesc);
            strDesc = extras.getString(MyConstants.STR_DESC, "");

            IncidentTextView.setText(strIncidentNo);
            SDescTextView.setText(strSDesc);
            DescTextView.setText(strDesc);
        }
    }

    public void loadData(View view) {
        Intent browserIntent = new Intent
                (Intent.ACTION_VIEW, Uri.parse
                        ("https://somebrowser?uri=incident.do?sysparm_query=number="
                                + strIncidentNo));
        startActivity(browserIntent);
    }
}