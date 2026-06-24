package com.app.criba.data.repository;

import com.app.criba.data.local.dao.ClimateDao;
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
public final class ClimateRepositoryImpl_Factory implements Factory<ClimateRepositoryImpl> {
  private final Provider<ClimateDao> climateDaoProvider;

  public ClimateRepositoryImpl_Factory(Provider<ClimateDao> climateDaoProvider) {
    this.climateDaoProvider = climateDaoProvider;
  }

  @Override
  public ClimateRepositoryImpl get() {
    return newInstance(climateDaoProvider.get());
  }

  public static ClimateRepositoryImpl_Factory create(Provider<ClimateDao> climateDaoProvider) {
    return new ClimateRepositoryImpl_Factory(climateDaoProvider);
  }

  public static ClimateRepositoryImpl newInstance(ClimateDao climateDao) {
    return new ClimateRepositoryImpl(climateDao);
  }
}
