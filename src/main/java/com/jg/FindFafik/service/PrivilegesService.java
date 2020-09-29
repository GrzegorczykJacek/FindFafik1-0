package com.jg.FindFafik.service;

import com.jg.FindFafik.persistence.dao.PrivilegeRepository;
import com.jg.FindFafik.persistence.dao.UserRepository;
import com.jg.FindFafik.persistence.model.Privilege;
import com.jg.FindFafik.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PrivilegesService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PrivilegeRepository privilegeRepository;

    // Set privileges to a new registered user
    public void setPrivileges(User user){
        final User one = userRepository.getOne(user.getId());
        final Set<Privilege> privileges = new HashSet<>();
        privileges.add(privilegeRepository.findByName("ADVERT_READ_PRIVILEGE"));
        privileges.add(privilegeRepository.findByName("ADVERT_WRITE_PRIVILEGE"));
        one.setPrivileges(privileges);
        userRepository.save(one);
    }

}
