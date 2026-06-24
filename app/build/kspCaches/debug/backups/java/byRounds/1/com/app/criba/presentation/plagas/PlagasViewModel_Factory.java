package com.app.criba.presentation.plagas;

import com.app.criba.domain.repository.PestRepository;
import com.app.criba.util.LocationHelper;
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
public final class PlagasViewModel_Factory implements Factory<PlagasViewModel> {
  private final Provider<PestRepository> plagasRepoProvider;

  private final Provider<LocationHelper> locationHelperProvider;

  public PlagasViewModel_Factory(Provider<PestRepository> plagasRepoProvider,
      Provider<LocationHelper> locationHelperProvider) {
    this.plagasRepoProvider = plagasRepoProvider;
    this.locationHelperProvider = locationHelperProvider;
  }

  @Override
  public PlagasViewModel get() {
    return newInstance(plagasRepoProvider.get(), locationHelperProvider.get());
  }

  public static PlagasViewModel_Factory create(Provider<PestRepository> plagasRepoProvider,
      Provider<LocationHelper> locationHelperProvider) {
    return new PlagasViewModel_Factory(plagasRepoProvider, locationHelperProvider);
  }

  public static PlagasViewModel newInstance(PestRepository plagasRepo,
      LocationHelper locationHelper) {
    return new PlagasViewModel(plagasRepo, locationHelper);
  }
}
