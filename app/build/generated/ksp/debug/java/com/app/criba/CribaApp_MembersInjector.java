package com.app.criba;

import androidx.hilt.work.HiltWorkerFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class CribaApp_MembersInjector implements MembersInjector<CribaApp> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public CribaApp_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<CribaApp> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new CribaApp_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(CribaApp instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.app.criba.CribaApp.workerFactory")
  public static void injectWorkerFactory(CribaApp instance, HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
