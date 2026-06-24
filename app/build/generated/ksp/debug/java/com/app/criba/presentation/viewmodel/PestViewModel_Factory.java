package com.app.criba.presentation.viewmodel;

import androidx.lifecycle.SavedStateHandle;
import com.app.criba.domain.repository.PestRepository;
import com.app.criba.domain.usecase.ReportPestUseCase;
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
public final class PestViewModel_Factory implements Factory<PestViewModel> {
  private final Provider<PestRepository> pestRepositoryProvider;

  private final Provider<ReportPestUseCase> reportPestUseCaseProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public PestViewModel_Factory(Provider<PestRepository> pestRepositoryProvider,
      Provider<ReportPestUseCase> reportPestUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.pestRepositoryProvider = pestRepositoryProvider;
    this.reportPestUseCaseProvider = reportPestUseCaseProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public PestViewModel get() {
    return newInstance(pestRepositoryProvider.get(), reportPestUseCaseProvider.get(), savedStateHandleProvider.get());
  }

  public static PestViewModel_Factory create(Provider<PestRepository> pestRepositoryProvider,
      Provider<ReportPestUseCase> reportPestUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new PestViewModel_Factory(pestRepositoryProvider, reportPestUseCaseProvider, savedStateHandleProvider);
  }

  public static PestViewModel newInstance(PestRepository pestRepository,
      ReportPestUseCase reportPestUseCase, SavedStateHandle savedStateHandle) {
    return new PestViewModel(pestRepository, reportPestUseCase, savedStateHandle);
  }
}
