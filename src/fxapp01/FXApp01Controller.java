/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxapp01;

import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.net.URL;
import java.util.ResourceBundle;
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
    private TableView table01;
    @FXML
    private TableColumn table01Column01;
    @FXML
    private TableColumn table01Column02;
    
    private ProductRefsObservList dataOL;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        log.debug("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        log.trace(">>> initialize");
        dataOL = new ProductRefsObservList();
        if (table01 == null) {
            log.debug("table01 == null");
            table01 = new TableView(dataOL);
        } else {
            log.debug("table01 != null");
            table01.setItems(dataOL);
        }
        table01.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //table01.getSortOrder();
        table01Column01.setCellValueFactory(
            //new PropertyValueFactory<ProductRefs, Integer>("id")
            new PropertyValueFactory("id")
        );
        /*
        table01Column01.setCellValueFactory(
            new Callback<CellDataFeatures<ProductRefs, Integer>, ObservableValue<Integer>>() {
             public ObservableValue<Integer> call(CellDataFeatures<ProductRefs, Integer> p) {
                 // p.getValue() returns the ProductRefs instance for a particular TableView row
                 return p.getValue().getId().intValue();
             }
            }
        );
        */
        table01Column02.setCellValueFactory(
            //new PropertyValueFactory<ProductRefs, String>("name")
            new PropertyValueFactory("name")
        );
        //getVisibleRows();
        log.trace("<<< initialize");
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
