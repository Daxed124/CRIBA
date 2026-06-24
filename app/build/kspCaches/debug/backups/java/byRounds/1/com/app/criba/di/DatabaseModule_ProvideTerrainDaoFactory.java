package com.app.criba.di;

import com.app.criba.data.local.CribaDatabase;
import com.app.criba.data.local.dao.TerrainDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideTerrainDaoFactory implements Factory<TerrainDao> {
  private final Provider<CribaDatabase> dbProvider;

  public DatabaseModule_ProvideTerrainDaoFactory(Provider<CribaDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public TerrainDao get() {
    return provideTerrainDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideTerrainDaoFactory create(Provider<CribaDatabase> dbProvider) {
    return new DatabaseModule_ProvideTerrainDaoFactory(dbProvider);
  }

  public static TerrainDao provideTerrainDao(CribaDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTerrainDao(db));
  }
}
