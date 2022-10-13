package com.auth.jwt.controller;


import com.auth.jwt.service.BookImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/image")
@RequiredArgsConstructor
@Slf4j
public class BookImageController {

    private final BookImageService imageService;

    @PostMapping("/upload/{book_id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> storeImage(@RequestParam("file") MultipartFile file, @PathVariable("book_id") Long bookId){
//        var saveImage = imageService.storeImage(file, bookId);
//        log.info("Data Image: {}", saveImage);
//        return new ResponseEntity<>(saveImage, HttpStatus.OK);
        return null;
    }

    @GetMapping("/get")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getIdImage(@RequestParam long image_id){
        var bookId = imageService.getOneImage(image_id);
        if(bookId.isEmpty()){
            return new ResponseEntity<>("Id with " + bookId + " doesn't exist",
                    HttpStatus.BAD_REQUEST);        }
        return new ResponseEntity<>(bookId, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateImage(@RequestParam MultipartFile file, @PathVariable("id") Long image_id){
        var imageId = imageService.getOneImage(image_id);
        if(imageId.isEmpty()){
            return new ResponseEntity<>("Image not found", HttpStatus.BAD_REQUEST);
        }
        var image = imageService.updateImage(file, image_id);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
}
