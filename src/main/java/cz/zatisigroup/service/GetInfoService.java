package cz.zatisigroup.service;

import cz.zatisigroup.model.User;
import cz.zatisigroup.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class GetInfoService implements InfoService {

    @Autowired
    private UsersRepository userRepository;

    public String getPersonalNumber(int id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new).getPersonalNumber();
    }

    public String getName(int id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new).getName();
    }

    public String getSurname(int id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new).getSurname();
    }

    public String getDepartment(int id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new).getDepartment();

    }

    public String getDepartmentID(int id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new).getDepartmentID();
    }

    public User getWholeUser(int id) {
        User user = new User();
        user.setId(id);
        user.setPersonalNumber(getPersonalNumber(id));
        user.setName(getName(id));
        user.setSurname(getSurname(id));
        user.setDepartment(getDepartment(id));
        user.setDepartmentID(getDepartmentID(id));
        return user;
    }
}
