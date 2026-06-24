package com.app.criba.data.repository;

import com.app.criba.data.local.dao.TerrainDao;
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
public final class TerrainRepositoryImpl_Factory implements Factory<TerrainRepositoryImpl> {
  private final Provider<TerrainDao> terrainDaoProvider;

  public TerrainRepositoryImpl_Factory(Provider<TerrainDao> terrainDaoProvider) {
    this.terrainDaoProvider = terrainDaoProvider;
  }

  @Override
  public TerrainRepositoryImpl get() {
    return newInstance(terrainDaoProvider.get());
  }

  public static TerrainRepositoryImpl_Factory create(Provider<TerrainDao> terrainDaoProvider) {
    return new TerrainRepositoryImpl_Factory(terrainDaoProvider);
  }

  public static TerrainRepositoryImpl newInstance(TerrainDao terrainDao) {
    return new TerrainRepositoryImpl(terrainDao);
  }
}
