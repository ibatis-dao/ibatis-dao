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
package fxapp01;

import fxapp01.dto.TestItemDTO;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * http://docs.oracle.com/javafx/2/ui_controls/table-view.htm
 * http://docs.oracle.com/javase/8/javafx/user-interface-tutorial/table-view.htm
 * http://docs.oracle.com/javafx/2/api/javafx/scene/control/TableView.html
 * http://download.java.net/jdk8/jfxdocs/javafx/scene/control/TableView.html
 * @author serg
 */
public class FXApp01Controller implements Initializable {
    
    private static final ILogger log = LogMgr.getLogger(FXApp01.class);

    @FXML
    private Label label;
    @FXML
    private TableView<TestItemDTO> table01;
    @FXML
    private TableColumn<TestItemDTO,Integer> table01Column01;
    @FXML
    private TableColumn<TestItemDTO,String> table01Column02;
    
    private TestItemObservList dataOL;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        log.debug("You clicked me!");
        label.setText("Hello World!");
        dataOL.debugPrintAll();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            log.trace(">>> initialize");
            dataOL = new TestItemObservList();
            if (table01 == null) {
                log.debug("table01 == null");
                table01 = new TableView<>(dataOL);
            } else {
                log.debug("table01 != null");
                table01.setItems(dataOL);
            }
            table01.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            //ObservableList<TableColumn<TestItemDTO,?>> so = table01.getSortOrder();
            // http://stackoverflow.com/questions/25509031/javafx-tableview-sort-policy
            table01Column01.setCellValueFactory(//new PropertyValueFactory<ProductRefs, Integer>("id")
                    new PropertyValueFactory<TestItemDTO,Integer>("id")
            );
            table01Column02.setCellValueFactory(//new PropertyValueFactory<ProductRefs, String>("name")
                    new PropertyValueFactory<TestItemDTO,String>("name")
            );
            //getVisibleRows();
            log.trace("<<< initialize");
        } catch (IOException | IntrospectionException ex) {
            log.error(null, ex);
        }
    }
    
    /*
    protected IntRange getVisibleRows01() {
        //http://stackoverflow.com/questions/26159224/tableview-visible-rows
        log.trace(">>> getVisibleRows");
        TableViewSkin<?> tableSkin = (TableViewSkin<?>) table01.getSkin();
        VirtualFlow virtualFlow = (VirtualFlow) tableSkin.getChildren().get(1); //java.lang.NullPointerException
        int first = virtualFlow.getFirstVisibleCell().getIndex();
        int last = virtualFlow.getLastVisibleCell().getIndex();
        log.debug("FirstRow="+first+", LastRow="+last);
        IntRange r = new IntRange(first, last-first-1);
        log.trace("<<< getVisibleRows");
        return r;
    }
    
    protected IntRange getVisibleRows02() {
        //http://stackoverflow.com/questions/26159224/tableview-visible-rows
        log.trace(">>> getVisibleRows");
        int first = 0;
        int last = 50;
        log.debug("FirstRow="+first+", LastRow="+last);
        IntRange r = new IntRange(first, last-first-1);
        log.trace("<<< getVisibleRows");
        return r;
    }
    */
}
