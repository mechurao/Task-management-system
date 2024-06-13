package com.mechurao.taskmanagementsystem;

import com.mechurao.taskmanagementsystem.api.exception.ResourceNotFoundException;
import com.mechurao.taskmanagementsystem.api.request.ProjectAddRequest;
import com.mechurao.taskmanagementsystem.api.request.ProjectEditRequest;
import com.mechurao.taskmanagementsystem.domain.Project;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ProjectIntegrationTests extends IntegrationTest{
    @Test
    public void getAll(){
        final ResponseEntity<List<Project>> projects = restTemplate.exchange(
                "/project",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        Assertions.assertEquals(HttpStatus.OK, projects.getStatusCode());
        Assertions.assertNotNull(projects.getBody());
        Assertions.assertTrue(projects.getBody().size() >= 2);
    }

    @Test
    public void getAllByUser(){
        final ResponseEntity<List<Project>> projectsByUser = restTemplate.exchange(
                "/project/userId=1",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        Assertions.assertEquals(HttpStatus.OK, projectsByUser.getStatusCode());
        Assertions.assertNotNull(projectsByUser.getBody());
        Assertions.assertFalse(projectsByUser.getBody().isEmpty());
    }

    @Test
    public void insert(){
        insertProject(generateRandomProject());
    }

    @Test
    public void insertWithoutDescription(){
        final ProjectAddRequest addRequest = new ProjectAddRequest(1L, "name"+Math.random(), null);
        final ResponseEntity<Long> addProjectResponse = restTemplate.postForEntity(
                "/project",
                addRequest,
                Long.class
        );
        Assertions.assertEquals(HttpStatus.CREATED, addProjectResponse.getStatusCode());
        final Long id = addProjectResponse.getBody();
        Assertions.assertNotNull(id);

        final ResponseEntity<Project> getResponse = restTemplate.getForEntity(
          "/project/"+id,
          Project.class
        );

        Assertions.assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        Assertions.assertNotNull(getResponse.getBody());
        Assertions.assertEquals(id, getResponse.getBody().getId());
        Assertions.assertEquals(addRequest.getUserId(), getResponse.getBody().getUserId());
        Assertions.assertEquals(addRequest.getName(), getResponse.getBody().getName());
        Assertions.assertNull(getResponse.getBody().getDescription());
    }

    @Test
    public void getProject(){
        final ProjectAddRequest addRequest = generateRandomProject();
        final long id = insertProject(addRequest);
        final ResponseEntity<Project> project = restTemplate.getForEntity(
                "/project/"+id,
                Project.class
        );
        Assertions.assertEquals(HttpStatus.OK, project.getStatusCode());
        Assertions.assertNotNull(project.getBody());
        Assertions.assertEquals(id, project.getBody().getId());
        Assertions.assertEquals(addRequest.getUserId(), project.getBody().getUserId());
        Assertions.assertEquals(addRequest.getName(), project.getBody().getName());
        Assertions.assertEquals(addRequest.getDescription(),project.getBody().getDescription());
    }

    @Test
    public void deleteProject(){
        final ProjectAddRequest addRequest = generateRandomProject();
        final long id = insertProject(addRequest);

        // Delete project
        final ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/project/"+id,
                HttpMethod.DELETE,
                null,
                Void.class
        );
        Assertions.assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());

        // Check project existence
        final ResponseEntity<ResourceNotFoundException> getResponse = restTemplate.getForEntity(
          "/project/"+id,
          ResourceNotFoundException.class
        );
        Assertions.assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }

    @Test
    public void updateProject(){
        final ProjectAddRequest addRequest = generateRandomProject();
        final long id = insertProject(addRequest);

        final ProjectEditRequest editRequest = new ProjectEditRequest("editedName","editedDescription");
        final ResponseEntity<Void> updateResponse = restTemplate.exchange(
                "/project/"+id,
                HttpMethod.PUT,
                new HttpEntity<>(editRequest),
                Void.class
        );
        Assertions.assertEquals(HttpStatus.OK, updateResponse.getStatusCode());

        // check updated project
        final ResponseEntity<Project> getResponse = restTemplate.getForEntity(
          "/project/"+id,
          Project.class
        );
        Assertions.assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        Assertions.assertNotNull(getResponse.getBody());
        Assertions.assertEquals(id, getResponse.getBody().getId());
        Assertions.assertEquals(editRequest.getName(), getResponse.getBody().getName());
        Assertions.assertEquals(editRequest.getDescription(),getResponse.getBody().getDescription());
    }


    private long insertProject(ProjectAddRequest request){
        final ResponseEntity<Long> project = restTemplate.postForEntity(
                "/project",
                request,
                Long.class
        );
        Assertions.assertEquals(HttpStatus.CREATED, project.getStatusCode());
        Assertions.assertNotNull(project.getBody());
        return project.getBody();
    }

    private ProjectAddRequest generateRandomProject(){
        return new ProjectAddRequest(
                1L,
                "name"+Math.random(),
                "description"+Math.random()
        );
    }
}
