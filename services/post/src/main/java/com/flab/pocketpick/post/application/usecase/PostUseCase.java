package com.flab.pocketpick.post.application.usecase;

import com.flab.pocketpick.post.application.dto.CreatePostRequest;
import com.flab.pocketpick.post.application.dto.PostDetailResult;

public interface PostUseCase {

    void create(CreatePostRequest request, Long sellerId);

    PostDetailResult getPost(Long postId);
}
