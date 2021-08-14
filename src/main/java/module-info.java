module com.example.solution {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.solution to javafx.fxml;
    exports com.example.solution;
}