package cz.zatisigroup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="workers")
//TODO use lombook library optional
public class User {

    @Id
    @GeneratedValue
    @Column(name="ID")
    private Integer id;
    @Column(name = "Personal_Number")
    private String personalNumber;
    @Column(name="First_Name")
    private String name;
    @Column(name="Last_Name")
    private String surname;
    @Column(name = "Department")
    private String department;
    @Column(name = "Department_ID")
    private String departmentID;

    public User() {
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String teamName) {
        this.surname = teamName;
    }
}
