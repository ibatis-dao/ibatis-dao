package fxapp01.dto;

import java.io.Serializable;
import java.math.BigInteger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProductRefs implements Serializable {

    private static final long serialVersionUID = 8030875135429404808L;
    /*
    private BigInteger id;
    private String name;
    */
    
    private IntegerProperty id;
    public BigInteger getId() { return BigInteger.valueOf(IdProperty().get());  }
    public void setId(BigInteger id) { IdProperty().set(id.intValue());    }
    public IntegerProperty IdProperty() {
        if (id == null) {
            id = new SimpleIntegerProperty(this, "id");
        }
        return id;
    }
    
    private StringProperty name;
    public String getName() { return NameProperty().get(); }
    public void setName(String name) { NameProperty().set(name); }
    public StringProperty NameProperty() {
        if (name == null) {
            name = new SimpleStringProperty(this, "name");
        }
        return name;
    }
    
    @Override
    public String toString() {
        return "Id="+IdProperty().get()+", Name="+NameProperty().get();
    }

}
