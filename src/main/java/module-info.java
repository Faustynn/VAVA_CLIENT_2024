module org.main.unimap_pc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports org.main.unimap_pc.client to javafx.graphics, javafx.fxml;
    exports org.main.unimap_pc.client.controllers to  javafx.fxml;

    opens org.main.unimap_pc.client to javafx.fxml;
    opens org.main.unimap_pc.client.controllers to javafx.fxml;
    opens org.main.unimap_pc.server to javafx.fxml;


}
