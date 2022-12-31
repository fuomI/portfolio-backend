package fi.jonij.portfoliobackend.aws;

import com.amazonaws.services.s3.model.Bucket;

import java.util.List;

public class S3BucketService {

    private final S3Client s3client;
    private List<Bucket> buckets;

    public S3BucketService() {
        this.s3client = new S3Client();
    }
}
