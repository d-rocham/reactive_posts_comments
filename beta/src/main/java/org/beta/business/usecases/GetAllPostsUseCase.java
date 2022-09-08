package org.beta.business.usecases;

import org.beta.application.adapters.repository.MongoViewRepository;
import org.beta.business.gateways.model.PostViewModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GetAllPostsUseCase {

    private final MongoViewRepository viewRepository;

    public GetAllPostsUseCase(MongoViewRepository viewRepository) {
        this.viewRepository = viewRepository;
    }

    public Flux<PostViewModel> getAllPosts() {
        return this.viewRepository.findAllPosts();
    }
}
