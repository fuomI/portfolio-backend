package fi.jonij.portfoliobackend.aws;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class S3BucketServiceIntegrationTest {

    @Autowired
    S3BucketService s3BucketService;

    @BeforeAll
    public void getS3Client() {
        AmazonS3 s3Client = s3BucketService.getS3client();
    }

}