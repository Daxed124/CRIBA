package com.app.criba.di;

import com.app.criba.data.local.CribaDatabase;
import com.app.criba.data.local.dao.CycleDao;
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
public final class DatabaseModule_ProvideCycleDaoFactory implements Factory<CycleDao> {
  private final Provider<CribaDatabase> dbProvider;

  public DatabaseModule_ProvideCycleDaoFactory(Provider<CribaDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CycleDao get() {
    return provideCycleDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideCycleDaoFactory create(Provider<CribaDatabase> dbProvider) {
    return new DatabaseModule_ProvideCycleDaoFactory(dbProvider);
  }

  public static CycleDao provideCycleDao(CribaDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCycleDao(db));
  }
}
