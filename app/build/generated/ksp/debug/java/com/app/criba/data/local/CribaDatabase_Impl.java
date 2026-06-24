package com.app.criba.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.app.criba.data.local.dao.ClimateDao;
import com.app.criba.data.local.dao.ClimateDao_Impl;
import com.app.criba.data.local.dao.CycleDao;
import com.app.criba.data.local.dao.CycleDao_Impl;
import com.app.criba.data.local.dao.PestDao;
import com.app.criba.data.local.dao.PestDao_Impl;
import com.app.criba.data.local.dao.TerrainDao;
import com.app.criba.data.local.dao.TerrainDao_Impl;
import com.app.criba.data.local.dao.TransactionDao;
import com.app.criba.data.local.dao.TransactionDao_Impl;
import com.app.criba.data.local.dao.UserDao;
import com.app.criba.data.local.dao.UserDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CribaDatabase_Impl extends CribaDatabase {
  private volatile TerrainDao _terrainDao;

  private volatile CycleDao _cycleDao;

  private volatile TransactionDao _transactionDao;

  private volatile PestDao _pestDao;

  private volatile ClimateDao _climateDao;

  private volatile UserDao _userDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `terrains` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `surface` REAL NOT NULL, `soilType` TEXT NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `userId` TEXT NOT NULL, `isSynced` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `crop_cycles` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `terrainId` INTEGER NOT NULL, `species` TEXT NOT NULL, `startDate` INTEGER NOT NULL, `endDate` INTEGER, `phenologicalState` TEXT NOT NULL, `harvestedVolumeKg` REAL, `isSynced` INTEGER NOT NULL, FOREIGN KEY(`terrainId`) REFERENCES `terrains`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_crop_cycles_terrainId` ON `crop_cycles` (`terrainId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `transactions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `cycleId` INTEGER NOT NULL, `type` TEXT NOT NULL, `amount` REAL NOT NULL, `category` TEXT, `description` TEXT NOT NULL, `date` INTEGER NOT NULL, `isSynced` INTEGER NOT NULL, FOREIGN KEY(`cycleId`) REFERENCES `crop_cycles`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_transactions_cycleId` ON `transactions` (`cycleId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `pest_incidents` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `cycleId` INTEGER NOT NULL, `name` TEXT NOT NULL, `severity` TEXT NOT NULL, `description` TEXT NOT NULL, `photoLocalUri` TEXT, `latitude` REAL, `longitude` REAL, `date` INTEGER NOT NULL, `isSynced` INTEGER NOT NULL, FOREIGN KEY(`cycleId`) REFERENCES `crop_cycles`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_pest_incidents_cycleId` ON `pest_incidents` (`cycleId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `climate_records` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `cycleId` INTEGER NOT NULL, `rainfall` REAL NOT NULL, `temperature` REAL NOT NULL, `droughtStage` TEXT NOT NULL, `date` INTEGER NOT NULL, `isSynced` INTEGER NOT NULL, FOREIGN KEY(`cycleId`) REFERENCES `crop_cycles`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_climate_records_cycleId` ON `climate_records` (`cycleId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`id` TEXT NOT NULL, `email` TEXT NOT NULL, `passwordHash` TEXT, `displayName` TEXT NOT NULL, `photoUrl` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'c09f00a025c9e3d7b02b27238cf30f6b')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `terrains`");
        db.execSQL("DROP TABLE IF EXISTS `crop_cycles`");
        db.execSQL("DROP TABLE IF EXISTS `transactions`");
        db.execSQL("DROP TABLE IF EXISTS `pest_incidents`");
        db.execSQL("DROP TABLE IF EXISTS `climate_records`");
        db.execSQL("DROP TABLE IF EXISTS `users`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsTerrains = new HashMap<String, TableInfo.Column>(8);
        _columnsTerrains.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTerrains.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTerrains.put("surface", new TableInfo.Column("surface", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTerrains.put("soilType", new TableInfo.Column("soilType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTerrains.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTerrains.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTerrains.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTerrains.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTerrains = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTerrains = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTerrains = new TableInfo("terrains", _columnsTerrains, _foreignKeysTerrains, _indicesTerrains);
        final TableInfo _existingTerrains = TableInfo.read(db, "terrains");
        if (!_infoTerrains.equals(_existingTerrains)) {
          return new RoomOpenHelper.ValidationResult(false, "terrains(com.app.criba.data.local.entity.TerrainEntity).\n"
                  + " Expected:\n" + _infoTerrains + "\n"
                  + " Found:\n" + _existingTerrains);
        }
        final HashMap<String, TableInfo.Column> _columnsCropCycles = new HashMap<String, TableInfo.Column>(8);
        _columnsCropCycles.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCropCycles.put("terrainId", new TableInfo.Column("terrainId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCropCycles.put("species", new TableInfo.Column("species", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCropCycles.put("startDate", new TableInfo.Column("startDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCropCycles.put("endDate", new TableInfo.Column("endDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCropCycles.put("phenologicalState", new TableInfo.Column("phenologicalState", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCropCycles.put("harvestedVolumeKg", new TableInfo.Column("harvestedVolumeKg", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCropCycles.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCropCycles = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCropCycles.add(new TableInfo.ForeignKey("terrains", "CASCADE", "NO ACTION", Arrays.asList("terrainId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesCropCycles = new HashSet<TableInfo.Index>(1);
        _indicesCropCycles.add(new TableInfo.Index("index_crop_cycles_terrainId", false, Arrays.asList("terrainId"), Arrays.asList("ASC")));
        final TableInfo _infoCropCycles = new TableInfo("crop_cycles", _columnsCropCycles, _foreignKeysCropCycles, _indicesCropCycles);
        final TableInfo _existingCropCycles = TableInfo.read(db, "crop_cycles");
        if (!_infoCropCycles.equals(_existingCropCycles)) {
          return new RoomOpenHelper.ValidationResult(false, "crop_cycles(com.app.criba.data.local.entity.CycleEntity).\n"
                  + " Expected:\n" + _infoCropCycles + "\n"
                  + " Found:\n" + _existingCropCycles);
        }
        final HashMap<String, TableInfo.Column> _columnsTransactions = new HashMap<String, TableInfo.Column>(8);
        _columnsTransactions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("cycleId", new TableInfo.Column("cycleId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("category", new TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTransactions = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTransactions.add(new TableInfo.ForeignKey("crop_cycles", "CASCADE", "NO ACTION", Arrays.asList("cycleId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesTransactions = new HashSet<TableInfo.Index>(1);
        _indicesTransactions.add(new TableInfo.Index("index_transactions_cycleId", false, Arrays.asList("cycleId"), Arrays.asList("ASC")));
        final TableInfo _infoTransactions = new TableInfo("transactions", _columnsTransactions, _foreignKeysTransactions, _indicesTransactions);
        final TableInfo _existingTransactions = TableInfo.read(db, "transactions");
        if (!_infoTransactions.equals(_existingTransactions)) {
          return new RoomOpenHelper.ValidationResult(false, "transactions(com.app.criba.data.local.entity.TransactionEntity).\n"
                  + " Expected:\n" + _infoTransactions + "\n"
                  + " Found:\n" + _existingTransactions);
        }
        final HashMap<String, TableInfo.Column> _columnsPestIncidents = new HashMap<String, TableInfo.Column>(10);
        _columnsPestIncidents.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPestIncidents.put("cycleId", new TableInfo.Column("cycleId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPestIncidents.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPestIncidents.put("severity", new TableInfo.Column("severity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPestIncidents.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPestIncidents.put("photoLocalUri", new TableInfo.Column("photoLocalUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPestIncidents.put("latitude", new TableInfo.Column("latitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPestIncidents.put("longitude", new TableInfo.Column("longitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPestIncidents.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPestIncidents.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPestIncidents = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysPestIncidents.add(new TableInfo.ForeignKey("crop_cycles", "CASCADE", "NO ACTION", Arrays.asList("cycleId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesPestIncidents = new HashSet<TableInfo.Index>(1);
        _indicesPestIncidents.add(new TableInfo.Index("index_pest_incidents_cycleId", false, Arrays.asList("cycleId"), Arrays.asList("ASC")));
        final TableInfo _infoPestIncidents = new TableInfo("pest_incidents", _columnsPestIncidents, _foreignKeysPestIncidents, _indicesPestIncidents);
        final TableInfo _existingPestIncidents = TableInfo.read(db, "pest_incidents");
        if (!_infoPestIncidents.equals(_existingPestIncidents)) {
          return new RoomOpenHelper.ValidationResult(false, "pest_incidents(com.app.criba.data.local.entity.PestEntity).\n"
                  + " Expected:\n" + _infoPestIncidents + "\n"
                  + " Found:\n" + _existingPestIncidents);
        }
        final HashMap<String, TableInfo.Column> _columnsClimateRecords = new HashMap<String, TableInfo.Column>(7);
        _columnsClimateRecords.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClimateRecords.put("cycleId", new TableInfo.Column("cycleId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClimateRecords.put("rainfall", new TableInfo.Column("rainfall", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClimateRecords.put("temperature", new TableInfo.Column("temperature", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClimateRecords.put("droughtStage", new TableInfo.Column("droughtStage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClimateRecords.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClimateRecords.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysClimateRecords = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysClimateRecords.add(new TableInfo.ForeignKey("crop_cycles", "CASCADE", "NO ACTION", Arrays.asList("cycleId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesClimateRecords = new HashSet<TableInfo.Index>(1);
        _indicesClimateRecords.add(new TableInfo.Index("index_climate_records_cycleId", false, Arrays.asList("cycleId"), Arrays.asList("ASC")));
        final TableInfo _infoClimateRecords = new TableInfo("climate_records", _columnsClimateRecords, _foreignKeysClimateRecords, _indicesClimateRecords);
        final TableInfo _existingClimateRecords = TableInfo.read(db, "climate_records");
        if (!_infoClimateRecords.equals(_existingClimateRecords)) {
          return new RoomOpenHelper.ValidationResult(false, "climate_records(com.app.criba.data.local.entity.ClimateEntity).\n"
                  + " Expected:\n" + _infoClimateRecords + "\n"
                  + " Found:\n" + _existingClimateRecords);
        }
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(5);
        _columnsUsers.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("passwordHash", new TableInfo.Column("passwordHash", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("displayName", new TableInfo.Column("displayName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("photoUrl", new TableInfo.Column("photoUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.app.criba.data.local.entity.UserEntity).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "c09f00a025c9e3d7b02b27238cf30f6b", "d2a3fa0607615965dc4c08cef631ab28");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "terrains","crop_cycles","transactions","pest_incidents","climate_records","users");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `terrains`");
      _db.execSQL("DELETE FROM `crop_cycles`");
      _db.execSQL("DELETE FROM `transactions`");
      _db.execSQL("DELETE FROM `pest_incidents`");
      _db.execSQL("DELETE FROM `climate_records`");
      _db.execSQL("DELETE FROM `users`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(TerrainDao.class, TerrainDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CycleDao.class, CycleDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TransactionDao.class, TransactionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PestDao.class, PestDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ClimateDao.class, ClimateDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public TerrainDao terrainDao() {
    if (_terrainDao != null) {
      return _terrainDao;
    } else {
      synchronized(this) {
        if(_terrainDao == null) {
          _terrainDao = new TerrainDao_Impl(this);
        }
        return _terrainDao;
      }
    }
  }

  @Override
  public CycleDao cycleDao() {
    if (_cycleDao != null) {
      return _cycleDao;
    } else {
      synchronized(this) {
        if(_cycleDao == null) {
          _cycleDao = new CycleDao_Impl(this);
        }
        return _cycleDao;
      }
    }
  }

  @Override
  public TransactionDao transactionDao() {
    if (_transactionDao != null) {
      return _transactionDao;
    } else {
      synchronized(this) {
        if(_transactionDao == null) {
          _transactionDao = new TransactionDao_Impl(this);
        }
        return _transactionDao;
      }
    }
  }

  @Override
  public PestDao pestDao() {
    if (_pestDao != null) {
      return _pestDao;
    } else {
      synchronized(this) {
        if(_pestDao == null) {
          _pestDao = new PestDao_Impl(this);
        }
        return _pestDao;
      }
    }
  }

  @Override
  public ClimateDao climateDao() {
    if (_climateDao != null) {
      return _climateDao;
    } else {
      synchronized(this) {
        if(_climateDao == null) {
          _climateDao = new ClimateDao_Impl(this);
        }
        return _climateDao;
      }
    }
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }
}
