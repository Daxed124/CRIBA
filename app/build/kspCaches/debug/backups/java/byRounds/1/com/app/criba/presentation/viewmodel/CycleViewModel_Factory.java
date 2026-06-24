package com.app.criba.presentation.viewmodel;

import androidx.lifecycle.SavedStateHandle;
import com.app.criba.domain.repository.CycleRepository;
import com.app.criba.domain.usecase.CreateCycleUseCase;
import com.app.criba.domain.usecase.UpdateCycleStateUseCase;
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
public final class CycleViewModel_Factory implements Factory<CycleViewModel> {
  private final Provider<CycleRepository> cycleRepositoryProvider;

  private final Provider<CreateCycleUseCase> createCycleUseCaseProvider;

  private final Provider<UpdateCycleStateUseCase> updateCycleStateUseCaseProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public CycleViewModel_Factory(Provider<CycleRepository> cycleRepositoryProvider,
      Provider<CreateCycleUseCase> createCycleUseCaseProvider,
      Provider<UpdateCycleStateUseCase> updateCycleStateUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.cycleRepositoryProvider = cycleRepositoryProvider;
    this.createCycleUseCaseProvider = createCycleUseCaseProvider;
    this.updateCycleStateUseCaseProvider = updateCycleStateUseCaseProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public CycleViewModel get() {
    return newInstance(cycleRepositoryProvider.get(), createCycleUseCaseProvider.get(), updateCycleStateUseCaseProvider.get(), savedStateHandleProvider.get());
  }

  public static CycleViewModel_Factory create(Provider<CycleRepository> cycleRepositoryProvider,
      Provider<CreateCycleUseCase> createCycleUseCaseProvider,
      Provider<UpdateCycleStateUseCase> updateCycleStateUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new CycleViewModel_Factory(cycleRepositoryProvider, createCycleUseCaseProvider, updateCycleStateUseCaseProvider, savedStateHandleProvider);
  }

  public static CycleViewModel newInstance(CycleRepository cycleRepository,
      CreateCycleUseCase createCycleUseCase, UpdateCycleStateUseCase updateCycleStateUseCase,
      SavedStateHandle savedStateHandle) {
    return new CycleViewModel(cycleRepository, createCycleUseCase, updateCycleStateUseCase, savedStateHandle);
  }
}
