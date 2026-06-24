package com.app.criba.presentation.viewmodel;

import com.app.criba.domain.repository.AuthRepository;
import com.app.criba.domain.usecase.CreateTerrainUseCase;
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
public final class TerrainFormViewModel_Factory implements Factory<TerrainFormViewModel> {
  private final Provider<CreateTerrainUseCase> createTerrainUseCaseProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public TerrainFormViewModel_Factory(Provider<CreateTerrainUseCase> createTerrainUseCaseProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.createTerrainUseCaseProvider = createTerrainUseCaseProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public TerrainFormViewModel get() {
    return newInstance(createTerrainUseCaseProvider.get(), authRepositoryProvider.get());
  }

  public static TerrainFormViewModel_Factory create(
      Provider<CreateTerrainUseCase> createTerrainUseCaseProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new TerrainFormViewModel_Factory(createTerrainUseCaseProvider, authRepositoryProvider);
  }

  public static TerrainFormViewModel newInstance(CreateTerrainUseCase createTerrainUseCase,
      AuthRepository authRepository) {
    return new TerrainFormViewModel(createTerrainUseCase, authRepository);
  }
}
