module com.example.game2d {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;

    opens com.example.game2d to javafx.fxml;
    exports com.example.game2d;
}