package com.jg.FindFafik.web;

import com.jg.FindFafik.persistence.dao.AdvertisementRepository;
import com.jg.FindFafik.persistence.dao.UserRepository;
import com.jg.FindFafik.persistence.model.Advertisement;
import com.jg.FindFafik.persistence.model.User;
import com.jg.FindFafik.security.MyAuthenticationService;
import com.jg.FindFafik.security.MyUserPrincipal;
import com.jg.FindFafik.service.PrivilegesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdvertisementRepository advertisementRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private MyAuthenticationService myAuth;

    @Autowired
    private PrivilegesService privilegesService;

    @PreAuthorize("hasPermission(#id, 'Advert', 'read')")
    @GetMapping("/advert/{id}")
    @ResponseBody
    public Advertisement findById(@PathVariable final long id) {
        return advertisementRepository.findById(id);
    }

    @PreAuthorize("hasPermission(#id, 'User', 'read')") //#id reffers to method parameter
    @GetMapping("/user")
    @ResponseBody
    public MyUserPrincipal retrieveUserDetails(@AuthenticationPrincipal MyUserPrincipal principal) {
        return principal;
    }


    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }

    @RequestMapping("/") // Main page mapping for non-logged users
    public String index(Model model) {
        final String userName = myAuth.getUserName();
        final List<Advertisement> allAdverts = advertisementRepository.findAll();
        model.addAttribute("username", userName);
        model.addAttribute("advertisement", new Advertisement());
        model.addAttribute("allAdverts", allAdverts);
        model.addAttribute("allAdvertsSize", allAdverts.size());
        return "index";
    }

    @GetMapping("/addUserForm")
    public String userForm(Model model) {
        final String userName = myAuth.getUserName();
        model.addAttribute("username", userName);
        model.addAttribute("user", new User());
        return "addUserSubmit";
    }

    @RequestMapping(path = "/userSubmit", method = RequestMethod.POST)
    public String userSubmit(@ModelAttribute User user, Model model) {
        String pass = user.getPassword();
        user.setPassword(encoder.encode(pass));
        userRepository.save(user);
        privilegesService.setPrivileges(user);

        //TODO
        // Set priviliges in user provileges service
        final String userName = user.getUsername();
        model.addAttribute("userName", userName);
        return "show-added-user";
    }

    @PreAuthorize("hasPermission(#id, 'Advert', 'write')")
    @GetMapping("/advertisementForm")
    public String advertisememntForm(Model model) {
        final String userName = myAuth.getUserName();
        model.addAttribute("username", userName);
        model.addAttribute("advertisement", new Advertisement());
        return "advertisement";
    }

    @PreAuthorize("hasPermission(#id, 'Advert', 'write')")
    @RequestMapping("/advertisement")
    public String advertisementSubmit(@ModelAttribute Advertisement advertisement, Model model) {
        advertisement.setUser(myAuth.getUser());
        advertisement.setDateOfPublication(LocalDateTime.now());
        advertisement.setEndDate(advertisement.getDateOfPublication().plusDays(30));
        advertisement.setIconBySpecies(advertisement.getSpecies());
        advertisementRepository.save(advertisement);
        final List<Advertisement> allAdverts = advertisementRepository.findAll();
        model.addAttribute("allAdverts", allAdverts);
        model.addAttribute("allAdvertsSize", allAdverts.size());
        return "index";
    }

    // Returns advertisement list view for authorized user
    @PreAuthorize("hasPermission(#id, 'Advert', 'read')")
    @RequestMapping("/advertisementList")
    public String advertisementList(Model model) {
        final String userName = myAuth.getUserName();
        model.addAttribute("username", userName);
        final Long userId = myAuth.getUser().getId();
        model.addAttribute("advertisementList", advertisementRepository.findByUserId(userId));
        return "advertisementList";
    }

    // Returns advertisement view for update
    @PreAuthorize("hasPermission(#id, 'Advert', 'read')")
    @RequestMapping("/advertisementUpdate/{addId}")
    public String advertisementById(@PathVariable final Long addId, Model model) {
        final String userName = myAuth.getUserName();
        model.addAttribute("username", userName);
        final Advertisement advertisement = advertisementRepository.findById(addId).get();
        model.addAttribute("advertisement", advertisement);
        final List<Advertisement> advertisementList = new ArrayList<>();
        advertisementList.add(advertisement);
        model.addAttribute("allAdverts", advertisementList);
        model.addAttribute("allAdvertsSize", advertisementList.size());
        return "advertisementUpdate";
    }

    // Updates chosen advertisement (getOne() returns reference to wanted object)
    @PreAuthorize("hasPermission(#id, 'Advert', 'write')")
    @RequestMapping("/advertisementUpdateSubmit")
    public String advertisementUpdateSubmit(@ModelAttribute Advertisement advertisement){
        final Advertisement toUpdate = advertisementRepository.getOne(advertisement.getId());
        toUpdate.setTitle(advertisement.getTitle());
        toUpdate.setContents(advertisement.getContents());
        toUpdate.setCity(advertisement.getCity());
        toUpdate.setCategory(advertisement.getCategory());
        toUpdate.setSpecies(advertisement.getSpecies());
        toUpdate.setIcon(toUpdate.getIcon());
        toUpdate.setLongitude(advertisement.getLongitude());
        toUpdate.setLatitude(advertisement.getLatitude());
        toUpdate.setDateOfPublication(LocalDateTime.now());
        toUpdate.setEndDate(LocalDateTime.now().plusDays(30));
        advertisementRepository.save(toUpdate);
        return "index";
    }
}