package com.app.criba.di;

import com.app.criba.data.local.CribaDatabase;
import com.app.criba.data.local.dao.TransactionDao;
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
public final class DatabaseModule_ProvideTransactionDaoFactory implements Factory<TransactionDao> {
  private final Provider<CribaDatabase> dbProvider;

  public DatabaseModule_ProvideTransactionDaoFactory(Provider<CribaDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public TransactionDao get() {
    return provideTransactionDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideTransactionDaoFactory create(
      Provider<CribaDatabase> dbProvider) {
    return new DatabaseModule_ProvideTransactionDaoFactory(dbProvider);
  }

  public static TransactionDao provideTransactionDao(CribaDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTransactionDao(db));
  }
}
