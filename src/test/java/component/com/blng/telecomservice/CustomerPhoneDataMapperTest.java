package component.com.blng.telecomservice;

import com.blng.telecomservice.TestUtil;
import com.blng.telecomservice.api.Phone;
import com.blng.telecomservice.mapper.CustomerPhoneDataMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ComponentTest
public class CustomerPhoneDataMapperTest {

  @Autowired
  private CustomerPhoneDataMapper customerPhoneDataMapper;

  @Test
  void map_whenMapPhones() {
    List<Phone> phoneList = customerPhoneDataMapper.mapPhones(TestUtil.getPhoneForCustomer());
    assertAll("phoneList",
        () -> assertNotNull(phoneList),
        () -> assertEquals("9123456789", phoneList.get(0).getPhoneNumber()),
        () -> assertEquals("9123456788", phoneList.get(1).getPhoneNumber()),
        () -> assertEquals(2, phoneList.size())
    );
  }
}
