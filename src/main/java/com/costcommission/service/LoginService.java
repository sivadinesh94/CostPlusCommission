package com.costcommission.service;

import com.costcommission.dto.UserDTO;
import com.costcommission.entity.User;
import com.costcommission.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO validateUser(String loginId, String password) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        User user = userRepository.findUserByLoginIdAndPassword(loginId, password);
        if(user == null)
            return  null;
        else
            return modelMapper.map(user, UserDTO.class);
    }
}
