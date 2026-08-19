package com.flab.pocketpick.post.application.usecase;

import com.flab.pocketpick.post.application.dto.CreatePostRequest;

public interface PostUseCase {

    void create(CreatePostRequest request, Long sellerId);
}
