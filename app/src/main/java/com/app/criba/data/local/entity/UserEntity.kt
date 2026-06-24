package com.app.criba.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val email: String,
    val passwordHash: String?, // In a real app this should be securely hashed
    val displayName: String,
    val photoUrl: String?
)
