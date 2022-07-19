package com.example.miniaturemanagement.controller.set;

import com.example.miniaturemanagement.controller.MainController;
import com.example.miniaturemanagement.doa.model.SetDAO;
import com.example.miniaturemanagement.model.Inventory;
import com.example.miniaturemanagement.model.OfficialSet;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.math.BigDecimal;

import static com.example.miniaturemanagement.util.TextFieldChecker.*;
import static com.example.miniaturemanagement.util.TextFieldChecker.checkValidNumber;

public class OfficialSetController extends SetController {

    public TextField setName;
    public TextField setInventoryId;
    public TextField setOverStock;
    public TextField setLowStock;
    public TextField setStock;
    public TextField setRetail;

    public TextField setWholesale;
    public TextField setSupplier;
    public TextField setBrand;

    protected OfficialSet activeSet;

    public void initialize(){
        this.activeSet = (OfficialSet) MainController.getActiveSet();
        if (activeSet != null) {
            fillSetFields();
            disableEdit();
        } else {
            enableEdit();
        }
        MainController.clearActiveSet();
    }

    @Override
    public void fillSetFields() {

        setName.setText(activeSet.getName());
        setInventoryId.setText(String.valueOf(activeSet.getId()));
        setBrand.setText(activeSet.getBrand());
        setSupplier.setText(activeSet.getSupplier());
        setWholesale.setText(String.valueOf(activeSet.getWholeSalePrice()));
        setRetail.setText(String.valueOf(activeSet.getRetailMarkup()));
        setStock.setText(String.valueOf(activeSet.getCurrentStock()));
        setLowStock.setText(String.valueOf(activeSet.getLowStockAmount()));
        setOverStock.setText(String.valueOf(activeSet.getOverStockAmount()));

    }

    protected void saveSet() {

        if(checkAllFields()) {
            if (activeSet == null) {
                activeSet = new OfficialSet();
            }

            activeSet.setName(setName.getText());
            activeSet.setBrand(setBrand.getText());
            activeSet.setSupplier(setSupplier.getText());
            activeSet.setWholeSalePrice(new BigDecimal(setWholesale.getText()));
            activeSet.setRetailMarkup(new BigDecimal(setRetail.getText()));
            activeSet.setCurrentStock(Integer.parseInt(setStock.getText()));
            activeSet.setLowStockAmount(Integer.parseInt(setLowStock.getText()));
            activeSet.setOverStockAmount(Integer.parseInt(setOverStock.getText()));

            if (activeSet.getId() > 0) {
                activeSet.setId(Integer.valueOf(setInventoryId.getText()));
                Inventory.updateSet(activeSet);
                SetDAO.update(activeSet);
            } else {
                activeSet.setId(SetDAO.add(activeSet));
                Inventory.addSet(activeSet);
            }
            disableEdit();
        } else {
            Alert invalidInputAlert = new Alert(Alert.AlertType.ERROR,"Please verify the inserted data.");
            invalidInputAlert.show();
        }
    }

    private boolean checkAllFields() {
        return  checkValidText(setName) &&
                checkValidText(setBrand) &&
                checkValidText(setSupplier) &&
                checkValidDecimal(setWholesale) &&
                checkValidDecimal(setRetail) &&
                checkValidNumber(setStock) &&
                checkValidNumber(setLowStock) &&
                checkValidNumber(setOverStock);
    }

    @Override
    protected void deleteSet() {
        Inventory.deleteSet(activeSet);
        SetDAO.delete(activeSet);
        detailsGrid.getChildren().clear();
    }

}
