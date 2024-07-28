package fpoly.example.demoretrofit.demo5;

public class SanPham {
    private String MaSP,TenSP,MoTa;

    public SanPham(String maSp, String tenSP, String moTa) {
        MaSP = maSp;
        TenSP = tenSP;
        MoTa = moTa;
    }

    public SanPham() {
    }

    public String getMaSp() {
        return MaSP;
    }

    public void setMaSp(String maSp) {
        MaSP = maSp;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }
}
