package com.example.inventorycapstone.util;

import javafx.scene.control.TextField;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class TextFieldChecker {

    public static boolean checkValidText(TextField field){
        if(field.getText().isEmpty()){
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkValidNumber(TextField field){
        if(field.getText().isEmpty()){
            return false;
        } else if(isNumeric(field.getText())){
            return true;
        }
        return false;
    }

    public static boolean checkValidDecimal(TextField field){
        if(field.getText().isEmpty()){
            return false;
        } else {
            String string = field.getText().replace(".","0");
            if(isNumeric(string)) {
                return true;
            }
        }
        return false;
    }

}
