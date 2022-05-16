module com.esstu.dymbrylov {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;
    requires jasperreports;
    opens com.esstu.dymbrylov to javafx.fxml;
    exports com.esstu.dymbrylov;
    exports com.esstu.dymbrylov.controllers;
    opens com.esstu.dymbrylov.controllers to javafx.fxml;

}