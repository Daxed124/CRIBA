package com.app.criba.presentation.viewmodel;

import com.app.criba.domain.repository.CycleRepository;
import com.app.criba.domain.repository.TerrainRepository;
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
public final class ParcelasViewModel_Factory implements Factory<ParcelasViewModel> {
  private final Provider<TerrainRepository> terrainRepositoryProvider;

  private final Provider<CycleRepository> cycleRepositoryProvider;

  public ParcelasViewModel_Factory(Provider<TerrainRepository> terrainRepositoryProvider,
      Provider<CycleRepository> cycleRepositoryProvider) {
    this.terrainRepositoryProvider = terrainRepositoryProvider;
    this.cycleRepositoryProvider = cycleRepositoryProvider;
  }

  @Override
  public ParcelasViewModel get() {
    return newInstance(terrainRepositoryProvider.get(), cycleRepositoryProvider.get());
  }

  public static ParcelasViewModel_Factory create(
      Provider<TerrainRepository> terrainRepositoryProvider,
      Provider<CycleRepository> cycleRepositoryProvider) {
    return new ParcelasViewModel_Factory(terrainRepositoryProvider, cycleRepositoryProvider);
  }

  public static ParcelasViewModel newInstance(TerrainRepository terrainRepository,
      CycleRepository cycleRepository) {
    return new ParcelasViewModel(terrainRepository, cycleRepository);
  }
}
