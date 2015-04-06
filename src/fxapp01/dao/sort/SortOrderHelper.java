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
package fxapp01.dao.sort;

import java.util.Iterator;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;


public class SortOrderHelper<DTOclass,T extends Object> extends SortOrder {

    public SortOrderHelper(ObservableList<TableColumn<DTOclass,T>> cols) {
        super();
        if (cols != null) {
            Iterator<TableColumn<DTOclass,T>> it = cols.iterator();
            while (it.hasNext()) {
                TableColumn<DTOclass,T> col = it.next();
                ISortOrder.Direction dir = (col.getSortType() == TableColumn.SortType.ASCENDING) ? ISortOrder.Direction.ASC : ISortOrder.Direction.DESC;
                //TODO имя колонки таблицы не совпадает с именем столбца в источнике данных
                String colID;
                Callback<TableColumn.CellDataFeatures<DTOclass,T>, ObservableValue<T>> clb = col.getCellValueFactory();
                if (clb instanceof PropertyValueFactory) {
                    PropertyValueFactory<DTOclass,T> pvf = (PropertyValueFactory<DTOclass,T>)clb;
                    colID = pvf.getProperty();
                } else {
                    colID = col.getId();
                }
                log.debug(colID+"="+dir);
                add(colID, dir);
            }
        }
    }
    
}
