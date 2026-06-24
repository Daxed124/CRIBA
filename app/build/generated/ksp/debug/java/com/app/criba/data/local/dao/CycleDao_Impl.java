package com.app.criba.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.app.criba.data.local.entity.CycleEntity;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CycleDao_Impl implements CycleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CycleEntity> __insertionAdapterOfCycleEntity;

  private final EntityDeletionOrUpdateAdapter<CycleEntity> __updateAdapterOfCycleEntity;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsSynced;

  public CycleDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCycleEntity = new EntityInsertionAdapter<CycleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `crop_cycles` (`id`,`terrainId`,`species`,`startDate`,`endDate`,`phenologicalState`,`harvestedVolumeKg`,`isSynced`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CycleEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getTerrainId());
        statement.bindString(3, entity.getSpecies());
        statement.bindLong(4, entity.getStartDate());
        if (entity.getEndDate() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getEndDate());
        }
        statement.bindString(6, entity.getPhenologicalState());
        if (entity.getHarvestedVolumeKg() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getHarvestedVolumeKg());
        }
        final int _tmp = entity.isSynced() ? 1 : 0;
        statement.bindLong(8, _tmp);
      }
    };
    this.__updateAdapterOfCycleEntity = new EntityDeletionOrUpdateAdapter<CycleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `crop_cycles` SET `id` = ?,`terrainId` = ?,`species` = ?,`startDate` = ?,`endDate` = ?,`phenologicalState` = ?,`harvestedVolumeKg` = ?,`isSynced` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CycleEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getTerrainId());
        statement.bindString(3, entity.getSpecies());
        statement.bindLong(4, entity.getStartDate());
        if (entity.getEndDate() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getEndDate());
        }
        statement.bindString(6, entity.getPhenologicalState());
        if (entity.getHarvestedVolumeKg() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getHarvestedVolumeKg());
        }
        final int _tmp = entity.isSynced() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM crop_cycles WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkAsSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE crop_cycles SET isSynced = 1 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final CycleEntity cycle, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCycleEntity.insertAndReturnId(cycle);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final CycleEntity cycle, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCycleEntity.handle(cycle);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDelete.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markAsSynced(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsSynced.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkAsSynced.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CycleEntity>> getCyclesByTerrainId(final long terrainId) {
    final String _sql = "SELECT * FROM crop_cycles WHERE terrainId = ? ORDER BY startDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, terrainId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"crop_cycles"}, new Callable<List<CycleEntity>>() {
      @Override
      @NonNull
      public List<CycleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTerrainId = CursorUtil.getColumnIndexOrThrow(_cursor, "terrainId");
          final int _cursorIndexOfSpecies = CursorUtil.getColumnIndexOrThrow(_cursor, "species");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfPhenologicalState = CursorUtil.getColumnIndexOrThrow(_cursor, "phenologicalState");
          final int _cursorIndexOfHarvestedVolumeKg = CursorUtil.getColumnIndexOrThrow(_cursor, "harvestedVolumeKg");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<CycleEntity> _result = new ArrayList<CycleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CycleEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTerrainId;
            _tmpTerrainId = _cursor.getLong(_cursorIndexOfTerrainId);
            final String _tmpSpecies;
            _tmpSpecies = _cursor.getString(_cursorIndexOfSpecies);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final Long _tmpEndDate;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmpEndDate = null;
            } else {
              _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            }
            final String _tmpPhenologicalState;
            _tmpPhenologicalState = _cursor.getString(_cursorIndexOfPhenologicalState);
            final Double _tmpHarvestedVolumeKg;
            if (_cursor.isNull(_cursorIndexOfHarvestedVolumeKg)) {
              _tmpHarvestedVolumeKg = null;
            } else {
              _tmpHarvestedVolumeKg = _cursor.getDouble(_cursorIndexOfHarvestedVolumeKg);
            }
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _item = new CycleEntity(_tmpId,_tmpTerrainId,_tmpSpecies,_tmpStartDate,_tmpEndDate,_tmpPhenologicalState,_tmpHarvestedVolumeKg,_tmpIsSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getCycleById(final long id, final Continuation<? super CycleEntity> $completion) {
    final String _sql = "SELECT * FROM crop_cycles WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CycleEntity>() {
      @Override
      @Nullable
      public CycleEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTerrainId = CursorUtil.getColumnIndexOrThrow(_cursor, "terrainId");
          final int _cursorIndexOfSpecies = CursorUtil.getColumnIndexOrThrow(_cursor, "species");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfPhenologicalState = CursorUtil.getColumnIndexOrThrow(_cursor, "phenologicalState");
          final int _cursorIndexOfHarvestedVolumeKg = CursorUtil.getColumnIndexOrThrow(_cursor, "harvestedVolumeKg");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final CycleEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTerrainId;
            _tmpTerrainId = _cursor.getLong(_cursorIndexOfTerrainId);
            final String _tmpSpecies;
            _tmpSpecies = _cursor.getString(_cursorIndexOfSpecies);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final Long _tmpEndDate;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmpEndDate = null;
            } else {
              _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            }
            final String _tmpPhenologicalState;
            _tmpPhenologicalState = _cursor.getString(_cursorIndexOfPhenologicalState);
            final Double _tmpHarvestedVolumeKg;
            if (_cursor.isNull(_cursorIndexOfHarvestedVolumeKg)) {
              _tmpHarvestedVolumeKg = null;
            } else {
              _tmpHarvestedVolumeKg = _cursor.getDouble(_cursorIndexOfHarvestedVolumeKg);
            }
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _result = new CycleEntity(_tmpId,_tmpTerrainId,_tmpSpecies,_tmpStartDate,_tmpEndDate,_tmpPhenologicalState,_tmpHarvestedVolumeKg,_tmpIsSynced);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUnsyncedCycles(final Continuation<? super List<CycleEntity>> $completion) {
    final String _sql = "SELECT * FROM crop_cycles WHERE isSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CycleEntity>>() {
      @Override
      @NonNull
      public List<CycleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTerrainId = CursorUtil.getColumnIndexOrThrow(_cursor, "terrainId");
          final int _cursorIndexOfSpecies = CursorUtil.getColumnIndexOrThrow(_cursor, "species");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfPhenologicalState = CursorUtil.getColumnIndexOrThrow(_cursor, "phenologicalState");
          final int _cursorIndexOfHarvestedVolumeKg = CursorUtil.getColumnIndexOrThrow(_cursor, "harvestedVolumeKg");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<CycleEntity> _result = new ArrayList<CycleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CycleEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTerrainId;
            _tmpTerrainId = _cursor.getLong(_cursorIndexOfTerrainId);
            final String _tmpSpecies;
            _tmpSpecies = _cursor.getString(_cursorIndexOfSpecies);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final Long _tmpEndDate;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmpEndDate = null;
            } else {
              _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            }
            final String _tmpPhenologicalState;
            _tmpPhenologicalState = _cursor.getString(_cursorIndexOfPhenologicalState);
            final Double _tmpHarvestedVolumeKg;
            if (_cursor.isNull(_cursorIndexOfHarvestedVolumeKg)) {
              _tmpHarvestedVolumeKg = null;
            } else {
              _tmpHarvestedVolumeKg = _cursor.getDouble(_cursorIndexOfHarvestedVolumeKg);
            }
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _item = new CycleEntity(_tmpId,_tmpTerrainId,_tmpSpecies,_tmpStartDate,_tmpEndDate,_tmpPhenologicalState,_tmpHarvestedVolumeKg,_tmpIsSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
