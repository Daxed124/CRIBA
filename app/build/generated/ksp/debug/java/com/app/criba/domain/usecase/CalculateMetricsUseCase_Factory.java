package com.app.criba.domain.usecase;

import com.app.criba.domain.repository.TransactionRepository;
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
public final class CalculateMetricsUseCase_Factory implements Factory<CalculateMetricsUseCase> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  public CalculateMetricsUseCase_Factory(
      Provider<TransactionRepository> transactionRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
  }

  @Override
  public CalculateMetricsUseCase get() {
    return newInstance(transactionRepositoryProvider.get());
  }

  public static CalculateMetricsUseCase_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new CalculateMetricsUseCase_Factory(transactionRepositoryProvider);
  }

  public static CalculateMetricsUseCase newInstance(TransactionRepository transactionRepository) {
    return new CalculateMetricsUseCase(transactionRepository);
  }
}
