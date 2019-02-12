package com.project.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ecommerce.EcommerceApplication;
import com.project.ecommerce.model.Category;
import com.project.ecommerce.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {EcommerceApplication.class})
public class CategoryControllerTest {
  @Autowired
  MockMvc mockMvc;

  @MockBean
  private CategoryService categoryService;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  public void saveCategory() throws Exception {
    Category category = new Category();
    category.setName("cat1");
    category.setDescription("category 1 description");
    String incomingCategoryJson = objectMapper.writeValueAsString(category);

    when(categoryService.createCategory(category)).thenReturn(category);
    mockMvc.perform(
        post("/categories")
            .content(incomingCategoryJson)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
    ).andExpect(status().isOk());

  }
}