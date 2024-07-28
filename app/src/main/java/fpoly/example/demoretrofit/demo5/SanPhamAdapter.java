package fpoly.example.demoretrofit.demo5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpoly.example.demoretrofit.R;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    private final List<SanPham> sanPhamList;
    private final OnSanPhamActionListener actionListener;

    public SanPhamAdapter(List<SanPham> sanPhamList, OnSanPhamActionListener actionListener) {
        this.sanPhamList = sanPhamList;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = sanPhamList.get(position);

        // Kiểm tra và gán giá trị cho các TextView
        holder.txtMaSP.setText(sanPham.getMaSp() != null ? sanPham.getMaSp() : "");
        holder.txtTenSP.setText(sanPham.getTenSP() != null ? sanPham.getTenSP() : "");
        holder.txtMoTa.setText(sanPham.getMoTa() != null ? sanPham.getMoTa() : "");

        // Thiết lập sự kiện cho các nút
        holder.btnXoa.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onDeleteSanPham(sanPham);
            }
        });

        holder.btnSua.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onEditSanPham(sanPham);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaSP, txtTenSP, txtMoTa;
        Button btnXoa, btnSua;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaSP = itemView.findViewById(R.id.txtMaSP);
            txtTenSP = itemView.findViewById(R.id.txtTenSP);
            txtMoTa = itemView.findViewById(R.id.txtMoTa);
            btnXoa = itemView.findViewById(R.id.btnXoa);
            btnSua = itemView.findViewById(R.id.btnSua);
        }
    }

    public interface OnSanPhamActionListener {
        void onDeleteSanPham(SanPham sanPham);
        void onEditSanPham(SanPham sanPham);
    }
}
