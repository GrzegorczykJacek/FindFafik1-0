package com.jg.FindFafik.web;

import com.jg.FindFafik.persistence.dao.AdvertisementRepository;
import com.jg.FindFafik.persistence.dao.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
class MainControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AdvertisementRepository advertisementRepository;
    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
mockMvc = MockMvcBuilders.standaloneSetup(advertisementRepository).build();
    }

    @Test
    public void name() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/advertisement")
        ).andExpect(MockMvcResultMatchers.status().isOk());
//        .andExpect(content().string("Zaginął"));
    }



    @Test
    void findByIdAdvertisement() {


   }

    @Test
    void retrieveUserDetails() {
        //given
        //when
        //then
    }

    @Test
    void advertisement() {
        //given
        //when
        //then
    }

   @Test
    void index() {
        //given

        //when
        //then
    }

    @Test
    void logout() {
        //given
        //when
        //then
    }

    @Test
    void userForm() {
        //given
        //when
        //then
    }

    @Test
    void userSubmit() {
        //given
        //when
        //then
    }

    @Test
    void advertisememntForm() {
    }

    @Test
    void advertisementSubmit() {
    }

    @Test
    void advertisementList() {
    }
}