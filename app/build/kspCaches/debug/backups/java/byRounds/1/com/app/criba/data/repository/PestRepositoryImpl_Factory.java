package com.app.criba.data.repository;

import com.app.criba.data.local.dao.PestDao;
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
public final class PestRepositoryImpl_Factory implements Factory<PestRepositoryImpl> {
  private final Provider<PestDao> pestDaoProvider;

  public PestRepositoryImpl_Factory(Provider<PestDao> pestDaoProvider) {
    this.pestDaoProvider = pestDaoProvider;
  }

  @Override
  public PestRepositoryImpl get() {
    return newInstance(pestDaoProvider.get());
  }

  public static PestRepositoryImpl_Factory create(Provider<PestDao> pestDaoProvider) {
    return new PestRepositoryImpl_Factory(pestDaoProvider);
  }

  public static PestRepositoryImpl newInstance(PestDao pestDao) {
    return new PestRepositoryImpl(pestDao);
  }
}
