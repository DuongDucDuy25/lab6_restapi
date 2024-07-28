package fpoly.example.demoretrofit.demo5;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InterfaceInsertSanPham {
    @POST("inser2.php")
    @FormUrlEncoded
    Call<SvrResponsiveSanPham> insertSanPham(
            @Field("MaSP") String maSP,
            @Field("TenSP") String tenSP,
            @Field("MoTa") String moTa
    );

    @GET("hienthi.php")
    Call<SvrResponsiveSanPham> hienThiSanPham();

    @POST("update.php")
    @FormUrlEncoded
    Call<SvrResponsiveSanPham> updateSanPham(
            @Field("MaSP") String maSP,
            @Field("TenSP") String tenSP,
            @Field("MoTa") String moTa
    );

    @HTTP(method = "DELETE", path = "delete.php", hasBody = true)
    @FormUrlEncoded
    Call<SvrResponsiveSanPham> deleteSanPham(@Field("MaSP") String maSP);
}
