package fi.jonij.portfoliobackend.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class S3Client {

    private final AmazonS3 s3client;
    private final AmazonCredentials credentials = new AmazonCredentials();

    protected S3Client(AmazonS3 s3client) {
        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials.getCredentials()))
                .withRegion(Regions.EU_NORTH_1)
                .build();
    }

    protected AmazonS3 getS3client() {
        return s3client;
    }
}
