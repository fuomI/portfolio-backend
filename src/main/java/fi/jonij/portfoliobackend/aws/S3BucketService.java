package fi.jonij.portfoliobackend.aws;

import com.amazonaws.services.s3.model.Bucket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class S3BucketService {

    private final S3Client s3client;
    private List<Bucket> buckets;

    public S3BucketService() {
        this.s3client = new S3Client();
    }


}
