module com.example.inventorycapstone {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens com.example.inventorycapstone to javafx.fxml;
    exports com.example.inventorycapstone;

    opens com.example.inventorycapstone.controller to javafx.fxml;
    exports com.example.inventorycapstone.controller;
}