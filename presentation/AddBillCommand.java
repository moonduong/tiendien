package presentation;

import domain.VietnamseCustomer;

public class AddBillCommand implements Command{
    private ElectricBillController controller;
    private VietnamseCustomer customer;

    public AddBillCommand(ElectricBillController controller, VietnamseCustomer customer) {
        this.controller = controller;
        this.customer = customer;
    }

    @Override
    public void execute() {
        controller.addBill(customer);
    }

    @Override
    public void undo() {
        controller.removeBill(customer);
    }
}

