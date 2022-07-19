module com.example.miniaturemanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires commons.csv;
    requires org.apache.commons.lang3;

    opens com.example.miniaturemanagement to javafx.fxml;
    exports com.example.miniaturemanagement;

    opens com.example.miniaturemanagement.model to javafx.fxml;
    exports com.example.miniaturemanagement.model;

    opens com.example.miniaturemanagement.controller to javafx.fxml;
    exports com.example.miniaturemanagement.controller;
    exports com.example.miniaturemanagement.controller.set;

    opens com.example.miniaturemanagement.controller.set to javafx.fxml;
    exports com.example.miniaturemanagement.controller.course;
    opens com.example.miniaturemanagement.controller.course to javafx.fxml;
}