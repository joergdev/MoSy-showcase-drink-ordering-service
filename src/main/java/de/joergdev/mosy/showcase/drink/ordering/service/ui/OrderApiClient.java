package de.joergdev.mosy.showcase.drink.ordering.service.ui;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import de.joergdev.mosy.api.APIConstants;
import de.joergdev.mosy.showcase.drink.ordering.service.model.DrinkOrder;

@Service
public class OrderApiClient
{
  private final WebClient webClient;

  @Value("${api.url}")
  private String apiUrl;

  @Value("${api.url.mock}")
  private String apiUrlMock;

  @Value("${api.mock.tenantId}")
  private String tenantId;

  private boolean mockEnabled;
  private Integer recordSessionId;

  public OrderApiClient(WebClient.Builder webClientBuilder)
  {
    webClient = webClientBuilder.build();
  }

  public List<DrinkOrder> getAllOrders()
  {
    WebClient.RequestHeadersSpec<?> request = webClient.get().uri(getApiUrl());

    if (mockEnabled && recordSessionId != null)
    {
      request = request.header(APIConstants.HTTP_HEADER_RECORD_SESSION_ID, String.valueOf(recordSessionId));

      if (tenantId != null && !tenantId.trim().isEmpty())
      {
        request = request.header(APIConstants.HTTP_HEADER_TENANT_ID, tenantId.trim());
      }
    }

    return request.retrieve().bodyToFlux(DrinkOrder.class).collectList().block();
  }

  public DrinkOrder getOrderById(Long id)
  {
    WebClient.RequestHeadersSpec<?> request = webClient.get().uri(getApiUrl() + "/" + id);

    return request.retrieve().bodyToFlux(DrinkOrder.class).single().block();
  }

  public void saveOrder(DrinkOrder order)
  {
    webClient.post().uri(getApiUrl()).bodyValue(order).retrieve().bodyToMono(Void.class).block();
  }

  public void deleteOrder(Long id)
  {
    webClient.delete().uri(getApiUrl() + "/" + id).retrieve().bodyToMono(Void.class).block();
  }

  private String getApiUrl()
  {
    return mockEnabled ? apiUrlMock : apiUrl;
  }

  public Integer getRecordSessionId()
  {
    return recordSessionId;
  }

  public void setRecordSessionId(Integer recordSessionId)
  {
    this.recordSessionId = recordSessionId;
  }

  public String getApiUrlMock()
  {
    return apiUrlMock;
  }

  public void setApiUrlMock(String apiUrlMock)
  {
    this.apiUrlMock = apiUrlMock;
  }

  public boolean isMockEnabled()
  {
    return mockEnabled;
  }

  public void setMockEnabled(boolean mockEnabled)
  {
    this.mockEnabled = mockEnabled;
  }
}
