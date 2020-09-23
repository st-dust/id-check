package cz.zatisigroup.service;


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

    public String getNameById(int id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new).getName();
    }

    public String getSurnameById(int id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new).getSurname();
    }

    public String getDepartment(int id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new).getDepartment();

    }

    public String getDepartmentID(int id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new).getDepartmentID();
    }

}
