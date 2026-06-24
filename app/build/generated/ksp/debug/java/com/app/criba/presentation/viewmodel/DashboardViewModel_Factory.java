package com.app.criba.presentation.viewmodel;

import com.app.criba.domain.repository.ClimateRepository;
import com.app.criba.domain.repository.CycleRepository;
import com.app.criba.domain.repository.PestRepository;
import com.app.criba.domain.repository.TerrainRepository;
import com.app.criba.domain.repository.TransactionRepository;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<TerrainRepository> terrainRepositoryProvider;

  private final Provider<CycleRepository> cycleRepositoryProvider;

  private final Provider<ClimateRepository> climateRepositoryProvider;

  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<PestRepository> pestRepositoryProvider;

  public DashboardViewModel_Factory(Provider<TerrainRepository> terrainRepositoryProvider,
      Provider<CycleRepository> cycleRepositoryProvider,
      Provider<ClimateRepository> climateRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<PestRepository> pestRepositoryProvider) {
    this.terrainRepositoryProvider = terrainRepositoryProvider;
    this.cycleRepositoryProvider = cycleRepositoryProvider;
    this.climateRepositoryProvider = climateRepositoryProvider;
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.pestRepositoryProvider = pestRepositoryProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(terrainRepositoryProvider.get(), cycleRepositoryProvider.get(), climateRepositoryProvider.get(), transactionRepositoryProvider.get(), pestRepositoryProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<TerrainRepository> terrainRepositoryProvider,
      Provider<CycleRepository> cycleRepositoryProvider,
      Provider<ClimateRepository> climateRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<PestRepository> pestRepositoryProvider) {
    return new DashboardViewModel_Factory(terrainRepositoryProvider, cycleRepositoryProvider, climateRepositoryProvider, transactionRepositoryProvider, pestRepositoryProvider);
  }

  public static DashboardViewModel newInstance(TerrainRepository terrainRepository,
      CycleRepository cycleRepository, ClimateRepository climateRepository,
      TransactionRepository transactionRepository, PestRepository pestRepository) {
    return new DashboardViewModel(terrainRepository, cycleRepository, climateRepository, transactionRepository, pestRepository);
  }
}
