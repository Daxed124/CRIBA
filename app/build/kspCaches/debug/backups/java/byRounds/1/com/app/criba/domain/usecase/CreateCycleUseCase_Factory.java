package com.app.criba.domain.usecase;

import com.app.criba.domain.repository.CycleRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class CreateCycleUseCase_Factory implements Factory<CreateCycleUseCase> {
  private final Provider<CycleRepository> cycleRepositoryProvider;

  public CreateCycleUseCase_Factory(Provider<CycleRepository> cycleRepositoryProvider) {
    this.cycleRepositoryProvider = cycleRepositoryProvider;
  }

  @Override
  public CreateCycleUseCase get() {
    return newInstance(cycleRepositoryProvider.get());
  }

  public static CreateCycleUseCase_Factory create(
      Provider<CycleRepository> cycleRepositoryProvider) {
    return new CreateCycleUseCase_Factory(cycleRepositoryProvider);
  }

  public static CreateCycleUseCase newInstance(CycleRepository cycleRepository) {
    return new CreateCycleUseCase(cycleRepository);
  }
}
