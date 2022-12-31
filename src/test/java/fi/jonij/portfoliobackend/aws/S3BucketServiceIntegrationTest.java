package fi.jonij.portfoliobackend.aws;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class S3BucketServiceIntegrationTest {

    @Autowired
    private S3BucketService s3BucketService;

    private AmazonS3 s3Client = s3BucketService.getS3client();



}