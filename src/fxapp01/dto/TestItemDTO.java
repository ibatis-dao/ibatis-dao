/*
 * Copyright 2015 serg.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fxapp01.dto;

import java.io.Serializable;
import java.math.BigInteger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TestItemDTO implements Serializable {

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
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
        //если параметр = null, то он не может быть равен текущему экземпляру
            return false;
        } else {
            //если тип входного параметра нельзя присвоить текущему типу, 
            //то их нельзя сравнить. он не может быть равен текущему экземпляру
            if (! o.getClass().isAssignableFrom(this.getClass())) {
                return false;
            } else {
                TestItemDTO p = (TestItemDTO)o;
                return (
                    (IdProperty().get() == p.IdProperty().get()) &&
                    (NameProperty().get().equals(p.NameProperty().get()))
                );
            }
        }
    }

}
