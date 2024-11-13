package de.joergdev.mosy.showcase.drink.ordering.service.ui;

import java.util.List;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import de.joergdev.mosy.showcase.drink.ordering.service.model.DrinkOrder;

@Route("")
public class MainView extends VerticalLayout
{
  private final Button newOrderButton = new Button("New order", e -> openOrderForm(new DrinkOrder()));
  private final Button refreshOrdersButton = new Button("Refresh orders", e -> updateList(true));
  private final Grid<DrinkOrder> orderGrid = new Grid<>(DrinkOrder.class);

  private OrderForm form;

  private final Checkbox apiMockCheckbox = new Checkbox("Enable Mock API");
  private final TextField apiRecordSessionId = new TextField("Recordsession ID");
  private final VerticalLayout apiMockLayout = new VerticalLayout();
  private VerticalLayout apiMockRootLayout;

  private final OrderApiClient apiClient;

  public MainView(OrderApiClient apiClient)
  {
    this.apiClient = apiClient;

    configureOrderGrid();
    configureOrderCreationChange(apiClient);
    configureApiMockSettings();

    add(new HorizontalLayout(newOrderButton, refreshOrdersButton));
    add(orderGrid);
    add(form);
    add(apiMockRootLayout);

    updateList(true);
  }

  private void configureOrderCreationChange(OrderApiClient apiClient)
  {
    form = new OrderForm(apiClient, null);
    form.addSaveDeleteListener(this::postDeleteSave);
    form.setVisible(false);
  }

  private void configureOrderGrid()
  {
    orderGrid.setColumns("id", "drinkName", "quantity", "orderDate");

    orderGrid.asSingleSelect().addValueChangeListener(event -> {
      DrinkOrder selectedOrder = event.getValue();
      openOrderForm(selectedOrder);
    });
  }

  private void configureApiMockSettings()
  {
    Button apiMockSettingsButton = new Button("API mock settings", event -> toggleApiMockSettings());

    apiRecordSessionId.setEnabled(false);
    apiRecordSessionId.addBlurListener(e -> {
      Integer apiRecordSessionIdInt = null;

      String apiRecordSessionIdTxt = apiRecordSessionId.getValue();
      if (apiRecordSessionIdTxt != null && !apiRecordSessionIdTxt.isEmpty())
      {
        apiRecordSessionIdInt = Integer.valueOf(apiRecordSessionIdTxt);
      }

      apiClient.setRecordSessionId(apiRecordSessionIdInt);
    });

    apiMockCheckbox.addValueChangeListener(event -> {
      apiRecordSessionId.setEnabled(event.getValue());

      if (!Boolean.TRUE.equals(event.getValue()))
      {
        apiRecordSessionId.setValue("");
      }

      apiClient.setMockEnabled(event.getValue());
    });

    apiMockLayout.setVisible(false);
    apiMockLayout.add(apiMockCheckbox, apiRecordSessionId);

    apiMockRootLayout = new VerticalLayout(apiMockSettingsButton, apiMockLayout);
    add(apiMockRootLayout);
  }

  private void toggleApiMockSettings()
  {
    apiMockLayout.setVisible(!apiMockLayout.isVisible());
  }

  private void updateList(boolean showInfo)
  {
    List<DrinkOrder> orders = apiClient.getAllOrders();
    orderGrid.setItems(orders);

    if (showInfo)
    {
      Notification.show("Orders loaded");
    }
  }

  private void postDeleteSave()
  {
    updateList(false);

    form.setVisible(false);
  }

  private void openOrderForm(DrinkOrder order)
  {
    form.setOrder(order);
    form.setVisible(true);
  }
}
