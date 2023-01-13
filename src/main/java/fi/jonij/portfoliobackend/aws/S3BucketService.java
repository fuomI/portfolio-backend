package fi.jonij.portfoliobackend.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import fi.jonij.portfoliobackend.user.UserRepository;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
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

    private final UserRepository userRepository;
    private final AWSCredentials credentials;
    private final AmazonS3 s3Client;
    private final List<Bucket> buckets;

    private final String bucketName = "jonij-portfolio-backend";
    private final String imageDirectory = "project-images/";

    public S3BucketService(UserRepository userRepository) {
        this.userRepository = userRepository;

        // All users have same AWSCredentials, so we can use the credentials of the first entry.
        this.credentials = new BasicAWSCredentials(
                userRepository.findAll().get(0).getS3AccessKey(),
                userRepository.findAll().get(0).getS3SecretKey()
        );

        this.s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_NORTH_1)
                .build();

        this.buckets = s3Client.listBuckets();
    }

    public List<Bucket> getBuckets() {
        return buckets;
    }

    public AmazonS3 getS3Client() {
        return s3Client;
    }

    public void uploadProjectImage(MultipartFile multipartFile) {
        File file = null;
        try {
            if (multipartFile.isEmpty()) {
                throw new AmazonS3Exception("Failed to store empty file.");
            }

            // s3client accepts File not MultipartFile
            file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

            try (OutputStream outputStream = new FileOutputStream(file)) {
                outputStream.write(multipartFile.getBytes());
            }

            s3Client.putObject(
                    bucketName,
                    imageDirectory + file.getName(),
                    file
            );

        } catch (IOException e) {
            throw new AmazonS3Exception("Failed to store file.", e);
        } finally {
            if (file != null) {
                file.delete();
            }
        }
    }

    public void uploadProjectImage(File file) {
        try {
            if (file == null) {
                throw new AmazonS3Exception("Failed to store empty file.");
            }

            s3Client.putObject(
                    bucketName,
                    "project-images/" + file.getName(),
                    file
            );

        } catch (Exception e) {
            throw new AmazonS3Exception("Failed to store file.", e);
        }
    }

    public void listFiles() {
        ObjectListing objectListing = s3Client.listObjects(bucketName);
        for(S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(objectSummary.getKey());
        }
    }

    public Resource getProjectImage(String projectImageFilename) {
        String key = imageDirectory + projectImageFilename;
        S3Object s3Object = s3Client.getObject(bucketName, key);
        if(s3Object == null) {
            throw new AmazonS3Exception("S3 Object with filename not found.");
        }

        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        return new InputStreamResource(inputStream);
    }

}
