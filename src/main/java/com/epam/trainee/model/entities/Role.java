package com.epam.trainee.model.entities;

public class Role {

    private Integer id;
    private String name;

    public Role() {
    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
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


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj == null || !(obj instanceof Role)) {
            return false;
        }
        Role role = (Role) obj;

        if (name != null && !name.equals(role.getName())) return false;
        return id != null && id.equals(role.getId());
    }

    @Override
    public int hashCode() {
        return id != null ? id : 0;
    }
}
