module com.example.inventorycapstone {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires commons.csv;
    requires org.apache.commons.lang3;

    opens com.example.inventorycapstone to javafx.fxml;
    exports com.example.inventorycapstone;

    opens com.example.inventorycapstone.model to javafx.fxml;
    exports com.example.inventorycapstone.model;

    opens com.example.inventorycapstone.controller to javafx.fxml;
    exports com.example.inventorycapstone.controller;
    exports com.example.inventorycapstone.controller.set;
    opens com.example.inventorycapstone.controller.set to javafx.fxml;
    exports com.example.inventorycapstone.controller.course;
    opens com.example.inventorycapstone.controller.course to javafx.fxml;
}