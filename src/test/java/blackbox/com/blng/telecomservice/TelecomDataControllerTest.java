package blackbox.com.blng.telecomservice;

import com.blng.telecomservice.TelecomServiceApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

@ExtendWith({SpringExtension.class})
@SpringBootTest(classes = TelecomServiceApplication.class)
@ActiveProfiles("test")
public class TelecomDataControllerTest {
  private static final String BASE_URI = "http://localhost";
  private static final String BASE_PATH = "/telecom";
  private static final Integer PORT = 8090;
  private static final String GET_PHONES = "/v1/phones";
  private static final String GET_CUSTOMER_PHONES = "/v1/customer/12345678/phones";
  private static final String ACTIVATE_PHONE = "/v1/phone/9123456789/active";

  @BeforeAll
  static void setUp() {
    RestAssured.baseURI = BASE_URI;
    RestAssured.basePath = BASE_PATH;
    RestAssured.port = PORT;
  }

  @Test
  @Sql(scripts = {"/db/testdata/data_cleanup.sql", "/db/testdata/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config= @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
  @Sql(scripts = {"/db/testdata/data_cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config= @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
  void givenValidRequest_getAllPhones() {
    Map<String, String> headers = new HashMap<>();
    headers.put("X_CORRELATION_ID","12345678");

    given()
            .log().all()
            .headers(headers)
            .accept("application/json")
            .when()
            .get(GET_PHONES)
            .then()
            .log().all()
            .statusCode(SC_OK)
            .contentType(ContentType.JSON)
            .body(not(empty()));
  }

  @Test
  @Sql(scripts = {"/db/testdata/data_cleanup.sql", "/db/testdata/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config= @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
  @Sql(scripts = {"/db/testdata/data_cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config= @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
  void givenValidRequest_getAllPhonesForCustomer() {
    Map<String, String> headers = new HashMap<>();
    headers.put("X_CORRELATION_ID","12345678");

    given()
            .log().all()
            .headers(headers)
            .accept("application/json")
            .when()
            .get(GET_CUSTOMER_PHONES.trim())
            .then()
            .log().all()
            .statusCode(SC_OK)
            .contentType(ContentType.JSON)
            .body(not(empty()));
  }

  @Test
  @Sql(scripts = {"/db/testdata/data_cleanup.sql", "/db/testdata/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config= @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
  @Sql(scripts = {"/db/testdata/data_cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config= @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
  void givenValidRequest_getAllTheAccountForTheCustomer() {
    Map<String, String> headers = new HashMap<>();
    headers.put("X_CORRELATION_ID","12345678");

    given()
            .log().all()
            .headers(headers)
            .accept("application/json")
            .when()
            .put(ACTIVATE_PHONE.trim())
            .then()
            .log().all()
            .statusCode(SC_OK);
  }
}
