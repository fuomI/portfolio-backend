package fi.jonij.portfoliobackend.aws;

import fi.jonij.portfoliobackend.storage.StorageException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class S3BucketServiceIntegrationTest {

    @Autowired
    private S3BucketService s3BucketService = new S3BucketService();

    @Test
    public void getBuckets_successfulScenario() {
        String expected = "jonij-portfolio-backend";
        String actual = s3BucketService.getBuckets().get(0).getName();
        assertEquals(expected, actual);
    }

    @Test
    public void uploadProjectImage_successfulScenario() {
        File file = new File("src/main/resources/static/upload/default.png");

        try {
            s3BucketService.uploadProjectImage("jonij-portfolio-backend",
                                        file);
            System.out.println("Successful");

        } catch (StorageException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void listFiles_successfulScenario() {
        s3BucketService.listFiles("jonij-portfolio-backend");
    }

}