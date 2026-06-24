package com.app.criba.domain.usecase;

import com.app.criba.domain.repository.PestRepository;
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
public final class ReportPestUseCase_Factory implements Factory<ReportPestUseCase> {
  private final Provider<PestRepository> pestRepositoryProvider;

  public ReportPestUseCase_Factory(Provider<PestRepository> pestRepositoryProvider) {
    this.pestRepositoryProvider = pestRepositoryProvider;
  }

  @Override
  public ReportPestUseCase get() {
    return newInstance(pestRepositoryProvider.get());
  }

  public static ReportPestUseCase_Factory create(Provider<PestRepository> pestRepositoryProvider) {
    return new ReportPestUseCase_Factory(pestRepositoryProvider);
  }

  public static ReportPestUseCase newInstance(PestRepository pestRepository) {
    return new ReportPestUseCase(pestRepository);
  }
}
