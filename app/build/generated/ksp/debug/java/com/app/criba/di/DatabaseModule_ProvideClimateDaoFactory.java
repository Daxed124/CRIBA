package com.app.criba.di;

import com.app.criba.data.local.CribaDatabase;
import com.app.criba.data.local.dao.ClimateDao;
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
public final class DatabaseModule_ProvideClimateDaoFactory implements Factory<ClimateDao> {
  private final Provider<CribaDatabase> dbProvider;

  public DatabaseModule_ProvideClimateDaoFactory(Provider<CribaDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ClimateDao get() {
    return provideClimateDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideClimateDaoFactory create(Provider<CribaDatabase> dbProvider) {
    return new DatabaseModule_ProvideClimateDaoFactory(dbProvider);
  }

  public static ClimateDao provideClimateDao(CribaDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideClimateDao(db));
  }
}
