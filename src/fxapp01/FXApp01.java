/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxapp01;

import java.awt.Rectangle;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javax.swing.JTable;

/**
 *
 * @author serg
 */
public class FXApp01 extends Application {
    
    @FXML
    private static TableView table01;
    
    private static ProductRefsObservList dataOL;
    
    @Override
    public void start(Stage stage) throws Exception {
        ResourceBundle res = ResourceBundle.getBundle("i18n.MsgRes", Locale.getDefault());
        Parent root = FXMLLoader.load(getClass().getResource("FXApp01.fxml"), res);
        
        dataOL = new ProductRefsObservList();
        table01 = new TableView(dataOL);
        //dataOL.setBaseDataPageSize(getNumberOfVisibleRows(table));
        //table.setAutoCreateRowSorter(true); //автосортировка
        //table.setRowSorter(new TableRowSorter(tableModel)); //указанный сортировщик
        //table.setGridColor(Color.DARK_GRAY);
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //  allow for a horizontal scrollbar
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /*
    public static int getNumberOfVisibleRows(TableView table) {
        Rectangle vr = table.getVisibleRect();
        int first = table.rowAtPoint(vr.getLocation());
        vr.translate(0, vr.height);
        return table.rowAtPoint(vr.getLocation()) - first;
        //table.getPreferredScrollableViewportSize()
    }
    */
}
