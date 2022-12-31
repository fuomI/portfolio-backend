package fi.jonij.portfoliobackend.aws;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class S3BucketServiceIntegrationTest {

    @Autowired
    private S3BucketService s3BucketService = new S3BucketService();

    private AmazonS3 s3Client = s3BucketService.getS3client();

    @Test
    public void getBuckets_successfulScenario() {
        String expected = "jonij-portfolio-backend";
        System.out.println(s3Client.listBuckets().toString());
    }

}