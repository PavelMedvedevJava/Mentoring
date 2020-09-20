package company.controller;


import company.model.Skill;
import company.repo.sql.SkillRepositoryImplSQL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SkillControllerTest {

    @Mock
    SkillRepositoryImplSQL mockSkillRepositoryImplSQL;

    @InjectMocks
    SkillController skillController = new SkillController(mockSkillRepositoryImplSQL);

    Skill skill;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllSkill() {
        List<Skill> skillList = new ArrayList<>();
        skill = new Skill();
        skillList.add(skill);
        when(mockSkillRepositoryImplSQL.getAll()).thenReturn(skillList);
        List<Skill> skillList1 = skillController.getAllSkill();
        assertEquals (skillList,skillList1);
    }

    @Test
    public void addSkill() {
        skill = new Skill();
        when(mockSkillRepositoryImplSQL.create(skill)).thenReturn(skill);
        Skill skill1 = skillController.addSkill(skill);
        assertEquals (skill,skill1);
    }

    @Test
    public void deleteSkill() {
        skillController.deleteSkill(2);
        verify(mockSkillRepositoryImplSQL,atLeastOnce()).delete((long) 2);
    }

    @Test
    public void updateSkill() {
        skill = new Skill();
        when(mockSkillRepositoryImplSQL.update(anyObject())).thenReturn(skill);
        Skill skill1 = skillController.updateSkill(skill);
        assertEquals (skill,skill1);
    }
}