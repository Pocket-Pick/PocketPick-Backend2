package com.flab.pocketpick.post.presentation;

import com.flab.pocketpick.post.application.dto.CreatePostRequest;
import com.flab.pocketpick.post.application.usecase.PostUseCase;
import com.flab.pocketpick.post.presentation.dto.PostDetailResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostUseCase postUseCase;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CreatePostRequest request,
                                       @AuthenticationPrincipal Long sellerId) {
        postUseCase.create(request, sellerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(PostDetailResponse.from(postUseCase.getPost(id)));
    }
}
