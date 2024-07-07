package com.moing.backend.domain.comment.domain.service;

public interface CommentSaveService<T> {
    T saveComment(T comment);
}
