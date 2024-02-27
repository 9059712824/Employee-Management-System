package com.learning.employemanagementsystem.mapper;

import com.learning.employemanagementsystem.dto.SkillsDto;
import com.learning.employemanagementsystem.entity.Skills;
import org.mapstruct.Mapper;

@Mapper
public interface SkillsMapper {
    Skills skillsDtoToSkills(SkillsDto skillsDto);
}
