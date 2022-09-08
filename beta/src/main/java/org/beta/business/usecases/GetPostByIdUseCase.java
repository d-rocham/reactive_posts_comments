package org.beta.business.usecases;


import org.beta.application.adapters.repository.MongoViewRepository;
import org.beta.business.gateways.model.PostViewModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetPostByIdUseCase {
    private final MongoViewRepository viewRepository;

    public GetPostByIdUseCase(MongoViewRepository viewRepository) {
        this.viewRepository = viewRepository;
    }

    public Mono<PostViewModel> getPostById(String targetPostId) {
        return viewRepository.findByAggregateId(targetPostId);
    }
}
