package fi.jonij.portfoliobackend.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class S3BucketServiceIntegrationTest {

    @Autowired
    private S3BucketService s3BucketService;

    @Test
    public void uploadProjectImage_successfulScenario() {
        AmazonS3 s3Client = s3BucketService.getS3Client();
        File file = new File("src/main/resources/static/upload/default.png");
        String expected = "project-images/default.png";

        try {
            s3BucketService.uploadProjectImage(file);

            String actual = s3Client.getObject("jonij-portfolio-backend",
                                                        "project-images/default.png")
                                                            .getKey();
            assertEquals(expected, actual);

        } catch (AmazonS3Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void listFiles_successfulScenario() {
        s3BucketService.listFiles();
    }

    @Test
    public void getProjectImage_successfulScenario() {
        try {
            Resource actual = s3BucketService.getProjectImage("default.png");

            assertNotNull(actual);

        } catch (AmazonS3Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void getProjectImage_negativeScenario() {
        // There is no project image with filename "car.png"

        try {
            Resource actual = s3BucketService.getProjectImage("car.png");
            fail();

        } catch (AmazonS3Exception e) {
            System.out.println(e.getMessage());
        }
    }
}