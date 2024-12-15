package ru.vafeen.whisperoftasks.data.local_database.dao.parent

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy


/**
 * Parent DAO interface with base methods
 */
@Dao
internal interface DataAccessObject<T> {

    /**
     * Inserting && Updating in database one or more entities
     * @param entities [Set of entities to put in database]
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)// insert && update
    suspend fun insertAll(vararg entities: T)

    /**
     * Deleting from database one or more entities
     * @param entities [Set of entities to remove from database]
     */
    @Delete
    suspend fun delete(vararg entities: T)

}