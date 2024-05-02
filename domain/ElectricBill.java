package domain;
import java.util.Date;
public abstract class ElectricBill{
    protected String mKH;
    protected String hoten;
    protected Date ngayraHD;
    protected double soluongKW;
    protected double dongia;

    public ElectricBill(String mKH, String hoten, Date ngayraHD, double soluongKW, double dongia) {
        this.mKH = mKH;
        this.hoten = hoten;
        this.ngayraHD = ngayraHD;
        this.soluongKW = soluongKW;
        this.dongia=dongia;
    }

    public abstract double calculateTotal();

    public String getMkh(){
        return mKH;
    }

    public String getHoTen() {
        return hoten;
    }

    public Date getNgayRahd() {
        return ngayraHD;
    }

    public double getSoLuongkw() {
        return soluongKW;
    }

    public double getDonGia() {
        return dongia;
    }

    public void setMkh(String mKH) {
        this.mKH=mKH;
    }

    public abstract double getDinhmuc();

    public void setHoTen( String hoten) {
        this.hoten=hoten;
    }

    public void setNgayRahd(Date ngayraHD) {
        this.ngayraHD=ngayraHD;
    }

    public void setSoLuongkw(double soluongKW) {
        this.soluongKW=soluongKW;
    }

    public void setDinhmuc(double dinhmuc) {
    }

    public void setDonGia(double dongia) {
        this.dongia=dongia;
    }

}