package fpoly.example.demoretrofit.demo5;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.example.demoretrofit.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Demo51 extends AppCompatActivity implements SanPhamAdapter.OnSanPhamActionListener {
    EditText txt1, txt2, txt3;
    TextView tvKQ;
    Button btn1;
    RecyclerView recyclerView;
    SanPhamAdapter adapter;
    List<SanPham> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_demo51);

        // Ánh xạ các view từ layout
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        tvKQ = findViewById(R.id.tvKetQua);
        btn1 = findViewById(R.id.BtnCall);
        recyclerView = findViewById(R.id.recyclerView);

        // Thiết lập padding cho các hệ thống thanh công cụ
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Thiết lập LayoutManager cho RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Thiết lập sự kiện khi nhấn nút btn1 để gọi hàm inserData
        btn1.setOnClickListener(v -> inserData(txt1, txt2, txt3, tvKQ));
        fetchSanPham();
    }

    // Hàm insert dữ liệu sản phẩm
    private void inserData(EditText txt1, EditText txt2, EditText txt3, TextView tvKetqua) {
        String maSP = txt1.getText().toString();
        String tenSP = txt2.getText().toString();
        String moTa = txt3.getText().toString();

        // Kiểm tra giá trị không rỗng
        if (maSP.isEmpty() || tenSP.isEmpty() || moTa.isEmpty()) {
            tvKetqua.setText("Thông tin không hợp lệ.");
            return;
        }

        SanPham s = new SanPham(maSP, tenSP, moTa);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.6/httpxam/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceInsertSanPham insertSanPham = retrofit.create(InterfaceInsertSanPham.class);
        Call<SvrResponsiveSanPham> call = insertSanPham.insertSanPham(s.getMaSp(), s.getTenSP(), s.getMoTa());

        call.enqueue(new Callback<SvrResponsiveSanPham>() {
            @Override
            public void onResponse(Call<SvrResponsiveSanPham> call, Response<SvrResponsiveSanPham> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SvrResponsiveSanPham res = response.body();
                    tvKetqua.setText(res.getMessage());
                    fetchSanPham(); // Refresh danh sách sản phẩm sau khi thêm thành công
                } else {
                    tvKetqua.setText("Không nhận được phản hồi từ server hoặc phản hồi không thành công.");
                }
            }

            @Override
            public void onFailure(Call<SvrResponsiveSanPham> call, Throwable throwable) {
                tvKetqua.setText(throwable.getMessage());
            }
        });
    }

    // Hàm fetch dữ liệu sản phẩm
    private void fetchSanPham() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.6/httpxam/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceInsertSanPham getSanPham = retrofit.create(InterfaceInsertSanPham.class);
        Call<SvrResponsiveSanPham> call = getSanPham.hienThiSanPham();

        call.enqueue(new Callback<SvrResponsiveSanPham>() {
            @Override
            public void onResponse(Call<SvrResponsiveSanPham> call, Response<SvrResponsiveSanPham> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SvrResponsiveSanPham res = response.body();
                    List<SanPham> sanPhams = res.getData(); // Lấy danh sách sản phẩm từ phản hồi

                    if (sanPhams != null && !sanPhams.isEmpty()) {
                        list.clear();
                        list.addAll(sanPhams);
                        adapter = new SanPhamAdapter(list, Demo51.this);
                        recyclerView.setAdapter(adapter);
                    } else {
                        tvKQ.setText("Danh sách sản phẩm trống.");
                    }
                } else {
                    tvKQ.setText("Không nhận được phản hồi từ server hoặc phản hồi không thành công.");
                }
            }

            @Override
            public void onFailure(Call<SvrResponsiveSanPham> call, Throwable throwable) {
                tvKQ.setText(throwable.getMessage());
            }
        });
    }

    @Override
    public void onDeleteSanPham(SanPham sanPham) {
        Log.d("Demo51", "Mã sản phẩm cần xóa: " + sanPham.getMaSp());
        deleteData(sanPham.getMaSp(), tvKQ);
    }

    @Override
    public void onEditSanPham(SanPham sanPham) {
        showEditDialog(sanPham);
    }

    // Hàm hiển thị dialog để chỉnh sửa sản phẩm
    private void showEditDialog(SanPham sanPham) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_sanpham, null);
        builder.setView(dialogView);

        EditText editTxt1 = dialogView.findViewById(R.id.editTextMaSP);
        EditText editTxt2 = dialogView.findViewById(R.id.editTextMoTa);
        EditText editTxt3 = dialogView.findViewById(R.id.editTextTenSP);
        Button btnUpdate = dialogView.findViewById(R.id.buttonUpdate);

        editTxt1.setText(sanPham.getMaSp());
        editTxt2.setText(sanPham.getTenSP());
        editTxt3.setText(sanPham.getMoTa());

        AlertDialog dialog = builder.create();

        btnUpdate.setOnClickListener(v -> {
            updateData(editTxt1, editTxt2, editTxt3, tvKQ);
            dialog.dismiss();
        });

        dialog.show();
    }

    // Hàm update dữ liệu sản phẩm
    private void updateData(EditText txt1, EditText txt2, EditText txt3, TextView tvKetqua) {
        SanPham s = new SanPham(txt1.getText().toString(), txt2.getText().toString(), txt3.getText().toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.6/httpxam/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceInsertSanPham updateSanPham = retrofit.create(InterfaceInsertSanPham.class);
        Call<SvrResponsiveSanPham> call = updateSanPham.updateSanPham(s.getMaSp(), s.getTenSP(), s.getMoTa());

        call.enqueue(new Callback<SvrResponsiveSanPham>() {
            @Override
            public void onResponse(Call<SvrResponsiveSanPham> call, Response<SvrResponsiveSanPham> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SvrResponsiveSanPham res = response.body();
                    tvKetqua.setText(res.getMessage());
                    fetchSanPham(); // Refresh danh sách sản phẩm sau khi cập nhật thành công
                } else {
                    tvKetqua.setText("Không nhận được phản hồi từ server hoặc phản hồi không thành công.");
                }
            }

            @Override
            public void onFailure(Call<SvrResponsiveSanPham> call, Throwable throwable) {
                tvKetqua.setText(throwable.getMessage());
            }
        });
    }

    // Hàm delete dữ liệu sản phẩm
    private void deleteData(String maSP, TextView tvKetqua) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.6/httpxam/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceInsertSanPham deleteSanPham = retrofit.create(InterfaceInsertSanPham.class);
        Call<SvrResponsiveSanPham> call = deleteSanPham.deleteSanPham(maSP);

        call.enqueue(new Callback<SvrResponsiveSanPham>() {
            @Override
            public void onResponse(Call<SvrResponsiveSanPham> call, Response<SvrResponsiveSanPham> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SvrResponsiveSanPham res = response.body();
                    tvKetqua.setText(res.getMessage());
                    fetchSanPham(); // Refresh danh sách sản phẩm sau khi xóa thành công
                } else {
                    tvKetqua.setText("Không nhận được phản hồi từ server hoặc phản hồi không thành công.");
                }
            }

            @Override
            public void onFailure(Call<SvrResponsiveSanPham> call, Throwable throwable) {
                tvKetqua.setText(throwable.getMessage());
            }
        });
    }
}

