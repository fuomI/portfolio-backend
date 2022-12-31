package fi.jonij.portfoliobackend.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class S3BucketService {

    private final AmazonS3 s3client;
    private List<Bucket> buckets;

    public S3BucketService() {
        this.s3client = new S3Client().getS3client();
        this.buckets = s3client.listBuckets();
    }

    public AmazonS3 getS3client() {
        return s3client;
    }

    public List<Bucket> getBuckets() {
        return buckets;
    }
}
