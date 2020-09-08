package cz.zatisigroup;


import cz.zatisigroup.model.User;
import cz.zatisigroup.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class GetInfoService implements Serializable {

    @Autowired
    private UsersRepository userRepository;

    public boolean isAnEmployee(int id) {
        return userRepository.existsById(id);
    }

    public String getNameById(int id) {
        return userRepository.findById(id).orElse(null).getName();
    }

    public String getSurnameById(int id) {
        return userRepository.findById(id).orElse(null).getSurname();
    }

    public String getNameAndSurname(int id) {
        return  getNameById(id) + " " + getSurnameById(id);
    }

    public String getDepartment(int id) {
        return userRepository.findById(id).orElse(null).getDepartment();
    }

}
