package presentation;

import java.util.Date;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import domain.*;

public class ElectricBillController  {
     private List<ElectricBill> electricBills;
     private List<Observer> observers = new ArrayList<>();
    private Stack<Command> commandHistory = new Stack<>();

    public void executeCommand(Command command) {
        command.execute();
        commandHistory.push(command);
    }

    public void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.pop();
            lastCommand.undo();
        }
    }

    public void addBill(ElectricBill bill) {
        electricBills.add(bill);
    }

    public List<ElectricBill> getElectricBills() {
        return electricBills;
    }


     public void addObserver(Observer observer) {
         observers.add(observer);
     }
 
     public void removeObserver(Observer observer) {
         observers.remove(observer);
     }
 
     public void notifyObservers(ElectricBill bill) {
         for (Observer observer : observers) {
             observer.update(bill);
         }
     }
    public ElectricBillController() {
        electricBills = new ArrayList<>();
        observers=new ArrayList<>();
    }

    public void removeBill(ElectricBill bill) {
        electricBills.remove(bill);
    }

    public void updateBill(ElectricBill oldBill, ElectricBill newBill) {
        int index = electricBills.indexOf(oldBill);
        if (index != -1) {
            electricBills.set(index, newBill);
        }
    }

    public double calculateTotalQuantityByCustomerType(String DoituongKH) {
        double totalQuantity = 0;
        for (ElectricBill bill : electricBills) {
            if (bill instanceof VietnamseCustomer) {
               VietnamseCustomer vietnameseCustomer = (VietnamseCustomer) bill;
                if (vietnameseCustomer.getMkh().equals(DoituongKH)) {
                    totalQuantity += vietnameseCustomer.getSoLuongkw();
                }
            }
        }
        return totalQuantity;
    }

    public double calculateAverageForeignCustomerTotal() {
        double total = 0;
        int foreignCustomerCount = 0;
        for (ElectricBill bill : electricBills) {
        if (bill instanceof ForeignCustomer) {
            total += bill.calculateTotal();
            foreignCustomerCount++;
        }
    }
    return foreignCustomerCount > 0 ? total / foreignCustomerCount : 0;
}

    public List<ElectricBill> getBillsInMonth(int month) {
        List<ElectricBill> billsInMonth = new ArrayList<>();
        for (ElectricBill bill : electricBills) {
            if (bill.getNgayRahd().getMonth() + 1 == month) {
                billsInMonth.add(bill);
            }
        }
        return billsInMonth;
    }

    public ElectricBill searchBill(String mKH) {
        for (ElectricBill bill : electricBills) {
            if (bill.getMkh().equals(mKH)) {
                return bill;
            }
        }
        return null;
    }

    public void deleteBill(String mKH) {
        ElectricBill billToDelete = null;
        for (ElectricBill bill : electricBills) {
            if (bill.getMkh().equals(mKH)) {
                billToDelete = bill;
                break;
            }
        }

        if (billToDelete != null) {
            electricBills.remove(billToDelete);
        }
    }

    public boolean exportBillsToFile(String filePath) {
       try {
            FileWriter writer = new FileWriter(filePath);

            for (ElectricBill bill : electricBills) {
                String billInfo = bill.toString(); 
                writer.write(billInfo + "\n");
            }

            writer.close();
            return true; 
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }
}

