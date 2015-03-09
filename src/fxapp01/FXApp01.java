/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxapp01;

import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author serg
 */
public class FXApp01 extends Application {
    
    private static final ILogger log = LogMgr.getLogger(FXApp01.class);
    
    @Override
    public void start(Stage stage) throws Exception {
        log.trace(">>> start");
        ResourceBundle res = ResourceBundle.getBundle("i18n.MsgRes", Locale.getDefault());
        Parent root = FXMLLoader.load(getClass().getResource("FXApp01.fxml"), res);
        
        //dataOL.setBaseDataPageSize(getNumberOfVisibleRows(table));
        //table.setAutoCreateRowSorter(true); //автосортировка
        //table.setRowSorter(new TableRowSorter(tableModel)); //указанный сортировщик
        //table.setGridColor(Color.DARK_GRAY);
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //  allow for a horizontal scrollbar
        
        Scene scene = new Scene(root);
        stage.setTitle(res.getString("App.Title"));
        stage.setScene(scene);
        stage.show();
        log.trace("<<< start");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        log.trace(">>> main");
        launch(args);
        log.trace("<<< main");
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
