package swg.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author StarukhSA
 */
public class ProductProps {
    
    private IntegerProperty id;
    public void setId (int value) { IdProperty().set(value); }
    public int getId() { return IdProperty().get(); }
    public IntegerProperty IdProperty() {
        if (id == null) {
            id = new SimpleIntegerProperty(this, "Id");
        }
        return id;
    }

    private StringProperty Name;
    public void setName(String value) { NameProperty().set(value); }
    public String getName() { return NameProperty().get(); }
    public StringProperty NameProperty() {
        if (Name == null) {
            Name = new SimpleStringProperty(this, "Name");
        }
        return Name;
    }
}

