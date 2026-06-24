package com.app.criba.presentation.viewmodel;

import com.app.criba.domain.usecase.GetTerrainsUseCase;
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
public final class MapViewModel_Factory implements Factory<MapViewModel> {
  private final Provider<GetTerrainsUseCase> getTerrainsUseCaseProvider;

  public MapViewModel_Factory(Provider<GetTerrainsUseCase> getTerrainsUseCaseProvider) {
    this.getTerrainsUseCaseProvider = getTerrainsUseCaseProvider;
  }

  @Override
  public MapViewModel get() {
    return newInstance(getTerrainsUseCaseProvider.get());
  }

  public static MapViewModel_Factory create(
      Provider<GetTerrainsUseCase> getTerrainsUseCaseProvider) {
    return new MapViewModel_Factory(getTerrainsUseCaseProvider);
  }

  public static MapViewModel newInstance(GetTerrainsUseCase getTerrainsUseCase) {
    return new MapViewModel(getTerrainsUseCase);
  }
}
