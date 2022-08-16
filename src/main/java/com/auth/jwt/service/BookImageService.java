package com.auth.jwt.service;

import com.auth.jwt.model.BookImage;
import com.auth.jwt.repository.BookImageRepo;
import com.auth.jwt.repository.BooksRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookImageService {

    private final BookImageRepo imageRepo;
    private final BooksRepo booksRepo;

    public BookImage storeImage(MultipartFile file, Long id){
        var book = booksRepo.findById(id);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")){
                throw new RuntimeException();
            }
            var image = new BookImage();
            image.setImageName(fileName);
            image.setImageData(file.getBytes());
            image.setDescription(file.getContentType());
            image.setBook(book.get());
            List<BookImage> images = new ArrayList<>();
            images.add(image);
            book.get().setBookImage(images);
            return imageRepo.save(image);

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public Optional<BookImage> getOneImage(Long fileId) {
        return imageRepo.findById(fileId);
    }

    public BookImage updateImage(MultipartFile file, Long image_id){
        BookImage image = imageRepo.findById(image_id)
                .orElseThrow(() -> new RuntimeException());
        if(image == null){
            throw new RuntimeException();
        }
        String newName = file.getOriginalFilename();
        try {
            image.setImageData(file.getBytes());
            image.setImageName(newName);
            image.setDescription(file.getContentType());
        }catch(IOException e){
            e.printStackTrace();
        }
        return imageRepo.save(image);
    }

}
