package fpoly.example.demoretrofit.demo5;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fpoly.example.demoretrofit.R;

public class DemoVolley extends AppCompatActivity {
    EditText txtname, txtGiaSP, txtMota;
    Button btnSelect, btnKQ;
    TextView tvKQ;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_volley);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtname = findViewById(R.id.editText1);
        txtGiaSP = findViewById(R.id.editText2);
        txtMota = findViewById(R.id.editText3);
        btnSelect = findViewById(R.id.buttonSelect);
        btnKQ = findViewById(R.id.buttonKetQua);
        tvKQ = findViewById(R.id.textViewKetQua);
        context = this; // initialize context

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectVolley();
            }
        });
    }

    String strKQ = "";

    private void selectVolley() {
        strKQ = "";
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://hungnq28.000webhostapp.com/su2024/select.php";

        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("products");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject p = jsonArray.getJSONObject(i);
                        String MaSP = p.getString("MaSP");
                        String TenSP = p.getString("TenSP");
                        String Mota = p.getString("MoTa");
                        strKQ += "MaSP : " + MaSP + "; TenSP : " + TenSP + "; MoTa : " + Mota + "\n";
                    }
                    tvKQ.setText(strKQ);
                } catch (JSONException e) {
                    e.printStackTrace();
                    tvKQ.setText("JSON Parsing error: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                tvKQ.setText("Volley error: " + volleyError.getMessage());
            }
        });

        queue.add(request);
    }
}
