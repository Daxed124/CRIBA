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
public final class UpdateCycleStateUseCase_Factory implements Factory<UpdateCycleStateUseCase> {
  private final Provider<CycleRepository> cycleRepositoryProvider;

  public UpdateCycleStateUseCase_Factory(Provider<CycleRepository> cycleRepositoryProvider) {
    this.cycleRepositoryProvider = cycleRepositoryProvider;
  }

  @Override
  public UpdateCycleStateUseCase get() {
    return newInstance(cycleRepositoryProvider.get());
  }

  public static UpdateCycleStateUseCase_Factory create(
      Provider<CycleRepository> cycleRepositoryProvider) {
    return new UpdateCycleStateUseCase_Factory(cycleRepositoryProvider);
  }

  public static UpdateCycleStateUseCase newInstance(CycleRepository cycleRepository) {
    return new UpdateCycleStateUseCase(cycleRepository);
  }
}
