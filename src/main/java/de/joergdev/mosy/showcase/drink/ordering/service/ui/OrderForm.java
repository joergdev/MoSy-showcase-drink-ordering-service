package de.joergdev.mosy.showcase.drink.ordering.service.ui;

import java.util.ArrayList;
import java.util.List;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import de.joergdev.mosy.shared.Utils;
import de.joergdev.mosy.showcase.drink.ordering.service.model.DrinkOrder;

public class OrderForm extends FormLayout
{
  private final OrderApiClient apiClient;
  private DrinkOrder order;

  private final TextField drinkName = new TextField("Drink Name");
  private final TextField quantity = new TextField("Quantity");
  private final DatePicker orderDate = new DatePicker("Order Date");
  private final Button save = new Button("Save");
  private final Button delete = new Button("Delete");

  private final List<Runnable> saveDeleteListeners = new ArrayList<>();

  public OrderForm(OrderApiClient apiClient, DrinkOrder order)
  {
    this.apiClient = apiClient;
    this.order = order;

    // Set up form fields
    drinkName.setPlaceholder("Enter drink name");
    quantity.setPlaceholder("Enter quantity");
    orderDate.setPlaceholder("Pick a date");

    // Set button styles
    save.addClassName("save-button");
    delete.addClassName("delete-button");

    // Button layout
    HorizontalLayout buttonLayout = new HorizontalLayout(save, delete);
    buttonLayout.setSpacing(true);
    buttonLayout.setPadding(true);
    buttonLayout.setJustifyContentMode(JustifyContentMode.END);

    // Add components to form layout
    add(drinkName, quantity, orderDate, buttonLayout);

    // Event listeners
    save.addClickListener(e -> saveOrder());
    delete.addClickListener(e -> deleteOrder());
  }

  public void setOrder(DrinkOrder order)
  {
    if (order == null)
    {
      order = new DrinkOrder();
    }

    this.order = order;

    drinkName.setValue(Utils.nvl(order.getDrinkName()));
    quantity.setValue(String.valueOf(order.getQuantity()));
    orderDate.setValue(order.getOrderDate());

    delete.setEnabled(order.getId() != null);
  }

  private void saveOrder()
  {
    order.setDrinkName(drinkName.getValue());
    order.setQuantity(Integer.parseInt(quantity.getValue()));
    order.setOrderDate(orderDate.getValue());

    apiClient.saveOrder(order);
    Notification.show("Order saved");

    saveDeleteListeners.forEach(Runnable::run);
  }

  private void deleteOrder()
  {
    apiClient.deleteOrder(order.getId());
    Notification.show("Order deleted");

    saveDeleteListeners.forEach(Runnable::run);
  }

  public void addSaveDeleteListener(Runnable listener)
  {
    saveDeleteListeners.add(listener);
  }
}
