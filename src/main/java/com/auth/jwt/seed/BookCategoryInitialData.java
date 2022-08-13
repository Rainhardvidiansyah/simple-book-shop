package com.auth.jwt.seed;

import com.auth.jwt.model.Category;
import com.auth.jwt.model.ECategory;
import com.auth.jwt.repository.CategoryRepo;
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

    private final CategoryRepo categoryRepo;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try{
            var philosophy = new Category(ECategory.PHILOSOPHY);
            var mysticism = new Category(ECategory.MYSTICISM);
            var islamicPhilosophy = new Category(ECategory.ISLAMIC_PHILOSOPHY);
            var islamicTheology = new Category(ECategory.ISLAMIC_THEOLOGY);
            var culturalStudies = new Category(ECategory.CULTURAL_STUDIES);
            var socialScienceAndPolitics = new Category(ECategory.SOCIAL_POLITICS);
            List<Category> savedCategories = List.of(philosophy, mysticism, islamicPhilosophy, islamicTheology,
                    culturalStudies, socialScienceAndPolitics);
            log.info("First data: {}", philosophy);
            log.info("Second data: {}", mysticism);
            log.info("All data: {}", savedCategories);
            categoryRepo.saveAll(savedCategories);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
