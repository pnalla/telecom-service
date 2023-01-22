package component.com.blng.telecomservice;


import com.blng.telecomservice.api.CustomerPhoneDetailResponse;
import com.blng.telecomservice.exception.DataNotFoundException;
import com.blng.telecomservice.repository.customer.entity.Customer;
import com.blng.telecomservice.repository.phonedetails.PhoneRepository;
import com.blng.telecomservice.repository.phonedetails.entity.Phone;
import com.blng.telecomservice.service.TelecomDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ComponentTest
@Sql(scripts = {"/db/testdata/data_cleanup.sql", "/db/testdata/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config= @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
@Sql(scripts = {"/db/testdata/data_cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config= @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
public class TelecomDataServiceTest {

  @Autowired
  private TelecomDataService telecomDataService;

  @Autowired
  private PhoneRepository phoneRepository;

  public static final String CUSTOMER_ID = "12345678";
  public static final String PHONE_NUMBER = "9123456789";

  @Test
  void getAllPhoneNumbers_shouldReturnList() {
    List<String> phoneList = telecomDataService.getAllPhoneNumbers();
    assertAll("phoneList",
        () -> assertNotNull(phoneList),
        () -> assertEquals("9123456789", phoneList.get(0)),
        () -> assertEquals("9123456788", phoneList.get(1)),
        () -> assertEquals("9123456787", phoneList.get(2)),
        () -> assertEquals(9, phoneList.size())
    );
  }

  @Test
  void getPhoneDetailsForCustomer_shouldReturnSuccessResponse() {
    Customer customer = new Customer();
    customer.setCustomerId(CUSTOMER_ID);
    CustomerPhoneDetailResponse customerPhoneDetailResponse = telecomDataService.getPhoneDetailsForCustomer(customer);
    assertAll("customerPhoneDetailResponse",
        () -> assertNotNull(customerPhoneDetailResponse),
        () -> assertNotNull(customerPhoneDetailResponse.getPhoneList()),
        () -> assertEquals("9123456789", customerPhoneDetailResponse.getPhoneList().get(0).getPhoneNumber()),
        () -> assertEquals("9123456788", customerPhoneDetailResponse.getPhoneList().get(1).getPhoneNumber()),
        () -> assertEquals(CUSTOMER_ID, customerPhoneDetailResponse.getCustomerId()),
        () -> assertEquals(3, customerPhoneDetailResponse.getPhoneList().size())
    );
  }

  @Test
  void getPhoneDetailsForCustomer_noRecordsFound_shouldThrowException() {
    Customer customer = new Customer();
    customer.setCustomerId("1298109");
    DataNotFoundException dataNotFoundException = assertThrows(DataNotFoundException.class, () -> telecomDataService.getPhoneDetailsForCustomer(customer));
    assertAll("dataNotFoundException",
        () -> assertNotNull(dataNotFoundException),
        () -> assertEquals("API-404", dataNotFoundException.getApiError().getErrorId()),
        () ->  assertEquals("No data found for this customer", dataNotFoundException.getApiError().getMessage())
    );
  }

  @Test
  void activeCustomerPhoneNumber_noRecordsFound_shouldThrowException() {
    DataNotFoundException dataNotFoundException = assertThrows(DataNotFoundException.class, () -> telecomDataService.activeCustomerPhoneNumber("1720"));
    assertAll("dataNotFoundException",
        () -> assertNotNull(dataNotFoundException),
        () -> assertEquals("API-404", dataNotFoundException.getApiError().getErrorId()),
        () ->  assertEquals("No data found against this phone number", dataNotFoundException.getApiError().getMessage())
    );
  }

  @Test
  void activeCustomerPhoneNumber_update_isActiveField_true() {
    telecomDataService.activeCustomerPhoneNumber(PHONE_NUMBER);
    Phone phone = phoneRepository.findByPhoneNumber(PHONE_NUMBER);
    assertAll("phone",
        () -> assertNotNull(phone),
        () -> assertEquals(true, phone.isActive()),
        () -> assertEquals("12345678", phone.getCustomer().getCustomerId()),
        () ->  assertEquals(PHONE_NUMBER, phone.getPhoneNumber())
    );
  }

}
