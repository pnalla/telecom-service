package component.com.blng.telecomservice;


import com.blng.telecomservice.TestUtil;
import com.blng.telecomservice.api.CustomerPhoneDetailResponse;
import com.blng.telecomservice.service.TelecomDataService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentTest
@AutoConfigureMockMvc
@Sql(scripts = {"/db/testdata/data_cleanup.sql", "/db/testdata/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config= @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
@Sql(scripts = {"/db/testdata/data_cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config= @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
public class TelecomDataControllerTest {
  private static final String GET_PHONES = "/v1/phones";
  private static final String GET_CUSTOMER_PHONES = "/v1/customer/12345678/phones";
  private static final String ACTIVATE_PHONE = "/v1/phone/9123456789/active";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TelecomDataService telecomDataService;

  @Test
  void getAllPhoneNumber_shouldReturnList() throws Exception {
    Mockito.when(telecomDataService.getAllPhoneNumbers()).thenReturn(TestUtil.getAllPhoneNumbers());
    mockMvc.perform(get(GET_PHONES)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .headers(new HttpHeaders()))
            .andExpect(status().is2xxSuccessful());
  }

  @Test
  void getPhoneForCustomer_shouldReturnSuccess() throws Exception {
    Mockito.when(telecomDataService.getPhoneDetailsForCustomer(any())).thenReturn(CustomerPhoneDetailResponse.builder()
            .phoneList(TestUtil.getPhoneApiList()).build());
    mockMvc.perform(get(GET_CUSTOMER_PHONES)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .headers(new HttpHeaders()))
            .andExpect(status().is2xxSuccessful());
  }

  @Test
  void activatePhoneNumber_shouldReturnSuccess() throws Exception {
    Mockito.doNothing().when(telecomDataService).activeCustomerPhoneNumber(any());
    mockMvc.perform(put(ACTIVATE_PHONE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .headers(new HttpHeaders()))
            .andExpect(status().is2xxSuccessful());
  }

}
