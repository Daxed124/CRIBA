package com.app.criba.data.repository;

import android.content.Context;
import com.app.criba.data.local.dao.UserDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AuthRepositoryImpl_Factory implements Factory<AuthRepositoryImpl> {
  private final Provider<Context> contextProvider;

  private final Provider<UserDao> userDaoProvider;

  public AuthRepositoryImpl_Factory(Provider<Context> contextProvider,
      Provider<UserDao> userDaoProvider) {
    this.contextProvider = contextProvider;
    this.userDaoProvider = userDaoProvider;
  }

  @Override
  public AuthRepositoryImpl get() {
    return newInstance(contextProvider.get(), userDaoProvider.get());
  }

  public static AuthRepositoryImpl_Factory create(Provider<Context> contextProvider,
      Provider<UserDao> userDaoProvider) {
    return new AuthRepositoryImpl_Factory(contextProvider, userDaoProvider);
  }

  public static AuthRepositoryImpl newInstance(Context context, UserDao userDao) {
    return new AuthRepositoryImpl(context, userDao);
  }
}
