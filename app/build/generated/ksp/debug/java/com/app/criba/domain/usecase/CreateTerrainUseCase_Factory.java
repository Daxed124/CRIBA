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
public final class CreateTerrainUseCase_Factory implements Factory<CreateTerrainUseCase> {
  private final Provider<TerrainRepository> terrainRepositoryProvider;

  public CreateTerrainUseCase_Factory(Provider<TerrainRepository> terrainRepositoryProvider) {
    this.terrainRepositoryProvider = terrainRepositoryProvider;
  }

  @Override
  public CreateTerrainUseCase get() {
    return newInstance(terrainRepositoryProvider.get());
  }

  public static CreateTerrainUseCase_Factory create(
      Provider<TerrainRepository> terrainRepositoryProvider) {
    return new CreateTerrainUseCase_Factory(terrainRepositoryProvider);
  }

  public static CreateTerrainUseCase newInstance(TerrainRepository terrainRepository) {
    return new CreateTerrainUseCase(terrainRepository);
  }
}
