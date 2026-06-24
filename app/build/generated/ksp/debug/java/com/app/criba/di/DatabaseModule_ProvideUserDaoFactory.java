package com.app.criba.di;

import com.app.criba.data.local.CribaDatabase;
import com.app.criba.data.local.dao.UserDao;
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
public final class DatabaseModule_ProvideUserDaoFactory implements Factory<UserDao> {
  private final Provider<CribaDatabase> dbProvider;

  public DatabaseModule_ProvideUserDaoFactory(Provider<CribaDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public UserDao get() {
    return provideUserDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideUserDaoFactory create(Provider<CribaDatabase> dbProvider) {
    return new DatabaseModule_ProvideUserDaoFactory(dbProvider);
  }

  public static UserDao provideUserDao(CribaDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideUserDao(db));
  }
}
