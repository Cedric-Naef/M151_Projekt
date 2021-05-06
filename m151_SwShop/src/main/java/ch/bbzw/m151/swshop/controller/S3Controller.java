package ch.bbzw.m151.swshop.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/s3")
public class S3Controller {

    private final AmazonS3 amazonS3;

    private final static String BUCKET = "test";

    @Autowired
    public S3Controller(final AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> get(@RequestParam("key") final String key) {
        final S3Object s3Object = amazonS3.getObject(BUCKET, key);
        final HttpHeaders httpHeaders = new HttpHeaders();
        final InputStreamResource inputStreamResource = new InputStreamResource(s3Object.getObjectContent());
        httpHeaders.setContentLength(s3Object.getObjectMetadata().getContentLength());
        httpHeaders.setContentType(MediaType.valueOf(s3Object.getObjectMetadata().getContentType()));
        return new ResponseEntity<>(inputStreamResource, httpHeaders, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String add(@RequestParam("key") final String key, @RequestParam("file") final MultipartFile multipartFile) throws IOException {
        final ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        amazonS3.putObject(BUCKET, key, multipartFile.getInputStream(), objectMetadata);
        return amazonS3.getUrl(BUCKET, key).toString();
    }
}
