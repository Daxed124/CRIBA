package com.app.criba.domain.usecase;

import com.app.criba.domain.repository.AuthRepository;
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
public final class LoginWithGoogleUseCase_Factory implements Factory<LoginWithGoogleUseCase> {
  private final Provider<AuthRepository> authRepositoryProvider;

  public LoginWithGoogleUseCase_Factory(Provider<AuthRepository> authRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public LoginWithGoogleUseCase get() {
    return newInstance(authRepositoryProvider.get());
  }

  public static LoginWithGoogleUseCase_Factory create(
      Provider<AuthRepository> authRepositoryProvider) {
    return new LoginWithGoogleUseCase_Factory(authRepositoryProvider);
  }

  public static LoginWithGoogleUseCase newInstance(AuthRepository authRepository) {
    return new LoginWithGoogleUseCase(authRepository);
  }
}
