module com.example.pp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.pp to javafx.fxml;
    exports com.example.pp;
}