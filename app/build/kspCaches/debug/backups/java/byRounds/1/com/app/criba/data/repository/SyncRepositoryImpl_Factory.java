package com.app.criba.data.repository;

import com.app.criba.data.local.dao.ClimateDao;
import com.app.criba.data.local.dao.CycleDao;
import com.app.criba.data.local.dao.PestDao;
import com.app.criba.data.local.dao.TerrainDao;
import com.app.criba.data.local.dao.TransactionDao;
import com.app.criba.data.remote.ApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class SyncRepositoryImpl_Factory implements Factory<SyncRepositoryImpl> {
  private final Provider<ApiService> apiServiceProvider;

  private final Provider<TerrainDao> terrainDaoProvider;

  private final Provider<CycleDao> cycleDaoProvider;

  private final Provider<TransactionDao> transactionDaoProvider;

  private final Provider<PestDao> pestDaoProvider;

  private final Provider<ClimateDao> climateDaoProvider;

  public SyncRepositoryImpl_Factory(Provider<ApiService> apiServiceProvider,
      Provider<TerrainDao> terrainDaoProvider, Provider<CycleDao> cycleDaoProvider,
      Provider<TransactionDao> transactionDaoProvider, Provider<PestDao> pestDaoProvider,
      Provider<ClimateDao> climateDaoProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.terrainDaoProvider = terrainDaoProvider;
    this.cycleDaoProvider = cycleDaoProvider;
    this.transactionDaoProvider = transactionDaoProvider;
    this.pestDaoProvider = pestDaoProvider;
    this.climateDaoProvider = climateDaoProvider;
  }

  @Override
  public SyncRepositoryImpl get() {
    return newInstance(apiServiceProvider.get(), terrainDaoProvider.get(), cycleDaoProvider.get(), transactionDaoProvider.get(), pestDaoProvider.get(), climateDaoProvider.get());
  }

  public static SyncRepositoryImpl_Factory create(Provider<ApiService> apiServiceProvider,
      Provider<TerrainDao> terrainDaoProvider, Provider<CycleDao> cycleDaoProvider,
      Provider<TransactionDao> transactionDaoProvider, Provider<PestDao> pestDaoProvider,
      Provider<ClimateDao> climateDaoProvider) {
    return new SyncRepositoryImpl_Factory(apiServiceProvider, terrainDaoProvider, cycleDaoProvider, transactionDaoProvider, pestDaoProvider, climateDaoProvider);
  }

  public static SyncRepositoryImpl newInstance(ApiService apiService, TerrainDao terrainDao,
      CycleDao cycleDao, TransactionDao transactionDao, PestDao pestDao, ClimateDao climateDao) {
    return new SyncRepositoryImpl(apiService, terrainDao, cycleDao, transactionDao, pestDao, climateDao);
  }
}
