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

import fxapp01.dao.sort.SortOrderHelper;
import fxapp01.dto.ISortOrder;
import fxapp01.dto.TestItemDTO;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SortEvent;
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
        try {
            //com.sun.javafx.runtime.VersionInfo.getRuntimeVersion();
            log.debug("javafx.runtime.version: " + System.getProperties().get("javafx.runtime.version"));
        } catch (Exception e) { //java.security.AccessControlException
            log.error("Error getting javafx.runtime.version", e);
        }
        label.setText("Hello World!");
        dataOL.debugPrintAll();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            log.trace(">>> initialize");
            /* 
            https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/table-view.htm
            https://docs.oracle.com/javafx/2/api/javafx/scene/control/TableView.html
            http://code.makery.ch/library/javafx-2-tutorial/
            http://stackoverflow.com/questions/17268529/javafx-tableview-keep-selected-row-in-current-view
            
            local sorting and filtering 
            http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
            */
            dataOL = new TestItemObservList();
            if (table01 == null) {
                log.debug("table01 == null");
                table01 = new TableView<>(dataOL);
            } else {
                log.debug("table01 != null");
                table01.setItems(dataOL);
            }
            table01.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            //table01.setEditable(true);
            //table01.getSortOrder().add(table01Column01);
            //table01Column02.setSortType(TableColumn.SortType.DESCENDING);
            
            table01.setOnSort(
                new EventHandler<SortEvent<TableView<TestItemDTO>>>() {

                @Override
                public void handle(SortEvent<TableView<TestItemDTO>> event) {
                    log.debug("******SortEvent start******"+event.getEventType().getName()+", "+event.getSource().getClass().getName());
                    dataOL.setSortOrder(new SortOrderHelper(table01.getSortOrder()));
                    log.debug("******SortEvent finish******");
                }
                }
            );
            
            //ObservableList<TableColumn<TestItemDTO,?>> so = table01.getSortOrder();
            // http://stackoverflow.com/questions/25509031/javafx-tableview-sort-policy
            // http://www.tagwith.com/question_968112_javafx-editable-tableview-without-javafx-style-properties
            table01Column01.setCellValueFactory(//new PropertyValueFactory<ProductRefs, Integer>("id")
                    new PropertyValueFactory<>("id")
            );
            table01Column02.setCellValueFactory(//new PropertyValueFactory<ProductRefs, String>("name")
                    new PropertyValueFactory<>("name")
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
