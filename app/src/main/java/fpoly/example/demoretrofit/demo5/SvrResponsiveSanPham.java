package fpoly.example.demoretrofit.demo5;

import java.util.List; // Sử dụng List thay cho mảng

public class SvrResponsiveSanPham {
    private int success; // Thay đổi thành kiểu int nếu cần
    private String message;
    private List<SanPham> data; // Sử dụng List thay cho mảng

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SanPham> getData() {
        return data;
    }

    public void setData(List<SanPham> data) {
        this.data = data;
    }
}
