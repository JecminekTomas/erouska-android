/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package cz.covid19cz.erouska.exposurenotifications.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Dao for the bucket [PositiveDiagnosisEntity] in the exposure notification database.
 */
@Dao
abstract class PositiveDiagnosisDao {
    @Query("SELECT * FROM PositiveDiagnosisEntity")
    abstract suspend fun getAll() : PositiveDiagnosisEntity

    @Query("SELECT * FROM PositiveDiagnosisEntity WHERE id = :id")
    abstract suspend fun getById(id: Long): PositiveDiagnosisEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: PositiveDiagnosisEntity?)

    @Query("DELETE FROM PositiveDiagnosisEntity WHERE id = :id")
    abstract suspend fun deleteById(id: Long)

    @Query("UPDATE PositiveDiagnosisEntity SET shared = :shared WHERE id = :id")
    abstract suspend fun markSharedForId(id: Long, shared: Boolean)
}