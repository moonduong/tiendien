package persistence;

import java.util.List;

import domain.ElectricBill;

public interface ElectricBillDAO {
    void save(ElectricBill electricBill);
    ElectricBill getByMkh(String mKH);
    List<ElectricBill> getAll();
}
