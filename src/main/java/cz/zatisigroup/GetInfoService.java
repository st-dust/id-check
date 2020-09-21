package cz.zatisigroup;


import cz.zatisigroup.model.User;
import cz.zatisigroup.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;

// TODO service should implement some interface
@Service
public class GetInfoService implements InfoService {

    public String getDepartmentID;
    @Autowired
    private UsersRepository userRepository;

    public boolean isAnEmployee(int id) {
        return userRepository.existsById(id);
    }

    public String getNameById(int id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new).getName();
    }
    // TODO if user not found method will produce {@link NullPointerException}
    public String getPersonalNumber(int id) {
        //return userRepository.findById(id).orElse(null).getPersonalNumber();
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new).getPersonalNumber();

    }

    public String getSurnameById(int id) {
//        return userRepository.findById(id).orElse(null).getSurname();
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new).getSurname();
    }

    public String getDepartment(int id) {
//        return userRepository.findById(id).orElse(null).getDepartment();
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new).getDepartment();

    }

    public String getDepartmentID(int id) {
//        return userRepository.findById(id).orElse(null).getDepartmentID();
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new).getDepartmentID();
    }

}
