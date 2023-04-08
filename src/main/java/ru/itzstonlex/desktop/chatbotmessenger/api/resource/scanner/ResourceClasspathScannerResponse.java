package ru.itzstonlex.desktop.chatbotmessenger.api.resource.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResourceClasspathScannerResponse {

  @Getter
  private final String packageName;

  private final Collection<Class<?>> response;

  public Collection<Class<?>> getClasses() {
    return response;
  }

  public Collection<Class<?>> getClasses(@NonNull Class<?> assignedType) {
    return response.stream().filter(assignedType::isAssignableFrom).collect(Collectors.toSet());
  }

  public Collection<Class<?>> getClassesByAnnotation(@NonNull Class<? extends Annotation> annotation) {
    return response.stream().filter(cls -> cls.isAnnotationPresent(annotation)).collect(Collectors.toSet());
  }

  public Collection<Field> getAccessedFields() {
    return response.stream().flatMap(cls -> Arrays.stream(cls.getDeclaredFields())).peek(field -> field.setAccessible(true))
        .collect(Collectors.toSet());
  }

  public Collection<Field> getAccessedFields(@NonNull Class<?> assignedType) {
    return getAccessedFields().stream().filter(field -> assignedType.isAssignableFrom(field.getType()))
        .collect(Collectors.toSet());
  }

  public Collection<Field> getAccessedFieldsByAnnotation(@NonNull Class<? extends Annotation> annotation) {
    return getAccessedFields().stream().filter(field -> field.isAnnotationPresent(annotation))
        .collect(Collectors.toSet());
  }

  public Collection<Field> getAccessedFieldsByAnnotation(@NonNull Class<?> assignedType, @NonNull Class<? extends Annotation> annotation) {
    return getAccessedFields(assignedType).stream().filter(field -> field.isAnnotationPresent(annotation))
        .collect(Collectors.toSet());
  }

  public Collection<Method> getAccessedMethods() {
    return response.stream().flatMap(cls -> Arrays.stream(cls.getDeclaredMethods())).collect(Collectors.toSet());
  }

  public Collection<Method> getAccessedMethodsByAnnotation(@NonNull Class<? extends Annotation> annotation) {
    return getAccessedMethods().stream().filter(method -> method.isAnnotationPresent(annotation))
        .collect(Collectors.toSet());
  }
}
