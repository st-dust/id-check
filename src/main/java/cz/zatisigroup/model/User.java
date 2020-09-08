package cz.zatisigroup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="workers")
public class User {

    @Id
    @GeneratedValue
    @Column(name="ID")
    private Integer id;
    @Column(name="First_Name")
    private String name;
    @Column(name="Last_Name")
    private String surname;
    @Column(name="Has_Discount")
    private String hasDiscount;
    @Column(name="Discount_Value")
    private String discountValue;
    @Column(name = "Department")
    private String department;

    public String getHasDiscount() {
        return hasDiscount;
    }

    public void setHasDiscount(String hasDiscount) {
        this.hasDiscount = hasDiscount;
    }

    public User() {
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(String discountValue) {
        this.discountValue = discountValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
