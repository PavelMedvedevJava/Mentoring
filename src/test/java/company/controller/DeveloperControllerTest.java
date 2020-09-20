package company.controller;



import company.model.Developer;
import company.repo.sql.DeveloperRepositoryImplSQL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class DeveloperControllerTest {

    @Mock
    DeveloperRepositoryImplSQL mockDeveloperRepositoryImplSQL;

    @InjectMocks
    DeveloperController developerController = new DeveloperController(mockDeveloperRepositoryImplSQL);

    Developer developer;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void create() {
       developer = new Developer();
        when(mockDeveloperRepositoryImplSQL.create(developer)).thenReturn(developer);
        Developer developer1 = developerController.create(developer);
        assertEquals (developer,developer1);
    }

    @Test
    public void getAllDev() {
        List<Developer> developerList = new ArrayList<>();
        developer = new Developer();
        developerList.add(developer);
        when(mockDeveloperRepositoryImplSQL.getAll()).thenReturn(developerList);
        List<Developer> developer2 = developerController.getAllDev();
        assertEquals (developerList,developer2);

    }

    @Test
    public void deleteDeveloper() {
        developerController.deleteDeveloper(2);
        verify(mockDeveloperRepositoryImplSQL,atLeastOnce()).delete((long) 2);
    }

    @Test
    public void updateDeveloper() {
        developer = new Developer();
        when(mockDeveloperRepositoryImplSQL.update(anyObject())).thenReturn(developer);
        Developer developer1 = developerController.updateDeveloper(developer);
        assertEquals (developer,developer1);
    }

    @Test
    public void getDeveloper() {
        developer = new Developer();
        when(mockDeveloperRepositoryImplSQL.read(anyLong())).thenReturn(Optional.ofNullable(developer));
        Developer developer1 = developerController.getDeveloper(2).get();
        assertEquals (developer,developer1);
    }
}