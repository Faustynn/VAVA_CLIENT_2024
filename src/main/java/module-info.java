module org.main.unimap_pc {
    requires static lombok;
    requires MaterialFX;
    requires java.net.http;
    requires fontawesomefx;
    requires com.fasterxml.jackson.databind;

    exports org.main.unimap_pc.client.models; // Экспорт пакет моделей
    exports org.main.unimap_pc.client to javafx.graphics; // Экспорт  MainApp
    exports org.main.unimap_pc.client.controllers to javafx.fxml; // Экспорт контроллеров для FXMLLoader
    opens org.main.unimap_pc.client to javafx.fxml, javafx.graphics; // рефлексия
    opens org.main.unimap_pc.client.controllers to javafx.fxml; // пакет контроллеров для рефлексии
}
