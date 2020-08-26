    package com.jg.FindFafik.web;

import com.jg.FindFafik.persistence.dao.UserRepository;
import com.jg.FindFafik.persistence.dao.AdvertisementRepository;
import com.jg.FindFafik.persistence.model.Advertisement;
import com.jg.FindFafik.persistence.model.User;
import com.jg.FindFafik.security.MyAuthenticationService;
import com.jg.FindFafik.security.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
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

//    @PreAuthorize("hasPermission(#id, 'Advert', 'read')")
//    @GetMapping("/my-advertisements")
//    public String advertisement(Model model) {
//
//        final List<Advertisement> allAdverts = advertisementRepository.findAll();
//
//        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        final MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
//        final String userName = principal.getUsername();
//        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        System.out.println(authorities);
//
//        model.addAttribute("allAdverts", allAdverts);
//        model.addAttribute("username", userName);
//        return "advertisements";
//    }

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
    public String userSubmit(@ModelAttribute User user) {
        String pass = user.getPassword();
        user.setPassword(encoder.encode(pass));
        userRepository.save(user);
        return "show-added-user";
    }

//    @PostMapping("/userSubmit")
//    public String userSubmit(@ModelAttribute User user) {
//        String pass = user.getPassword();
//        user.setPassword(encoder.encode(pass));
//        userRepository.save(user);
//        return "show-added-user";
//    }

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

    @PreAuthorize("hasPermission(#id, 'Advert', 'read')")
    @RequestMapping("/advertisementList")
    public String advertisementList(Model model) {

        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());

        final String userName = myAuth.getUserName();
        model.addAttribute("username", userName);
        final Long userId = myAuth.getUser().getId();
        model.addAttribute("advertisementList", advertisementRepository.findByUserId(userId));
        return "advertisementList";
    }
}