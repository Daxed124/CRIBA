package com.app.criba.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.app.criba.data.local.entity.ClimateEntity;
import java.lang.Class;
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
public final class ClimateDao_Impl implements ClimateDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ClimateEntity> __insertionAdapterOfClimateEntity;

  private final EntityDeletionOrUpdateAdapter<ClimateEntity> __updateAdapterOfClimateEntity;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsSynced;

  public ClimateDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfClimateEntity = new EntityInsertionAdapter<ClimateEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `climate_records` (`id`,`cycleId`,`rainfall`,`temperature`,`droughtStage`,`date`,`isSynced`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ClimateEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCycleId());
        statement.bindDouble(3, entity.getRainfall());
        statement.bindDouble(4, entity.getTemperature());
        statement.bindString(5, entity.getDroughtStage());
        statement.bindLong(6, entity.getDate());
        final int _tmp = entity.isSynced() ? 1 : 0;
        statement.bindLong(7, _tmp);
      }
    };
    this.__updateAdapterOfClimateEntity = new EntityDeletionOrUpdateAdapter<ClimateEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `climate_records` SET `id` = ?,`cycleId` = ?,`rainfall` = ?,`temperature` = ?,`droughtStage` = ?,`date` = ?,`isSynced` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ClimateEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCycleId());
        statement.bindDouble(3, entity.getRainfall());
        statement.bindDouble(4, entity.getTemperature());
        statement.bindString(5, entity.getDroughtStage());
        statement.bindLong(6, entity.getDate());
        final int _tmp = entity.isSynced() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getId());
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM climate_records WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkAsSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE climate_records SET isSynced = 1 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final ClimateEntity record, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfClimateEntity.insertAndReturnId(record);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final ClimateEntity record, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfClimateEntity.handle(record);
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
  public Flow<List<ClimateEntity>> getClimateRecordsByCycleId(final long cycleId) {
    final String _sql = "SELECT * FROM climate_records WHERE cycleId = ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, cycleId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"climate_records"}, new Callable<List<ClimateEntity>>() {
      @Override
      @NonNull
      public List<ClimateEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCycleId = CursorUtil.getColumnIndexOrThrow(_cursor, "cycleId");
          final int _cursorIndexOfRainfall = CursorUtil.getColumnIndexOrThrow(_cursor, "rainfall");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfDroughtStage = CursorUtil.getColumnIndexOrThrow(_cursor, "droughtStage");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<ClimateEntity> _result = new ArrayList<ClimateEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ClimateEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCycleId;
            _tmpCycleId = _cursor.getLong(_cursorIndexOfCycleId);
            final double _tmpRainfall;
            _tmpRainfall = _cursor.getDouble(_cursorIndexOfRainfall);
            final double _tmpTemperature;
            _tmpTemperature = _cursor.getDouble(_cursorIndexOfTemperature);
            final String _tmpDroughtStage;
            _tmpDroughtStage = _cursor.getString(_cursorIndexOfDroughtStage);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _item = new ClimateEntity(_tmpId,_tmpCycleId,_tmpRainfall,_tmpTemperature,_tmpDroughtStage,_tmpDate,_tmpIsSynced);
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
  public Object getUnsyncedRecords(final Continuation<? super List<ClimateEntity>> $completion) {
    final String _sql = "SELECT * FROM climate_records WHERE isSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ClimateEntity>>() {
      @Override
      @NonNull
      public List<ClimateEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCycleId = CursorUtil.getColumnIndexOrThrow(_cursor, "cycleId");
          final int _cursorIndexOfRainfall = CursorUtil.getColumnIndexOrThrow(_cursor, "rainfall");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfDroughtStage = CursorUtil.getColumnIndexOrThrow(_cursor, "droughtStage");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<ClimateEntity> _result = new ArrayList<ClimateEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ClimateEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCycleId;
            _tmpCycleId = _cursor.getLong(_cursorIndexOfCycleId);
            final double _tmpRainfall;
            _tmpRainfall = _cursor.getDouble(_cursorIndexOfRainfall);
            final double _tmpTemperature;
            _tmpTemperature = _cursor.getDouble(_cursorIndexOfTemperature);
            final String _tmpDroughtStage;
            _tmpDroughtStage = _cursor.getString(_cursorIndexOfDroughtStage);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final boolean _tmpIsSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp != 0;
            _item = new ClimateEntity(_tmpId,_tmpCycleId,_tmpRainfall,_tmpTemperature,_tmpDroughtStage,_tmpDate,_tmpIsSynced);
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
