package com.learning.employemanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learning.employemanagementsystem.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentModel {

    private UUID id;

    private String name;

    @JsonIgnore
    private List<Profile> profiles;
}
