package com.app.criba.domain.usecase;

import com.app.criba.domain.repository.SyncRepository;
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
public final class SyncDataUseCase_Factory implements Factory<SyncDataUseCase> {
  private final Provider<SyncRepository> syncRepositoryProvider;

  public SyncDataUseCase_Factory(Provider<SyncRepository> syncRepositoryProvider) {
    this.syncRepositoryProvider = syncRepositoryProvider;
  }

  @Override
  public SyncDataUseCase get() {
    return newInstance(syncRepositoryProvider.get());
  }

  public static SyncDataUseCase_Factory create(Provider<SyncRepository> syncRepositoryProvider) {
    return new SyncDataUseCase_Factory(syncRepositoryProvider);
  }

  public static SyncDataUseCase newInstance(SyncRepository syncRepository) {
    return new SyncDataUseCase(syncRepository);
  }
}
