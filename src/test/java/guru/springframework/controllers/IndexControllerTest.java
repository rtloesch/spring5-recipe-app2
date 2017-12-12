package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

public class IndexControllerTest {
    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    IndexController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new IndexController(recipeService);
    }

    @Test
    public void testMockMvc() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getIndexPage() throws Exception{

        //given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());

        Recipe recipe = new Recipe();
        recipe.setId(1l);
        recipes.add(recipe);

        when (recipeService.getRecipes()).thenReturn(recipes);

        ArgumentCaptor<Set<Recipe>> argCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.getIndexPage(model);



        assertEquals("index", viewName);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argCaptor.capture());
        Set<Recipe> setInController = argCaptor.getValue();
        assertEquals(2, setInController.size());

    }
}