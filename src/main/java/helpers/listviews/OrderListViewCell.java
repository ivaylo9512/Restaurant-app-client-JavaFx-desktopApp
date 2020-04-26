package helpers.listviews;

import animations.ExpandOrderPane;
import models.Order;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import controllers.firststyle.LoggedFirst;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import static application.RestaurantApplication.stageManager;

public class OrderListViewCell extends ListCell<Order> {
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @FXML
    private Label orderId, updatedDate, createdDate, createdTime, updatedTime;

    @FXML
    private AnchorPane orderPane;

    @FXML
    private Pane container, updatedContainer;

    @FXML
    private AnchorPane dishesAnchor, createdContainer;

    @FXML
    private ScrollPane dishesScroll;

    @FXML
    private VBox dishesBox;
    @FXML
    private Button button;

    private FXMLLoader mLLoader;

    public Order order;

    @Override
    protected void updateItem(Order order, boolean empty) {
        super.updateItem(order, empty);
        this.order = order;

        if(empty || order == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/FXML/cells/order-cell.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            orderPane.setId(String.valueOf(order.getId().get()));

            orderId.setText(String.valueOf(order.getId().get()));

            container.setClip(new Rectangle(container.getPrefWidth(), container.getPrefHeight()));

            setText(null);
            setGraphic(container);

            orderPane.setOpacity(1);
            if(ExpandOrderPane.currentOrder == order){
                ExpandOrderPane.currentPane = orderPane;
                ExpandOrderPane.currentContainer = container;
                ExpandOrderPane.cell = this;

                orderPane.setOpacity(0);
                if(order.getIndex() != getIndex()){
                    order.setIndex(getIndex());
                    ((LoggedFirst)stageManager.currentController).updateListScroll();
                }
            }
            order.setIndex(getIndex());
        }

    }


}