package unit.com.blng.telecomservice;

import com.blng.telecomservice.TestUtil;
import com.blng.telecomservice.api.CustomerPhoneDetailResponse;
import com.blng.telecomservice.exception.DataNotFoundException;
import com.blng.telecomservice.mapper.CustomerPhoneDataMapper;
import com.blng.telecomservice.repository.customer.entity.Customer;
import com.blng.telecomservice.repository.phonedetails.PhoneRepository;
import com.blng.telecomservice.repository.phonedetails.entity.Phone;
import com.blng.telecomservice.service.TelecomDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TelecomDataServiceTest {

  public static final String CUSTOMER_ID = "12345678";
  public static final String PHONE_NUMBER = "9123456789";

  @InjectMocks
  private TelecomDataService telecomDataService;

  @Mock
  private PhoneRepository phoneRepository;

  @Mock
  private CustomerPhoneDataMapper customerPhoneDataMapper;

  @BeforeEach
  void initialize() {
    ReflectionTestUtils.setField(telecomDataService, "pageSize", 5);
  }


  @Test
  void getAllPhoneNumbers_shouldReturnList() {
    PageRequest pageRequest = PageRequest.of(0, 5);
    when(phoneRepository.getAllPhoneNumbers(pageRequest)).thenReturn(new SliceImpl<>(TestUtil.getAllPhoneNumbers(), pageRequest, false));
    List<String> phoneList = telecomDataService.getAllPhoneNumbers();
    assertAll("phoneList",
        () -> assertNotNull(phoneList),
        () -> assertEquals("9123456789", phoneList.get(0)),
        () -> assertEquals("9123456788", phoneList.get(1)),
        () -> assertEquals("9123456780", phoneList.get(2)),
        () -> assertEquals(3, phoneList.size())
    );
  }

  @Test
  void getAllPhoneNumbers_noRecordsFound_shouldThrowException() {
    PageRequest pageRequest = PageRequest.of(0, 50);
    when(phoneRepository.getAllPhoneNumbers(pageRequest)).thenReturn(new SliceImpl<>(Collections.emptyList(), pageRequest, false));
    DataNotFoundException dataNotFoundException = assertThrows(DataNotFoundException.class, () -> telecomDataService.getAllPhoneNumbers());
    assertAll("dataNotFoundException",
        () -> assertNotNull(dataNotFoundException),
        () -> assertEquals("API-404", dataNotFoundException.getApiError().getErrorId()),
        () ->  assertEquals("No data found", dataNotFoundException.getApiError().getMessage())
    );
  }

  @Test
  void getPhoneDetailsForCustomer_shouldReturnSuccessResponse() {
    Customer customer = new Customer();
    customer.setCustomerId(CUSTOMER_ID);
    when(phoneRepository.findByCustomer(customer)).thenReturn(TestUtil.getPhoneForCustomer());
    when(customerPhoneDataMapper.mapPhones(any())).thenReturn(TestUtil.getPhoneApiList());
    CustomerPhoneDetailResponse customerPhoneDetailResponse = telecomDataService.getPhoneDetailsForCustomer(customer);
    assertAll("customerPhoneDetailResponse",
        () -> assertNotNull(customerPhoneDetailResponse),
        () -> assertNotNull(customerPhoneDetailResponse.getPhoneList()),
        () -> assertEquals("9123456789", customerPhoneDetailResponse.getPhoneList().get(0).getPhoneNumber()),
        () -> assertEquals("9123456788", customerPhoneDetailResponse.getPhoneList().get(1).getPhoneNumber()),
        () -> assertEquals(CUSTOMER_ID, customerPhoneDetailResponse.getCustomerId()),
        () -> assertEquals(2, customerPhoneDetailResponse.getPhoneList().size())
    );
  }

  @Test
  void getPhoneDetailsForCustomer_noRecordsFound_shouldThrowException() {
    Customer customer = new Customer();
    customer.setCustomerId(CUSTOMER_ID);
    when(phoneRepository.findByCustomer(customer)).thenReturn(Collections.emptyList());
    DataNotFoundException dataNotFoundException = assertThrows(DataNotFoundException.class, () -> telecomDataService.getPhoneDetailsForCustomer(customer));
    assertAll("dataNotFoundException",
        () -> assertNotNull(dataNotFoundException),
        () -> assertEquals("API-404", dataNotFoundException.getApiError().getErrorId()),
        () ->  assertEquals("No data found for this customer", dataNotFoundException.getApiError().getMessage())
    );
  }

  @Test
  void activeCustomerPhoneNumber_noRecordsFound_shouldThrowException() {
    when(phoneRepository.findByPhoneNumber(PHONE_NUMBER)).thenReturn(null);
    DataNotFoundException dataNotFoundException = assertThrows(DataNotFoundException.class, () -> telecomDataService.activeCustomerPhoneNumber(PHONE_NUMBER));
    assertAll("dataNotFoundException",
        () -> assertNotNull(dataNotFoundException),
        () -> assertEquals("API-404", dataNotFoundException.getApiError().getErrorId()),
        () ->  assertEquals("No data found against this phone number", dataNotFoundException.getApiError().getMessage())
    );
  }

  @Test
  void activeCustomerPhoneNumber_update_isActiveField_true() {
    when(phoneRepository.findByPhoneNumber(PHONE_NUMBER)).thenReturn(Phone.builder().phoneNumber(PHONE_NUMBER).isActive(true).phoneType("work").build());
    telecomDataService.activeCustomerPhoneNumber(PHONE_NUMBER);
    ArgumentCaptor<Phone> phoneArgumentCaptor = ArgumentCaptor.forClass(Phone.class);
    verify(phoneRepository, times((1))).save(phoneArgumentCaptor.capture());

    Phone phone = phoneArgumentCaptor.getValue();
    assertAll("phone",
        () -> assertNotNull(phone),
        () -> assertEquals(true, phone.isActive()),
        () ->  assertEquals(PHONE_NUMBER, phone.getPhoneNumber())
    );
  }
}
