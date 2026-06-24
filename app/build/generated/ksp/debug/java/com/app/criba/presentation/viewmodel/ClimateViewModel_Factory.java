package com.app.criba.presentation.viewmodel;

import androidx.lifecycle.SavedStateHandle;
import com.app.criba.domain.repository.ClimateRepository;
import com.app.criba.domain.usecase.RecordClimateUseCase;
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
public final class ClimateViewModel_Factory implements Factory<ClimateViewModel> {
  private final Provider<ClimateRepository> climateRepositoryProvider;

  private final Provider<RecordClimateUseCase> recordClimateUseCaseProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public ClimateViewModel_Factory(Provider<ClimateRepository> climateRepositoryProvider,
      Provider<RecordClimateUseCase> recordClimateUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.climateRepositoryProvider = climateRepositoryProvider;
    this.recordClimateUseCaseProvider = recordClimateUseCaseProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public ClimateViewModel get() {
    return newInstance(climateRepositoryProvider.get(), recordClimateUseCaseProvider.get(), savedStateHandleProvider.get());
  }

  public static ClimateViewModel_Factory create(
      Provider<ClimateRepository> climateRepositoryProvider,
      Provider<RecordClimateUseCase> recordClimateUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new ClimateViewModel_Factory(climateRepositoryProvider, recordClimateUseCaseProvider, savedStateHandleProvider);
  }

  public static ClimateViewModel newInstance(ClimateRepository climateRepository,
      RecordClimateUseCase recordClimateUseCase, SavedStateHandle savedStateHandle) {
    return new ClimateViewModel(climateRepository, recordClimateUseCase, savedStateHandle);
  }
}
