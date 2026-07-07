package org.pytch.backend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Assert;
import org.junit.jupiter.api.*;

import org.pytch.backend.controller.ProjectController;
import org.pytch.backend.controller.UserController;
import org.pytch.backend.dto.request.PytchUserDto;
import org.pytch.backend.dto.request.SaveProjectDto;
import org.pytch.backend.model.PytchUser;
import org.pytch.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@SpringBootTest
class ProjectTests {

    @Autowired
    private ProjectController projectController;

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @BeforeAll
    public static void setUp() throws Exception {
        File rootDir = new File (BackendApplication.class.getClassLoader().getResource("").toURI());
        File appDir = new File(rootDir, "app");
        File storageDir = new File(appDir, "storage");
        storageDir.mkdirs();
    }

    @BeforeEach
    public void initialise() {
        PytchUserDto testUserDto = new PytchUserDto("test", "test@test.com", "test");
        Assertions.assertEquals(HttpStatusCode.valueOf(200), userController.register(testUserDto).getStatusCode());
    }

//    @Test
//    void testUploadProject() throws Exception {
//        PytchUser testUser = userService.findUserById(1L);
//
//        SaveProjectDto saveProjectDto = new SaveProjectDto(
//                "testProject",
//                PytchProgramKind.perMethod,
//                PytchProjectStatus.unlisted,
//                testUser
//        );
//
//        MockMultipartFile testFile
//                = new MockMultipartFile(
//                "test-file",
//                "test-project-zip",
//                "application/zip",
//                this.getClass().getClassLoader().getResource("test-project.zip").openStream()
//        );
//
//        Assert.assertEquals(
//                HttpStatusCode.valueOf(204),
//                projectController.uploadFile(saveProjectDto, (MultipartFile) testFile).getStatusCode()
//        );
//    }
}