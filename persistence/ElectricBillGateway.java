package persistence;

import java.util.List;

import domain.ElectricBill;

public interface ElectricBillGateway {
    void saveElectricBill(ElectricBill electricBill);
    ElectricBill getElectricBillByMkh(String mKH);
    List<ElectricBill> getAllElectricBills();
}
