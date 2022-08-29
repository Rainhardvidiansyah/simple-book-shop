package com.auth.jwt.seed;

import com.auth.jwt.model.Book;
import com.auth.jwt.dto.utils.repository.BooksRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class BookCategoryInitialData implements ApplicationListener<ContextRefreshedEvent> {

    private final BooksRepo booksRepo;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        var book1 = Book.builder().id(1L)
                .title("title 1").author("Author 1") .synopsis("Synopsis 1").tags("Tags 1, Category 1")
                .price(75000.00).build();

        var book2 = Book.builder().id(2L)
                .title("title 2").author("author2").synopsis("synopsis 2").tags("tags 2, category 2")
                .price(55750.00).build();

        var book3 = Book.builder().id(3L)
                .title("title 3").author("author 3").synopsis("synopsis 3").tags("tags 3, category 3")
                .price(98000.00).build();

        log.info("Book 3 -> {}", book3);
        booksRepo.saveAll(List.of(book1, book2, book3));

    }
}
