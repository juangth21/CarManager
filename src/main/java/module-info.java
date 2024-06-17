module com.github.juan {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;

    opens com.github.juan to javafx.fxml;
    exports com.github.juan;
    exports com.github.juan.view;
    opens com.github.juan.view to javafx.fxml;
    opens com.github.juan.Model.connection to java.xml.bind;
    opens com.github.juan.Model.entity to javafx.base;

}
