package com.app.criba.presentation.viewmodel;

import androidx.lifecycle.SavedStateHandle;
import com.app.criba.domain.repository.TransactionRepository;
import com.app.criba.domain.usecase.AddTransactionUseCase;
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
public final class FinanceViewModel_Factory implements Factory<FinanceViewModel> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<AddTransactionUseCase> addTransactionUseCaseProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public FinanceViewModel_Factory(Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<AddTransactionUseCase> addTransactionUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.addTransactionUseCaseProvider = addTransactionUseCaseProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public FinanceViewModel get() {
    return newInstance(transactionRepositoryProvider.get(), addTransactionUseCaseProvider.get(), savedStateHandleProvider.get());
  }

  public static FinanceViewModel_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<AddTransactionUseCase> addTransactionUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new FinanceViewModel_Factory(transactionRepositoryProvider, addTransactionUseCaseProvider, savedStateHandleProvider);
  }

  public static FinanceViewModel newInstance(TransactionRepository transactionRepository,
      AddTransactionUseCase addTransactionUseCase, SavedStateHandle savedStateHandle) {
    return new FinanceViewModel(transactionRepository, addTransactionUseCase, savedStateHandle);
  }
}
