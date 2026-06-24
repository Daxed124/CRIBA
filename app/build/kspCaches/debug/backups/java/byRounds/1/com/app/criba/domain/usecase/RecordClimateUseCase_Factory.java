package com.app.criba.domain.usecase;

import com.app.criba.domain.repository.ClimateRepository;
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
public final class RecordClimateUseCase_Factory implements Factory<RecordClimateUseCase> {
  private final Provider<ClimateRepository> climateRepositoryProvider;

  public RecordClimateUseCase_Factory(Provider<ClimateRepository> climateRepositoryProvider) {
    this.climateRepositoryProvider = climateRepositoryProvider;
  }

  @Override
  public RecordClimateUseCase get() {
    return newInstance(climateRepositoryProvider.get());
  }

  public static RecordClimateUseCase_Factory create(
      Provider<ClimateRepository> climateRepositoryProvider) {
    return new RecordClimateUseCase_Factory(climateRepositoryProvider);
  }

  public static RecordClimateUseCase newInstance(ClimateRepository climateRepository) {
    return new RecordClimateUseCase(climateRepository);
  }
}
