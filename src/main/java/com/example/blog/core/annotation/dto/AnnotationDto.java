package com.example.blog.core.annotation.dto;

import com.example.blog.core.annotation.AdminCallable;
import com.example.blog.core.annotation.AnonymousCallable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AnnotationDto {

    private AnonymousCallable anonymousCallable;
    private AdminCallable adminCallable;

    public AnnotationDto(Method method) {
        this.anonymousCallable = method.getDeclaredAnnotation(AnonymousCallable.class);
        this.adminCallable = method.getDeclaredAnnotation(AdminCallable.class);
    }

    public boolean isAnonymousCallable() {
        return is(this.anonymousCallable);
    }

    public boolean isAdminCallable() {
        return is(this.adminCallable);
    }

    private boolean is(Annotation annotation) {
        return Objects.nonNull(annotation);
    }
}
