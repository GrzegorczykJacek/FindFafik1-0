package com.jg.FindFafik.persistence;

import com.jg.FindFafik.persistence.dao.AdvertisementRepository;
import com.jg.FindFafik.persistence.dao.PrivilegeRepository;
import com.jg.FindFafik.persistence.dao.RoleRepository;
import com.jg.FindFafik.persistence.dao.UserRepository;
import com.jg.FindFafik.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class SetupData {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        initPrivileges();
        initRoles();
        initUsers();
        initAdvertisements();
    }

    private void initRoles() {
        final Role roleAdmin = new Role();
        roleAdmin.setRole("ADMIN");
        roleRepository.save(roleAdmin);
        final Role roleUser = new Role();
        roleUser.setRole("USER");
        roleRepository.save(roleUser);
    }

    private void initUsers() {
        final Privilege privilege1 = privilegeRepository.findByName("ADVERT_READ_PRIVILEGE");
        final Privilege privilege2 = privilegeRepository.findByName("ADVERT_WRITE_PRIVILEGE");
        final Privilege privilege3 = privilegeRepository.findByName("USER_READ_PRIVILEGE");
        final Privilege privilege4 = privilegeRepository.findByName("USER_WRITE_PRIVILEGE");


        final User user1 = new User();
        user1.setUsername("Dorota");
        user1.setPassword(encoder.encode("passDorota"));
        user1.setPrivileges(new HashSet<>(Arrays.asList(privilege1, privilege2, privilege3, privilege4)));
        Set<Role> dorotaRoles = new HashSet<>();
        dorotaRoles.add(roleRepository.findByRole("ADMIN"));
        dorotaRoles.add(roleRepository.findByRole("USER"));
        user1.setRoles(dorotaRoles);
        userRepository.save(user1);

        final User user2 = new User();
        user2.setUsername("Jacek");
        user2.setPassword(encoder.encode("passJacek"));
        user2.setPrivileges(new HashSet<>(Arrays.asList(privilege1, privilege2, privilege3, privilege4)));
        Set<Role> jacekRoles = new HashSet<>();
        jacekRoles.add(roleRepository.findByRole("ADMIN"));
        jacekRoles.add(roleRepository.findByRole("USER"));
        user2.setRoles(jacekRoles);
        userRepository.save(user2);
    }

    private void initAdvertisements() {
        final Advertisement advertisement = new Advertisement();
        final Advertisement advertisement1 = new Advertisement();

        advertisement.setTitle("Zaginął pieseł!!!");
        advertisement.setCity("Lublin");
        advertisement.setContents("Dziś w okolicach 12.00 zaginął czarny owczarek niemiecki z czerwoną obrożą.");
        advertisement.setCategory(Category.LOST);
        advertisement.setSpecies(Species.DOG);
        advertisement.setDateOfPublication(LocalDateTime.now());
        advertisement.setEndDate(advertisement.getDateOfPublication().plus(Duration.ofDays(30)));
        advertisement.setLatitude(51.2246013);
        advertisement.setLongitude(22.4957823);
        advertisement.setUser(userRepository.findByUsername("Jacek"));
        advertisement.setIcon("assets/img/marker-icons/dog.png");
        advertisementRepository.save(advertisement);

        advertisement1.setTitle("Zaginął koteł!");
        advertisement1.setCity("Lublin");
        advertisement1.setContents("Mój sierściuch dachowy wyszedł i nie wrócił. Ma białe łapki i krawat.");
        advertisement1.setCategory(Category.LOST);
        advertisement1.setSpecies(Species.CAT);
        advertisement1.setDateOfPublication(LocalDateTime.now());
        advertisement1.setEndDate(advertisement.getDateOfPublication().plus(Duration.ofDays(30)));
        advertisement1.setUser(userRepository.findByUsername("Dorota"));
        advertisement1.setLatitude(51.2536688);
        advertisement1.setLongitude(22.5766788);
        advertisement1.setIcon("assets/img/marker-icons/cat.png");
        advertisementRepository.save(advertisement1);
    }

    private void initPrivileges() {
        final Privilege privilege1 = new Privilege("ADVERT_READ_PRIVILEGE");
        privilegeRepository.save(privilege1);

        final Privilege privilege2 = new Privilege("ADVERT_WRITE_PRIVILEGE");
        privilegeRepository.save(privilege2);

        final Privilege privilege3 = new Privilege("USER_READ_PRIVILEGE");
        privilegeRepository.save(privilege3);

        final Privilege privilege4 = new Privilege("USER_WRITE_PRIVILEGE");
        privilegeRepository.save(privilege4);
    }
}
