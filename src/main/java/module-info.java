module gaudin.firstscreen {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;


    exports gaudin.inventoryappmain;
    exports controller;
    exports model;
    opens gaudin.inventoryappmain to javafx.fxml, javafx.base;
    opens controller to javafx.fxml, javafx.base;
    opens model to javafx.fxml, javafx.base;
}