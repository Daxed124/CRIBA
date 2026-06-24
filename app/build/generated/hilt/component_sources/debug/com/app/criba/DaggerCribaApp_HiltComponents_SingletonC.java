package com.app.criba;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.app.criba.data.local.CribaDatabase;
import com.app.criba.data.local.dao.ClimateDao;
import com.app.criba.data.local.dao.CycleDao;
import com.app.criba.data.local.dao.PestDao;
import com.app.criba.data.local.dao.TerrainDao;
import com.app.criba.data.local.dao.TransactionDao;
import com.app.criba.data.local.dao.UserDao;
import com.app.criba.data.remote.ApiService;
import com.app.criba.data.repository.AuthRepositoryImpl;
import com.app.criba.data.repository.ClimateRepositoryImpl;
import com.app.criba.data.repository.CycleRepositoryImpl;
import com.app.criba.data.repository.PestRepositoryImpl;
import com.app.criba.data.repository.SyncRepositoryImpl;
import com.app.criba.data.repository.TerrainRepositoryImpl;
import com.app.criba.data.repository.TransactionRepositoryImpl;
import com.app.criba.data.worker.SyncWorker;
import com.app.criba.data.worker.SyncWorker_AssistedFactory;
import com.app.criba.di.DatabaseModule_ProvideClimateDaoFactory;
import com.app.criba.di.DatabaseModule_ProvideCycleDaoFactory;
import com.app.criba.di.DatabaseModule_ProvideDatabaseFactory;
import com.app.criba.di.DatabaseModule_ProvidePestDaoFactory;
import com.app.criba.di.DatabaseModule_ProvideTerrainDaoFactory;
import com.app.criba.di.DatabaseModule_ProvideTransactionDaoFactory;
import com.app.criba.di.DatabaseModule_ProvideUserDaoFactory;
import com.app.criba.di.NetworkModule_ProvideApiServiceFactory;
import com.app.criba.di.NetworkModule_ProvideOkHttpClientFactory;
import com.app.criba.di.NetworkModule_ProvideRetrofitFactory;
import com.app.criba.domain.usecase.AddTransactionUseCase;
import com.app.criba.domain.usecase.GetTerrainsUseCase;
import com.app.criba.domain.usecase.RecordClimateUseCase;
import com.app.criba.domain.usecase.ReportPestUseCase;
import com.app.criba.presentation.plagas.PlagasViewModel;
import com.app.criba.presentation.plagas.PlagasViewModel_HiltModules;
import com.app.criba.presentation.ui.MainActivity;
import com.app.criba.presentation.viewmodel.ClimateViewModel;
import com.app.criba.presentation.viewmodel.ClimateViewModel_HiltModules;
import com.app.criba.presentation.viewmodel.DashboardViewModel;
import com.app.criba.presentation.viewmodel.DashboardViewModel_HiltModules;
import com.app.criba.presentation.viewmodel.FinanceViewModel;
import com.app.criba.presentation.viewmodel.FinanceViewModel_HiltModules;
import com.app.criba.presentation.viewmodel.LoginViewModel;
import com.app.criba.presentation.viewmodel.LoginViewModel_HiltModules;
import com.app.criba.presentation.viewmodel.MapViewModel;
import com.app.criba.presentation.viewmodel.MapViewModel_HiltModules;
import com.app.criba.presentation.viewmodel.ParcelasViewModel;
import com.app.criba.presentation.viewmodel.ParcelasViewModel_HiltModules;
import com.app.criba.presentation.viewmodel.PestViewModel;
import com.app.criba.presentation.viewmodel.PestViewModel_HiltModules;
import com.app.criba.util.LocationHelper;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SingleCheck;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

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
public final class DaggerCribaApp_HiltComponents_SingletonC {
  private DaggerCribaApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public CribaApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements CribaApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public CribaApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements CribaApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public CribaApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements CribaApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public CribaApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements CribaApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public CribaApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements CribaApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public CribaApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements CribaApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public CribaApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements CribaApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public CribaApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends CribaApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends CribaApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends CribaApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends CribaApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(8).put(LazyClassKeyProvider.com_app_criba_presentation_viewmodel_ClimateViewModel, ClimateViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_criba_presentation_viewmodel_DashboardViewModel, DashboardViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_criba_presentation_viewmodel_FinanceViewModel, FinanceViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_criba_presentation_viewmodel_LoginViewModel, LoginViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_criba_presentation_viewmodel_MapViewModel, MapViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_criba_presentation_viewmodel_ParcelasViewModel, ParcelasViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_criba_presentation_viewmodel_PestViewModel, PestViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_app_criba_presentation_plagas_PlagasViewModel, PlagasViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_app_criba_presentation_viewmodel_LoginViewModel = "com.app.criba.presentation.viewmodel.LoginViewModel";

      static String com_app_criba_presentation_viewmodel_PestViewModel = "com.app.criba.presentation.viewmodel.PestViewModel";

      static String com_app_criba_presentation_viewmodel_DashboardViewModel = "com.app.criba.presentation.viewmodel.DashboardViewModel";

      static String com_app_criba_presentation_viewmodel_FinanceViewModel = "com.app.criba.presentation.viewmodel.FinanceViewModel";

      static String com_app_criba_presentation_plagas_PlagasViewModel = "com.app.criba.presentation.plagas.PlagasViewModel";

      static String com_app_criba_presentation_viewmodel_MapViewModel = "com.app.criba.presentation.viewmodel.MapViewModel";

      static String com_app_criba_presentation_viewmodel_ClimateViewModel = "com.app.criba.presentation.viewmodel.ClimateViewModel";

      static String com_app_criba_presentation_viewmodel_ParcelasViewModel = "com.app.criba.presentation.viewmodel.ParcelasViewModel";

      @KeepFieldType
      LoginViewModel com_app_criba_presentation_viewmodel_LoginViewModel2;

      @KeepFieldType
      PestViewModel com_app_criba_presentation_viewmodel_PestViewModel2;

      @KeepFieldType
      DashboardViewModel com_app_criba_presentation_viewmodel_DashboardViewModel2;

      @KeepFieldType
      FinanceViewModel com_app_criba_presentation_viewmodel_FinanceViewModel2;

      @KeepFieldType
      PlagasViewModel com_app_criba_presentation_plagas_PlagasViewModel2;

      @KeepFieldType
      MapViewModel com_app_criba_presentation_viewmodel_MapViewModel2;

      @KeepFieldType
      ClimateViewModel com_app_criba_presentation_viewmodel_ClimateViewModel2;

      @KeepFieldType
      ParcelasViewModel com_app_criba_presentation_viewmodel_ParcelasViewModel2;
    }
  }

  private static final class ViewModelCImpl extends CribaApp_HiltComponents.ViewModelC {
    private final SavedStateHandle savedStateHandle;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<ClimateViewModel> climateViewModelProvider;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<FinanceViewModel> financeViewModelProvider;

    private Provider<LoginViewModel> loginViewModelProvider;

    private Provider<MapViewModel> mapViewModelProvider;

    private Provider<ParcelasViewModel> parcelasViewModelProvider;

    private Provider<PestViewModel> pestViewModelProvider;

    private Provider<PlagasViewModel> plagasViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.savedStateHandle = savedStateHandleParam;
      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private RecordClimateUseCase recordClimateUseCase() {
      return new RecordClimateUseCase(singletonCImpl.climateRepositoryImplProvider.get());
    }

    private AddTransactionUseCase addTransactionUseCase() {
      return new AddTransactionUseCase(singletonCImpl.transactionRepositoryImplProvider.get());
    }

    private GetTerrainsUseCase getTerrainsUseCase() {
      return new GetTerrainsUseCase(singletonCImpl.terrainRepositoryImplProvider.get());
    }

    private ReportPestUseCase reportPestUseCase() {
      return new ReportPestUseCase(singletonCImpl.pestRepositoryImplProvider.get());
    }

    private LocationHelper locationHelper() {
      return new LocationHelper(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.climateViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.financeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.loginViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.mapViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.parcelasViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.pestViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.plagasViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(8).put(LazyClassKeyProvider.com_app_criba_presentation_viewmodel_ClimateViewModel, ((Provider) climateViewModelProvider)).put(LazyClassKeyProvider.com_app_criba_presentation_viewmodel_DashboardViewModel, ((Provider) dashboardViewModelProvider)).put(LazyClassKeyProvider.com_app_criba_presentation_viewmodel_FinanceViewModel, ((Provider) financeViewModelProvider)).put(LazyClassKeyProvider.com_app_criba_presentation_viewmodel_LoginViewModel, ((Provider) loginViewModelProvider)).put(LazyClassKeyProvider.com_app_criba_presentation_viewmodel_MapViewModel, ((Provider) mapViewModelProvider)).put(LazyClassKeyProvider.com_app_criba_presentation_viewmodel_ParcelasViewModel, ((Provider) parcelasViewModelProvider)).put(LazyClassKeyProvider.com_app_criba_presentation_viewmodel_PestViewModel, ((Provider) pestViewModelProvider)).put(LazyClassKeyProvider.com_app_criba_presentation_plagas_PlagasViewModel, ((Provider) plagasViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_app_criba_presentation_viewmodel_MapViewModel = "com.app.criba.presentation.viewmodel.MapViewModel";

      static String com_app_criba_presentation_plagas_PlagasViewModel = "com.app.criba.presentation.plagas.PlagasViewModel";

      static String com_app_criba_presentation_viewmodel_DashboardViewModel = "com.app.criba.presentation.viewmodel.DashboardViewModel";

      static String com_app_criba_presentation_viewmodel_ClimateViewModel = "com.app.criba.presentation.viewmodel.ClimateViewModel";

      static String com_app_criba_presentation_viewmodel_LoginViewModel = "com.app.criba.presentation.viewmodel.LoginViewModel";

      static String com_app_criba_presentation_viewmodel_FinanceViewModel = "com.app.criba.presentation.viewmodel.FinanceViewModel";

      static String com_app_criba_presentation_viewmodel_ParcelasViewModel = "com.app.criba.presentation.viewmodel.ParcelasViewModel";

      static String com_app_criba_presentation_viewmodel_PestViewModel = "com.app.criba.presentation.viewmodel.PestViewModel";

      @KeepFieldType
      MapViewModel com_app_criba_presentation_viewmodel_MapViewModel2;

      @KeepFieldType
      PlagasViewModel com_app_criba_presentation_plagas_PlagasViewModel2;

      @KeepFieldType
      DashboardViewModel com_app_criba_presentation_viewmodel_DashboardViewModel2;

      @KeepFieldType
      ClimateViewModel com_app_criba_presentation_viewmodel_ClimateViewModel2;

      @KeepFieldType
      LoginViewModel com_app_criba_presentation_viewmodel_LoginViewModel2;

      @KeepFieldType
      FinanceViewModel com_app_criba_presentation_viewmodel_FinanceViewModel2;

      @KeepFieldType
      ParcelasViewModel com_app_criba_presentation_viewmodel_ParcelasViewModel2;

      @KeepFieldType
      PestViewModel com_app_criba_presentation_viewmodel_PestViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.app.criba.presentation.viewmodel.ClimateViewModel 
          return (T) new ClimateViewModel(singletonCImpl.climateRepositoryImplProvider.get(), viewModelCImpl.recordClimateUseCase(), viewModelCImpl.savedStateHandle);

          case 1: // com.app.criba.presentation.viewmodel.DashboardViewModel 
          return (T) new DashboardViewModel(singletonCImpl.terrainRepositoryImplProvider.get(), singletonCImpl.cycleRepositoryImplProvider.get(), singletonCImpl.climateRepositoryImplProvider.get(), singletonCImpl.transactionRepositoryImplProvider.get(), singletonCImpl.pestRepositoryImplProvider.get());

          case 2: // com.app.criba.presentation.viewmodel.FinanceViewModel 
          return (T) new FinanceViewModel(singletonCImpl.transactionRepositoryImplProvider.get(), viewModelCImpl.addTransactionUseCase(), viewModelCImpl.savedStateHandle);

          case 3: // com.app.criba.presentation.viewmodel.LoginViewModel 
          return (T) new LoginViewModel(singletonCImpl.authRepositoryImplProvider.get());

          case 4: // com.app.criba.presentation.viewmodel.MapViewModel 
          return (T) new MapViewModel(viewModelCImpl.getTerrainsUseCase());

          case 5: // com.app.criba.presentation.viewmodel.ParcelasViewModel 
          return (T) new ParcelasViewModel(singletonCImpl.terrainRepositoryImplProvider.get(), singletonCImpl.cycleRepositoryImplProvider.get());

          case 6: // com.app.criba.presentation.viewmodel.PestViewModel 
          return (T) new PestViewModel(singletonCImpl.pestRepositoryImplProvider.get(), viewModelCImpl.reportPestUseCase(), viewModelCImpl.savedStateHandle);

          case 7: // com.app.criba.presentation.plagas.PlagasViewModel 
          return (T) new PlagasViewModel(singletonCImpl.pestRepositoryImplProvider.get(), viewModelCImpl.locationHelper());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends CribaApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends CribaApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends CribaApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<Retrofit> provideRetrofitProvider;

    private Provider<ApiService> provideApiServiceProvider;

    private Provider<CribaDatabase> provideDatabaseProvider;

    private Provider<SyncRepositoryImpl> syncRepositoryImplProvider;

    private Provider<SyncWorker_AssistedFactory> syncWorker_AssistedFactoryProvider;

    private Provider<ClimateRepositoryImpl> climateRepositoryImplProvider;

    private Provider<TerrainRepositoryImpl> terrainRepositoryImplProvider;

    private Provider<CycleRepositoryImpl> cycleRepositoryImplProvider;

    private Provider<TransactionRepositoryImpl> transactionRepositoryImplProvider;

    private Provider<PestRepositoryImpl> pestRepositoryImplProvider;

    private Provider<AuthRepositoryImpl> authRepositoryImplProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private TerrainDao terrainDao() {
      return DatabaseModule_ProvideTerrainDaoFactory.provideTerrainDao(provideDatabaseProvider.get());
    }

    private CycleDao cycleDao() {
      return DatabaseModule_ProvideCycleDaoFactory.provideCycleDao(provideDatabaseProvider.get());
    }

    private TransactionDao transactionDao() {
      return DatabaseModule_ProvideTransactionDaoFactory.provideTransactionDao(provideDatabaseProvider.get());
    }

    private PestDao pestDao() {
      return DatabaseModule_ProvidePestDaoFactory.providePestDao(provideDatabaseProvider.get());
    }

    private ClimateDao climateDao() {
      return DatabaseModule_ProvideClimateDaoFactory.provideClimateDao(provideDatabaseProvider.get());
    }

    private Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return Collections.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>singletonMap("com.app.criba.data.worker.SyncWorker", ((Provider) syncWorker_AssistedFactoryProvider));
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    private UserDao userDao() {
      return DatabaseModule_ProvideUserDaoFactory.provideUserDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 4));
      this.provideRetrofitProvider = DoubleCheck.provider(new SwitchingProvider<Retrofit>(singletonCImpl, 3));
      this.provideApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<ApiService>(singletonCImpl, 2));
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<CribaDatabase>(singletonCImpl, 5));
      this.syncRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<SyncRepositoryImpl>(singletonCImpl, 1));
      this.syncWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<SyncWorker_AssistedFactory>(singletonCImpl, 0));
      this.climateRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<ClimateRepositoryImpl>(singletonCImpl, 6));
      this.terrainRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<TerrainRepositoryImpl>(singletonCImpl, 7));
      this.cycleRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<CycleRepositoryImpl>(singletonCImpl, 8));
      this.transactionRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<TransactionRepositoryImpl>(singletonCImpl, 9));
      this.pestRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<PestRepositoryImpl>(singletonCImpl, 10));
      this.authRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<AuthRepositoryImpl>(singletonCImpl, 11));
    }

    @Override
    public void injectCribaApp(CribaApp cribaApp) {
      injectCribaApp2(cribaApp);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private CribaApp injectCribaApp2(CribaApp instance) {
      CribaApp_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.app.criba.data.worker.SyncWorker_AssistedFactory 
          return (T) new SyncWorker_AssistedFactory() {
            @Override
            public SyncWorker create(Context context, WorkerParameters workerParams) {
              return new SyncWorker(context, workerParams, singletonCImpl.syncRepositoryImplProvider.get());
            }
          };

          case 1: // com.app.criba.data.repository.SyncRepositoryImpl 
          return (T) new SyncRepositoryImpl(singletonCImpl.provideApiServiceProvider.get(), singletonCImpl.terrainDao(), singletonCImpl.cycleDao(), singletonCImpl.transactionDao(), singletonCImpl.pestDao(), singletonCImpl.climateDao());

          case 2: // com.app.criba.data.remote.ApiService 
          return (T) NetworkModule_ProvideApiServiceFactory.provideApiService(singletonCImpl.provideRetrofitProvider.get());

          case 3: // retrofit2.Retrofit 
          return (T) NetworkModule_ProvideRetrofitFactory.provideRetrofit(singletonCImpl.provideOkHttpClientProvider.get());

          case 4: // okhttp3.OkHttpClient 
          return (T) NetworkModule_ProvideOkHttpClientFactory.provideOkHttpClient();

          case 5: // com.app.criba.data.local.CribaDatabase 
          return (T) DatabaseModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 6: // com.app.criba.data.repository.ClimateRepositoryImpl 
          return (T) new ClimateRepositoryImpl(singletonCImpl.climateDao());

          case 7: // com.app.criba.data.repository.TerrainRepositoryImpl 
          return (T) new TerrainRepositoryImpl(singletonCImpl.terrainDao());

          case 8: // com.app.criba.data.repository.CycleRepositoryImpl 
          return (T) new CycleRepositoryImpl(singletonCImpl.cycleDao());

          case 9: // com.app.criba.data.repository.TransactionRepositoryImpl 
          return (T) new TransactionRepositoryImpl(singletonCImpl.transactionDao());

          case 10: // com.app.criba.data.repository.PestRepositoryImpl 
          return (T) new PestRepositoryImpl(singletonCImpl.pestDao());

          case 11: // com.app.criba.data.repository.AuthRepositoryImpl 
          return (T) new AuthRepositoryImpl(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.userDao());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
