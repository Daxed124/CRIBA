package com.app.criba.domain.usecase;

import com.app.criba.domain.repository.TerrainRepository;
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
public final class GetTerrainsUseCase_Factory implements Factory<GetTerrainsUseCase> {
  private final Provider<TerrainRepository> terrainRepositoryProvider;

  public GetTerrainsUseCase_Factory(Provider<TerrainRepository> terrainRepositoryProvider) {
    this.terrainRepositoryProvider = terrainRepositoryProvider;
  }

  @Override
  public GetTerrainsUseCase get() {
    return newInstance(terrainRepositoryProvider.get());
  }

  public static GetTerrainsUseCase_Factory create(
      Provider<TerrainRepository> terrainRepositoryProvider) {
    return new GetTerrainsUseCase_Factory(terrainRepositoryProvider);
  }

  public static GetTerrainsUseCase newInstance(TerrainRepository terrainRepository) {
    return new GetTerrainsUseCase(terrainRepository);
  }
}
