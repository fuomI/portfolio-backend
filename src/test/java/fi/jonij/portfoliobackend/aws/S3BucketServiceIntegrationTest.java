package fi.jonij.portfoliobackend.aws;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class S3BucketServiceIntegrationTest {

    @Autowired
    S3BucketService s3BucketService;
    

}