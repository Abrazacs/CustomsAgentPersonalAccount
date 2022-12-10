package ru.ssemenov;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.ssemenov.dtos.CustomsDeclarationRequest;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class CustomsDeclarationControllerImplTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getAllCustomsDeclarationByVatCodeTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/declarations")
                        .header("vatCode", "7777777777")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items[0].vatCode", is("7777777777")));
    }

    @Test
    public void getAllCustomsDeclarationByVatCodeMissingHeaderTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/declarations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("Required request header 'vatCode'")));
    }

    @Test
    public void getCustomsDeclarationByIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/declarations/{id}", "684e4ea4-cac9-4b33-843e-d26274ff9f7e")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invoiceData", is("ab-234")))
                .andExpect(jsonPath("$.number", is("10226010/220211/0003344")));
    }

    @Test
    public void getCustomsDeclarationByIdNotFoundTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/declarations/{id}", "1234")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addNewCustomsDeclarationTest() throws Exception {
        CustomsDeclarationRequest declarationRequest = CustomsDeclarationRequest.builder()
                .vatCode("7777777777")
                .goodsValue(BigDecimal.valueOf(12345))
                .invoiceData("bs-142")
                .consignor("Super trader")
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/declarations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(declarationRequest))
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void addNewCustomsDeclarationErrorValidationTest() throws Exception {
        CustomsDeclarationRequest declarationRequest = CustomsDeclarationRequest.builder()
                .vatCode("7777777777")
                .invoiceData("sv-124")
                .goodsValue(BigDecimal.valueOf(12345))
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/declarations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(declarationRequest))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].fieldName", is("consignor")))
                .andExpect(jsonPath("$.violations[0].message", is("must not be blank")));
    }

    @Test
    public void deleteCustomsDeclarationByIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/declarations/{id}", "ca26b177-dfdb-40c5-beaf-1e1672111e52")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCustomsDeclarationByIdNotFoundTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/declarations/{id}", "1234")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
