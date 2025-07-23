package com.shaper.server.model.entity;

import java.time.LocalDateTime;

public class Template {
    private Long id;
    private String name;
    private String description;
    
    private String content;
    private Long HireId;
    
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Template() {
    }

    public Template(Long id, String name, String description, String thumbnailUrl, 
                   String content, Long userId, boolean isPublic, Long categoryId,
                   String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.content = content;
        this.userId = userId;
        this.isPublic = isPublic;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
