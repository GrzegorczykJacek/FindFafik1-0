package com.jg.FindFafik.web;

import com.jg.FindFafik.persistence.dao.AdvertisementRepository;
import com.jg.FindFafik.persistence.dao.UserRepository;
import com.jg.FindFafik.persistence.model.Advertisement;
import com.jg.FindFafik.persistence.model.Category;
import com.jg.FindFafik.persistence.model.Species;
import com.jg.FindFafik.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class AdvertisementRestController {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/rest/advertisements")
    @ResponseBody
    public List<Advertisement> findAll() {
        return advertisementRepository.findAll();
    }

    @GetMapping("/rest/advertisements/{id}")
    @ResponseBody
    public Advertisement findById(@PathVariable final long id) {
        return advertisementRepository.findById(id);
    }

    @PostMapping("/rest/advertisements")
    @ResponseStatus(HttpStatus.CREATED)
    Advertisement advertisement(@RequestBody Advertisement advertisement, Authentication auth){
        advertisement.setUser(userRepository.findByUsername(auth.getName()));
        advertisement.setIconBySpecies(advertisement.getSpecies());
        advertisement.setDateOfPublication(LocalDateTime.now());
        advertisement.setEndDate(advertisement.getDateOfPublication().plusDays(30));
        return advertisementRepository.save(advertisement);
    }

    @PutMapping("/rest/advertisements/{id}")
    Advertisement replaceAdvertisement(@RequestBody Advertisement newAdvertisement, @PathVariable Long id){
        return advertisementRepository.findById(id)
                .map(advertisement -> {
                    advertisement.setIconBySpecies(newAdvertisement.getSpecies());
                    advertisement.setLatitude(newAdvertisement.getLatitude());
                    advertisement.setLongitude(newAdvertisement.getLongitude());
                    advertisement.setCategory(newAdvertisement.getCategory());
                    advertisement.setContents(newAdvertisement.getContents());
                    advertisement.setTitle(newAdvertisement.getTitle());
                    advertisement.setCity(newAdvertisement.getCity());
                    return advertisementRepository.save(advertisement);
                })
                .orElseGet(() ->{
                    newAdvertisement.setId(id);
                    return advertisementRepository.save(newAdvertisement);
                });
    }

    @DeleteMapping("rest/advertisements/{id}")
    void deleteAdvertisement(@PathVariable Long id){
        advertisementRepository.deleteById(id);
    }

}
