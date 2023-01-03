package fi.jonij.portfoliobackend.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import fi.jonij.portfoliobackend.storage.StorageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

@Service
public class S3BucketService {

    private final AmazonS3 s3client;
    private final List<Bucket> buckets;

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

    public void uploadProjectImage(String bucketName, MultipartFile multipartFile) {
        File file = null;
        try {
            if (multipartFile.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }

            // s3client accepts File not MultipartFile
            file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

            try (OutputStream outputStream = new FileOutputStream(file)) {
                outputStream.write(multipartFile.getBytes());
            }

            s3client.putObject(
                    bucketName,
                    "project-images/" + file.getName(),
                    file
            );

        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        } finally {
            if (file != null) {
                file.delete();
            }
        }
    }

    public void uploadProjectImage(String bucketName, File file) {
        try {
            if (file == null) {
                throw new StorageException("Failed to store empty file.");
            }

            s3client.putObject(
                    bucketName,
                    "project-images/" + file.getName(),
                    file
            );

        } catch (Exception e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    public void listFiles(String bucketName) {
        ObjectListing objectListing = s3client.listObjects(bucketName);
        for(S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(objectSummary.getKey());
        }
    }
}
