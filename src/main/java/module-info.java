module org.main.unimap_pc {
    requires static lombok;
    requires MaterialFX;
    requires java.net.http;
    requires fontawesomefx;

    exports org.main.unimap_pc.client to javafx.graphics; // Экспортируйте пакет, содержащий MainApp
    exports org.main.unimap_pc.client.controllers to javafx.fxml; // Экспортируйте пакет контроллеров для FXMLLoader
    opens org.main.unimap_pc.client to javafx.fxml, javafx.graphics; // Откройте для рефлексии
    opens org.main.unimap_pc.client.controllers to javafx.fxml; // Откройте пакет контроллеров для рефлексии
}
