package com.github.opticyclic.restdocs;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.MediaType;
import org.springframework.restdocs.ManualRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(StockAPIController.class)
//@AutoConfigureRestDocs()
@SpringBootTest
@TestExecutionListeners(MockitoTestExecutionListener.class)
public class StockAPIDocsTest extends AbstractTestNGSpringContextTests {

  private final ManualRestDocumentation restDocumentation = new ManualRestDocumentation();

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private StockService stockService;

  @BeforeMethod
  public void setUp(Method method) {
    mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    restDocumentation.beforeTest(getClass(), method.getName());
  }

  @AfterMethod
  public void tearDown() {
    restDocumentation.afterTest();
  }

  @Test
  public void createStocks() throws Exception {
    Stock stock = new Stock(2L, "IBM", new BigDecimal("128.27"));
    String payload = objectMapper.writeValueAsString(stock);
    mockMvc.perform(post("/api/v1/stocks")
                      .contentType(MediaType.APPLICATION_JSON_UTF8)
                      .content(payload))
      .andDo(print())
      .andExpect(status().isCreated())
      //      .andExpect(jsonPath("name").value("IBM"))
      .andDo(document("shouldCreateStock"));
  }

  @Test
  public void getStocks() throws Exception {
    Stock stock = new Stock(1L, "AMD", new BigDecimal("27.58"));

    Mockito.when(stockService.findById(stock.getId()))
      .thenReturn(Optional.of(stock));

    mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/stocks/{id}", 1))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andDo(document("stocks/get-by-id",
        pathParameters(parameterWithName("id")
                         .description("Numerical id of the stock")),
        responseFields(
          fieldWithPath("id")
            .description("Unique identifier of the stock."),
          fieldWithPath("name")
            .description("Stock ticker"),
          fieldWithPath("price")
            .description("Current price of the stock.")
        )
      ));
  }

}
